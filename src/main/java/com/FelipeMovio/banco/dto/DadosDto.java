package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.database.model.DadosEntity;

public record DadosDto(
        Long numero,
        String agencia,
        Double saldo
) {
    public DadosDto(DadosEntity dados) {
        this(dados.getNumero(),
                dados.getAgencia(),
                dados.getSaldo());
    }
}
