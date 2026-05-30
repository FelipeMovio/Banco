package com.FelipeMovio.banco.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransacaoDto(
        @NotNull BigDecimal value,
        @NotNull Long payer,
        @NotNull Long payee
) {
}
