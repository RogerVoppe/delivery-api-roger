package com.deliverytech.delivery.service.exception;

// Exceção para regras de negócio (Ex: e-mail duplicado)
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}