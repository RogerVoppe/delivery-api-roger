package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema; // <-- IMPORT ADICIONADO
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO de resposta para um pedido detalhado") // <-- ANOTAÇÃO NA CLASSE
public record PedidoResponseDTO(
    
    @Schema(description = "ID único do pedido", example = "1001") // <--
    Long id,
    
    @Schema(description = "Nome do cliente", example = "João da Silva") // <--
    String nomeCliente,
    
    @Schema(description = "Nome do restaurante", example = "Pizzaria do Zé") // <--
    String nomeRestaurante,
    
    @Schema(description = "Endereço de entrega", example = "Rua B, 456, Apto 10") // <--
    String enderecoEntrega,
    
    @Schema(description = "Data e hora da criação do pedido") // <--
    LocalDateTime dataPedido,
    
    @Schema(description = "Status atual do pedido", example = "EM_PREPARO") // <--
    StatusPedido status,
    
    @Schema(description = "Valor total (itens + taxa de entrega)", example = "150.00") // <--
    BigDecimal valorTotal,
    
    @Schema(description = "Lista detalhada dos itens do pedido") // <--
    List<ItemPedidoResponseDTO> itens 
) {
}