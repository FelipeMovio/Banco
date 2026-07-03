package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.ContaEntity;

import com.FelipeMovio.banco.database.model.TransacoesEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;

import com.FelipeMovio.banco.database.repository.TransacaoRepository;


import com.FelipeMovio.banco.dto.MinhasTransacoesResponseDto;
import com.FelipeMovio.banco.dto.TransacaoPixRequestDto;
import com.FelipeMovio.banco.dto.TransacaoRequestDto;
import com.FelipeMovio.banco.dto.TransacaoResponseDto;
import com.FelipeMovio.banco.exception.AutoTransferenciaException;
import com.FelipeMovio.banco.exception.SaldoInsuficienteException;

import com.FelipeMovio.banco.exception.ValorTransferenciaInvalidoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;
    private final EmailService emailService;
    private final PixService pixService;


    @Transactional
    public TransacaoResponseDto transferirValores(
            TransacaoRequestDto dto,
            UsuarioEntity usuario
    ) {

        ContaEntity pagador = usuario.getConta();
        ContaEntity recebedor = contaService.buscarPorConta(dto.payee());

        return realizarTransferencia(
                pagador,
                recebedor,
                dto.value()
        );
    }

    @Transactional
    public TransacaoResponseDto transferenciaPix(
            TransacaoPixRequestDto dto,
            UsuarioEntity usuario
    ) {

        ContaEntity pagador = usuario.getConta();

        ContaEntity recebedor = pixService
                .buscarPorChave(dto.chave())
                .getConta();

        return realizarTransferencia(
                pagador,
                recebedor,
                dto.value()
        );
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

    //privadas
    private TransacaoResponseDto realizarTransferencia(
            ContaEntity pagador,
            ContaEntity recebedor,
            BigDecimal valor
    ) {
        if (pagador.getId().equals(recebedor.getId())) {
            throw new AutoTransferenciaException(
                    "Não é permitido transferir para si mesmo."
            );
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorTransferenciaInvalidoException(
                    "O valor deve ser maior que zero."
            );
        }

        validarSaldo(pagador, valor);

        pagador.getDados().setSaldo(
                pagador.getDados().getSaldo().subtract(valor)
        );

        recebedor.getDados().setSaldo(
                recebedor.getDados().getSaldo().add(valor)
        );

        contaService.salvar(pagador.getDados());
        contaService.salvar(recebedor.getDados());

        TransacoesEntity transacao = TransacoesEntity.builder()
                .valor(valor)
                .pagador(pagador.getUsuario())
                .recebedor(recebedor.getUsuario())
                .build();

        TransacoesEntity salva = transacaoRepository.save(transacao);

        enviarEmails(
                pagador,
                recebedor,
                valor
        );

        return TransacaoResponseDto.fromEntity(salva);
    }


    private void validarSaldo(
            ContaEntity conta,
            BigDecimal valor
    ){

        if (conta.getDados().getSaldo().compareTo(valor) < 0){
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente para realizar a transferência."
            );
        }
    }

    private void enviarEmails(
            ContaEntity pagador,
            ContaEntity recebedor,
            BigDecimal valor
    ){
        emailService.enviarEmail(
                pagador.getUsuario().getEmail(),
                "Transferência enviada",
                "Você enviou R$ " + valor
        );

        emailService.enviarEmail(
                recebedor.getUsuario().getEmail(),
                "Transferência recebida",
                "Você recebeu R$ " + valor
        );
    }
}
