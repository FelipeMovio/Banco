package com.FelipeMovio.banco.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
// payer = pagador
// payee = recebedor
public record TransacaoRequestDto(
        @NotNull BigDecimal value,
        @NotNull Long payer,
        @NotNull Long payee
) {
}
