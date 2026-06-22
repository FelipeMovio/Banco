package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.ContaEntity;
import com.FelipeMovio.banco.database.model.TransacoesEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;

import com.FelipeMovio.banco.database.repository.TransacaoRepository;

import com.FelipeMovio.banco.database.repository.UsuarioRepository;
import com.FelipeMovio.banco.dto.MinhasTransacoesResponseDto;
import com.FelipeMovio.banco.dto.TransacaoRequestDto;
import com.FelipeMovio.banco.dto.TransacaoResponseDto;
import com.FelipeMovio.banco.exception.SaldoInsuficienteException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;
    private final EmailService emailService;


    @Transactional
    public TransacaoResponseDto transferirValores(TransacaoRequestDto transacaoDto,UsuarioEntity usuarioLogado) {

        ContaEntity pagador = usuarioLogado.getConta();
        ContaEntity recebedor = contaService.buscarPorConta(transacaoDto.payee());

        // nao pode se auto mandar dinheiro
        if (pagador.getId().equals(recebedor.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é permitido transferir para si mesmo."
            );
        }

        if (transacaoDto.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O valor deve ser maior que zero."
            );
        }

        validarSaldoPagador(pagador.getUsuario(), transacaoDto.value());

        pagador.getDados().setSaldo(pagador.getDados().getSaldo().subtract(transacaoDto.value()));
        contaService.salvar(pagador.getDados());

        recebedor.getDados().setSaldo(recebedor.getDados().getSaldo().add(transacaoDto.value()));
        contaService.salvar(recebedor.getDados());

        TransacoesEntity transacoesEntity = TransacoesEntity.builder()
                .valor(transacaoDto.value())
                .pagador(pagador.getUsuario())
                .recebedor(recebedor.getUsuario())
                .build();


        TransacoesEntity transacaoSalva = transacaoRepository.save(transacoesEntity);

        emailService.enviarEmail(
                pagador.getUsuario().getEmail(),
                "Transferência enviada",
                "Você enviou R$ " + transacaoDto.value()
        );

        emailService.enviarEmail(
                recebedor.getUsuario().getEmail(),
                "Transferência recebida",
                "Você recebeu R$ " + transacaoDto.value()
        );


            return TransacaoResponseDto.fromEntity(transacaoSalva);
    }

    public MinhasTransacoesResponseDto minhasTransacoes(UsuarioEntity usuario){

        List<TransacaoResponseDto> comoPagador =
                transacaoRepository.findByPagador(usuario)
                        .stream()
                        .map(TransacaoResponseDto::fromEntity)
                        .toList();

        List<TransacaoResponseDto> comoRecebedor =
                transacaoRepository.findByRecebedor(usuario)
                        .stream()
                        .map(TransacaoResponseDto::fromEntity)
                        .toList();

        return new MinhasTransacoesResponseDto(
                comoPagador,
                comoRecebedor
        );
    }

    private void validarSaldoPagador(UsuarioEntity usuarioEntity, BigDecimal valor){
        if (usuarioEntity.getConta().getDados().getSaldo().compareTo(valor) < 0){
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência.");
        }
    }

}
