package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// DTO para a *entrada* de um item
public record ItemPedidoDTO(
    
    @NotNull(message = "O ID do produto não pode ser nulo")
    Long produtoId,
    
    @NotNull(message = "A quantidade não pode ser nula")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    Integer quantidade
) {
}