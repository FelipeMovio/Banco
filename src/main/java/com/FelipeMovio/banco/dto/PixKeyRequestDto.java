package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.enums.TipoChave;
import jakarta.validation.constraints.NotNull;

public record PixKeyRequestDto(

        @NotNull
        TipoChave tipoChave,

        String chave
) {
}
