package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema; // <-- IMPORT ADICIONADO
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para representar um item dentro de um novo pedido") // <-- ANOTAÇÃO NA CLASSE
public record ItemPedidoDTO(
    
    @Schema(description = "ID do produto a ser comprado", example = "1") // <--
    @NotNull(message = "O ID do produto não pode ser nulo")
    Long produtoId,
    
    @Schema(description = "Quantidade desejada do produto", example = "2") // <--
    @NotNull(message = "A quantidade não pode ser nula")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    Integer quantidade
) {
}