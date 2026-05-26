package com.FelipeMovio.banco.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransacaoDto(
        @NotNull Double value,
        @NotNull Long payer,
        @NotNull Long payee
) {
}
