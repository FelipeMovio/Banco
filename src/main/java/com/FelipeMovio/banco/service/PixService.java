package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.ContaEntity;
import com.FelipeMovio.banco.database.model.PixKeyEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.database.repository.PixKeyRepository;
import com.FelipeMovio.banco.dto.PixKeyRequestDto;
import com.FelipeMovio.banco.enums.TipoChave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PixService {

    private final PixKeyRepository pixKeyRepository;
    private final ContaService contaService;


    public void cadastrarChave(PixKeyRequestDto pixKeyRequestDto, UsuarioEntity usuario){
       ContaEntity conta = contaService.buscarPorConta(usuario.getConta().getId());

        String chave;

        if (pixKeyRequestDto.tipoChave() == TipoChave.ALEATORIA) {
            chave = gerarChaveAleatoria();
        } else {
            chave = conta.getUsuario().getEmail();
        }

        PixKeyEntity pixKey = PixKeyEntity.builder()
                .tipoChave(pixKeyRequestDto.tipoChave())
                .chave(chave)
                .conta(conta)
                .build();

        pixKeyRepository.save(pixKey);
    }




    private String gerarChaveAleatoria() {
        String chave;

        do {
            chave = UUID.randomUUID().toString();
        } while (pixKeyRepository.existsByChave(chave));

        return chave;
    }
}
