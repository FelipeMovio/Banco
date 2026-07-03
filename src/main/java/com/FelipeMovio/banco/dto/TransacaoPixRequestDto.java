package com.FelipeMovio.banco.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransacaoPixRequestDto(
        @NotNull
        BigDecimal value,
        @NotNull
        String chave
) {
}
