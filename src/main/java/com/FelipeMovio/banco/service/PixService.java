package com.FelipeMovio.banco.service;

import com.FelipeMovio.banco.database.model.ContaEntity;
import com.FelipeMovio.banco.database.model.PixKeyEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.database.repository.PixKeyRepository;
import com.FelipeMovio.banco.dto.PixKeyRequestDto;
import com.FelipeMovio.banco.dto.PixKeyResponseDto;
import com.FelipeMovio.banco.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PixService {

    private final PixKeyRepository pixKeyRepository;
    private final ContaService contaService;

    @Transactional
    public void cadastrarChave(PixKeyRequestDto dto, UsuarioEntity usuario) {

        ContaEntity conta = buscarConta(usuario);

        String chave = gerarChave(dto, conta);

        validarDuplicidade(chave);

        PixKeyEntity pixKey = PixKeyEntity.builder()
                .tipoChave(dto.tipoChave())
                .chave(chave)
                .conta(conta)
                .ativa(true)
                .build();

        pixKeyRepository.save(pixKey);
    }

    public List<PixKeyResponseDto> listarChaves(UsuarioEntity usuario) {

        ContaEntity conta = buscarConta(usuario);

        return pixKeyRepository
                .findAllByContaIdAndAtivaTrue(conta.getId())
                .stream()
                .map(PixKeyResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public void desativar(Long id, UsuarioEntity usuario) {

        ContaEntity conta = buscarConta(usuario);

        PixKeyEntity pixKey = pixKeyRepository
                .findByIdAndAtivaTrue(id)
                .orElseThrow(() ->
                        new PixKeyNaoEncontradaException("Chave PIX não encontrada."));

        if (!pixKey.getConta().getId().equals(conta.getId())) {
            throw new PixKeyNaoPertenceAoUsuarioException("Esta chave PIX não pertence à sua conta.");
        }
        if (pixKeyRepository.countByContaIdAndAtivaTrue(conta.getId()) == 1) {
            throw new UltimaChavePixException("A conta deve possuir pelo menos uma chave PIX.");
        }

        pixKey.setAtiva(false);


        pixKeyRepository.save(pixKey);
    }

    public PixKeyEntity buscarPorChave(String chave) {

        return pixKeyRepository.findByChaveAndAtivaTrue(chave)
                .orElseThrow(() ->
                        new PixKeyNaoEncontradaException("Chave PIX não encontrada."));
    }

    //
    // MÉTODOS PRIVADOS
    //
    private ContaEntity buscarConta(UsuarioEntity usuario) {

        if (usuario.getConta() == null) {
            throw new ContaNaoExisteEception("Conta não encontrada.");
        }

        return contaService.buscarPorConta(usuario.getConta().getId());
    }

    private void validarDuplicidade(String chave) {

        if (pixKeyRepository.existsByChave(chave)) {
            throw new PixKeyJaExisteException("Esta chave PIX já está cadastrada.");
        }
    }

    private String gerarChave(PixKeyRequestDto dto, ContaEntity conta) {

        return switch (dto.tipoChave()) {

            case EMAIL -> conta.getUsuario().getEmail();

            case ALEATORIA -> gerarChaveAleatoria();

        };
    }

    private String gerarChaveAleatoria() {

        String chave;

        do {
            chave = UUID.randomUUID().toString();
        } while (pixKeyRepository.existsByChave(chave));

        return chave;
    }
}