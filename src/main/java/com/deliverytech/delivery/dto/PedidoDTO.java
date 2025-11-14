package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema; // <-- IMPORT ADICIONADO
import jakarta.validation.Valid; 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "DTO para a criação de um pedido completo") // <-- ANOTAÇÃO NA CLASSE
public record PedidoDTO(
    
    @Schema(description = "ID do cliente que está fazendo o pedido", example = "1") // <--
    @NotNull(message = "O ID do cliente não pode ser nulo")
    Long clienteId,
    
    @Schema(description = "ID do restaurante de onde o pedido está saindo", example = "1") // <--
    @NotNull(message = "O ID do restaurante não pode ser nulo")
    Long restauranteId,
    
    @Schema(description = "Endereço completo para a entrega", example = "Rua B, 456, Apto 10") // <--
    @NotBlank(message = "O endereço de entrega não pode ser vazio")
    String enderecoEntrega,
    
    @Schema(description = "Lista de itens do pedido") // <--
    @Valid // Valida os itens da lista
    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    List<ItemPedidoDTO> itens
) {
}