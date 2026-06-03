package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.enums.TipoConta;
import jakarta.validation.constraints.NotBlank;

public record CompletarPerfilDto(
        @NotBlank
        TipoConta tipoConta,

        @NotBlank
        String agencia,

        @NotBlank
        Double saldo
) {
}
