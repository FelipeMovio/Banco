package com.FelipeMovio.banco.dto;

import java.util.List;

public record MinhasTransacoesResponseDto(
        List<TransacaoResponseDto> comoPagador,
        List<TransacaoResponseDto> comoRecebedor
) {
}
