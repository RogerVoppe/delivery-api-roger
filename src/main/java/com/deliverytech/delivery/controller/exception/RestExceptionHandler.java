package com.deliverytech.delivery.controller.exception;

import com.deliverytech.delivery.service.exception.BusinessException;
import com.deliverytech.delivery.service.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

// @ControllerAdvice -> Diz ao Spring que esta classe vai "ouvir" exceções
@ControllerAdvice
public class RestExceptionHandler {

    // "Ouve" por exceções do tipo ResourceNotFoundException (ex: ID não encontrado)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // "Ouve" por exceções do tipo BusinessException (ex: E-mail duplicado)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    // "Ouve" por exceções do tipo MethodArgumentNotValidException (validação do @Valid falhou)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        // Pega todas as mensagens de erro (ex: "O nome não pode ser vazio")
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        
        ApiError error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Erro de validação", errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }
}

// Classe DTO simples para formatar a resposta de erro
record ApiError(HttpStatus status, String message, List<String> errors) {
    public ApiError(HttpStatus status, String message) {
        this(status, message, List.of());
    }
}