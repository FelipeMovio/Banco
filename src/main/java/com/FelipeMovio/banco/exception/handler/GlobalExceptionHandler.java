package com.FelipeMovio.banco.exception.handler;

import com.FelipeMovio.banco.exception.ContaJaExisteException;
import com.FelipeMovio.banco.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContaJaExisteException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(ContaJaExisteException ex){
        ErrorResponse response = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
