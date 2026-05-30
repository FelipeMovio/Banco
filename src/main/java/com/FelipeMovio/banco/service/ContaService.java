package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.ContaEntity;
import com.FelipeMovio.banco.database.model.DadosEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.database.repository.ContaRepository;
import com.FelipeMovio.banco.database.repository.DadosRepositoy;
import com.FelipeMovio.banco.dto.CompletarPerfilDto;
import com.FelipeMovio.banco.dto.UsuarioMeResponseDto;
import com.FelipeMovio.banco.exception.ContaJaExisteException;
import com.FelipeMovio.banco.exception.ContaNaoExisteEception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final DadosRepositoy dadosRepositoy;


    @Transactional
    public void completarPerfil(
            CompletarPerfilDto dto,
            UsuarioEntity usuario
    ) {

        if (usuario.getConta() != null) {
            throw new ContaJaExisteException("Usuário já possui conta");
        }

        ContaEntity conta = ContaEntity.builder()
                .tipo(dto.tipoConta())
                .usuario(usuario)
                .build();

        DadosEntity dados = DadosEntity.builder()
                .agencia(dto.agencia())
                .numero(gerarNumeroConta())
                .saldo(gerarSaldoInicial())
                .status(true)
                .conta(conta)
                .build();

        conta.setDados(dados);

        contaRepository.save(conta);
    }

    // ver minha conta
    public UsuarioMeResponseDto buscarDadosUsuario(
            UsuarioEntity usuario
    ) {

        return new UsuarioMeResponseDto(usuario);
    }

    //buscar conta
    public ContaEntity buscarPorConta(Long id){
        return contaRepository.findById(id).orElseThrow( () -> new ContaNaoExisteEception(" não encontrado") );
    }

    private Long gerarNumeroConta() {

        Long numero;

        do {
            numero = 100000L + new Random().nextInt(900000);
        } while (dadosRepositoy.existsByNumero(numero));

        return numero;
    }

    private BigDecimal gerarSaldoInicial() {

        return BigDecimal.valueOf(
                100 + new Random().nextDouble(1000)
        ).setScale(2, RoundingMode.HALF_UP);
    }


}
