package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.database.model.UsuarioEntity;

public record UsuarioMeResumoResponseDto(
        Long id,
        String nome
) {
    public static UsuarioMeResumoResponseDto fromEntity(UsuarioEntity usuario){
        return new UsuarioMeResumoResponseDto(
                usuario.getId(),
                usuario.getNome()
        );
    }
}
