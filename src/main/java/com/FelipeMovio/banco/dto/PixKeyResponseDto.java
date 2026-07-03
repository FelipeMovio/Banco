package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.database.model.PixKeyEntity;
import com.FelipeMovio.banco.enums.TipoChave;

public record PixKeyResponseDto(
        Long id,
        TipoChave tipoChave,
        String chave,
        Boolean ativa
) {
    public static PixKeyResponseDto fromEntity(PixKeyEntity pixKeyEntity){
        return new PixKeyResponseDto(
                pixKeyEntity.getId(),
                pixKeyEntity.getTipoChave(),
                pixKeyEntity.getChave(),
                pixKeyEntity.getAtiva()
        );
    }
}
