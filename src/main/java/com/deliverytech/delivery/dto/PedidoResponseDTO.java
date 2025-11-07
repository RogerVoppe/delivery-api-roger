package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// DTO de *Resposta* para um pedido detalhado
public record PedidoResponseDTO(
    Long id,
    String nomeCliente,
    String nomeRestaurante,
    String enderecoEntrega,
    LocalDateTime dataPedido,
    StatusPedido status,
    BigDecimal valorTotal,
    List<ItemPedidoResponseDTO> itens // Lista de DTOs de item
) {
}