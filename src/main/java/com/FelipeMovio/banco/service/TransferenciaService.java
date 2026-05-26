package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.TransacoesEntity;
import com.FelipeMovio.banco.database.repository.ContaRepository;
import com.FelipeMovio.banco.database.repository.DadosRepositoy;
import com.FelipeMovio.banco.database.repository.UsuarioRepository;
import com.FelipeMovio.banco.dto.TransacaoDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final UsuarioRepository usuarioRepository;
    private final DadosRepositoy dadosRepositoy;
    private final ContaRepository contaRepository;

    @Transactional
    public TransacoesEntity transferirValores(TransacaoDto transacaoDtod) {

    }


}
