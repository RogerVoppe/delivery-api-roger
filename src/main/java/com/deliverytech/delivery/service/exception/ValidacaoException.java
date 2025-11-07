package com.deliverytech.delivery.service.exception;

// Esta é uma exceção que usaremos para regras de negócio
public class ValidacaoException extends RuntimeException {

    public ValidacaoException(String message) {
        super(message);
    }
}