package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.ContaEntity;
import com.FelipeMovio.banco.database.model.TransacoesEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.database.repository.ContaRepository;
import com.FelipeMovio.banco.database.repository.DadosRepositoy;
import com.FelipeMovio.banco.database.repository.TransacaoRepository;
import com.FelipeMovio.banco.database.repository.UsuarioRepository;
import com.FelipeMovio.banco.dto.TransacaoDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;

    @Transactional
    public TransacoesEntity transferirValores(TransacaoDto transacaoDto) {

        ContaEntity pagador = contaService.buscarPorConta(transacaoDto.payer());
        ContaEntity recebedor = contaService.buscarPorConta(transacaoDto.payee());

        // nao pode se auto mandar dinheiro
        if (transacaoDto.payer().equals(transacaoDto.payee())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é permitido transferir para si mesmo.");
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


            return transacaoRepository.saveAndFlush(transacoesEntity);
    }

    private void validarSaldoPagador(UsuarioEntity usuarioEntity, BigDecimal valor){
        if (usuarioEntity.getConta().getDados().getSaldo().compareTo(valor) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para realizar a transferência.");
        }
    }

}
