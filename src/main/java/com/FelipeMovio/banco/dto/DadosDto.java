package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.database.model.DadosEntity;

import java.math.BigDecimal;

public record DadosDto(
        Long numero,
        String agencia,
        BigDecimal saldo
) {
    public DadosDto(DadosEntity dados) {
        this(dados.getNumero(),
                dados.getAgencia(),
                dados.getSaldo());
    }
}
