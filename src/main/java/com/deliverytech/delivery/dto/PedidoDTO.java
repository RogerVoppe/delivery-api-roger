package com.deliverytech.delivery.dto;

import jakarta.validation.Valid; // Importa o @Valid
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

// DTO para a *criação* de um pedido completo
public record PedidoDTO(
    
    @NotNull(message = "O ID do cliente não pode ser nulo")
    Long clienteId,
    
    @NotNull(message = "O ID do restaurante não pode ser nulo")
    Long restauranteId,
    
    @NotBlank(message = "O endereço de entrega não pode ser vazio")
    String enderecoEntrega,
    
    @Valid // Valida os itens da lista
    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    List<ItemPedidoDTO> itens
) {
}