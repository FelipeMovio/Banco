package com.FelipeMovio.banco.dto;

import com.FelipeMovio.banco.database.model.TransacoesEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponseDto(
        Long id,
        BigDecimal valor,
        UsuarioMeResumoResponseDto payer,
        UsuarioMeResumoResponseDto payee,
        LocalDateTime dataHoraTransacao

) {

    public static TransacaoResponseDto fromEntity(TransacoesEntity transacoesEntity){
        return new TransacaoResponseDto(
                transacoesEntity.getId(),
                transacoesEntity.getValor(),
                UsuarioMeResumoResponseDto.fromEntity(transacoesEntity.getPagador()),
                UsuarioMeResumoResponseDto.fromEntity(transacoesEntity.getRecebedor()),
                transacoesEntity.getDataHoraTransacao()
        );
    }
}
