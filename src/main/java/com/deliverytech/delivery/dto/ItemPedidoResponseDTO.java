package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

// DTO para mostrar o item *dentro* da resposta do pedido
public record ItemPedidoResponseDTO(
    String nomeProduto,
    Integer quantidade,
    BigDecimal precoUnitario
) {
}