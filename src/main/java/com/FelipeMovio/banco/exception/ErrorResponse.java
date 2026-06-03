package com.FelipeMovio.banco.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        Integer status
) {
}
