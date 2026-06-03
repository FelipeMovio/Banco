package com.FelipeMovio.banco.exception;

public class ContaJaExisteException extends RuntimeException {
    public ContaJaExisteException(String message) {
        super(message);
    }
}
