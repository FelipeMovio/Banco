package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.enums.TipoConta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CompletarPerfilDto(

        TipoConta tipoConta,

        @NotNull
        String agencia,

        @NotNull
        BigDecimal saldo
) {
}
