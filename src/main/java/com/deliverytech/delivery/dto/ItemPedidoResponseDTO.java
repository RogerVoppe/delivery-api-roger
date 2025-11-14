package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema; // <-- IMPORT ADICIONADO
import java.math.BigDecimal;

@Schema(description = "DTO que representa um item dentro de um pedido retornado pela API") // <-- ANOTAÇÃO NA CLASSE
public record ItemPedidoResponseDTO(
    
    @Schema(description = "Nome do produto", example = "Pizza Margherita") // <--
    String nomeProduto,
    
    @Schema(description = "Quantidade comprada", example = "2") // <--
    Integer quantidade,
    
    @Schema(description = "Preço unitário do produto no momento da compra", example = "45.00") // <--
    BigDecimal precoUnitario
) {
}