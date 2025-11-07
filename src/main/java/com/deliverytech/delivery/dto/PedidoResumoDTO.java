package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// DTO de *Resposta* para listagens (histórico)
public record PedidoResumoDTO(
    Long id,
    String nomeRestaurante,
    LocalDateTime dataPedido,
    StatusPedido status,
    BigDecimal valorTotal
) {
}