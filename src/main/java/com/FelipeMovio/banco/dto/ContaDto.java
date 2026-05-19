package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.database.model.ContaEntity;
import com.FelipeMovio.banco.enums.TipoConta;

public record ContaDto(
        TipoConta tipo,
        DadosDto dados
) {
    public ContaDto(ContaEntity conta) {
        this(
                conta.getTipo(),
                conta.getDados() != null
                        ? new DadosDto(conta.getDados())
                        : null
        );
    }
}

