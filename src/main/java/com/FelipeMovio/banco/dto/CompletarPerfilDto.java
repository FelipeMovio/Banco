package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.enums.TipoConta;

public record CompletarPerfilDto(
        TipoConta tipoConta,
        String agencia,
        Double SaldoInicial
) {
}
