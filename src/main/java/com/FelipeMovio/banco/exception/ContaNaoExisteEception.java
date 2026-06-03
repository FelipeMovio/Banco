package com.FelipeMovio.banco.exception;

public class ContaNaoExisteEception extends RuntimeException {
    public ContaNaoExisteEception(String message) {
        super(message);
    }
}
