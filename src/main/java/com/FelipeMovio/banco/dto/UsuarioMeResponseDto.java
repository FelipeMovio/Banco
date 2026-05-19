package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.database.model.UsuarioEntity;

public record UsuarioMeResponseDto(
        String nome,
        String email,
        ContaDto conta
) {
    public UsuarioMeResponseDto(UsuarioEntity usuario) {
        this(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getConta() != null
                        ? new ContaDto(usuario.getConta())
                        : null
        );
    }
}

