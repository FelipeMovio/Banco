package com.FelipeMovio.banco.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
// payee = recebedor
public record TransacaoRequestDto(
        @NotNull BigDecimal value,
        @NotNull Long payee
) {
}
