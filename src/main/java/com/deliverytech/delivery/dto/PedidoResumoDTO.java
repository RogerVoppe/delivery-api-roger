package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema; // <-- IMPORT ADICIONADO
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO de resposta resumido, usado para listagens (ex: histórico de pedidos)") // <-- ANOTAÇÃO NA CLASSE
public record PedidoResumoDTO(
    
    @Schema(description = "ID único do pedido", example = "1001") // <--
    Long id,
    
    @Schema(description = "Nome do restaurante", example = "Pizzaria do Zé") // <--
    String nomeRestaurante,
    
    @Schema(description = "Data e hora da criação do pedido") // <--
    LocalDateTime dataPedido,
    
    @Schema(description = "Status atual do pedido", example = "ENTREGUE") // <--
    StatusPedido status,
    
    @Schema(description = "Valor total do pedido", example = "150.00") // <--
    BigDecimal valorTotal
) {
}