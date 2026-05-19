package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.ContaEntity;
import com.FelipeMovio.banco.database.model.DadosEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.database.repository.ContaRepository;
import com.FelipeMovio.banco.dto.CompletarPerfilDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;


    @Transactional
    public void completarPerfil(
            CompletarPerfilDto dto,
            UsuarioEntity usuario
    ) {

        if (usuario.getConta() != null) {
            throw new RuntimeException("Usuário já possui conta");
        }

        ContaEntity conta = ContaEntity.builder()
                .tipo(dto.tipoConta())
                .usuario(usuario)
                .build();

        DadosEntity dados = DadosEntity.builder()
                .agencia(dto.agencia())
                .numero(gerarNumeroConta())
                .saldo(dto.saldo())
                .status(true)
                .conta(conta)
                .build();

        conta.setDados(dados);

        contaRepository.save(conta);
    }

    private Long gerarNumeroConta() {
        return 100000L + new Random().nextInt(900000);
    }
}
