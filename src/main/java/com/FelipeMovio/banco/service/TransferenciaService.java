package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.TransacoesEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.database.repository.ContaRepository;
import com.FelipeMovio.banco.database.repository.DadosRepositoy;
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

    private final UsuarioRepository usuarioRepository;
    private final DadosRepositoy dadosRepositoy;
    private final ContaRepository contaRepository;

    @Transactional
    public TransacoesEntity transferirValores(TransacaoDto transacaoDtod) {

        return null;
    }

    private void validarSaldoPagador(UsuarioEntity usuarioEntity, BigDecimal valor){
        if (usuarioEntity.getConta().getDados().getSaldo().compareTo(valor) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para realizar a transferência.");
        }
    }

}
