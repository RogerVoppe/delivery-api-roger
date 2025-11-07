package com.deliverytech.delivery.service.exception;

// Exceção para quando um ID não é encontrado (Ex: Cliente ID 99)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}