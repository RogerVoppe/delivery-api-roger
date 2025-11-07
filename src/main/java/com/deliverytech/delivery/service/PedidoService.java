package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.PedidoDTO;
import com.deliverytech.delivery.dto.PedidoResponseDTO;
import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.entity.StatusPedido;
import java.util.List;

public interface PedidoService {

    // Transação complexa
    PedidoResponseDTO criarPedido(PedidoDTO dto);

    // Com itens do pedido
    PedidoResponseDTO buscarPedidoPorId(Long id);

    // Histórico do cliente
    List<PedidoResumoDTO> buscarPedidosPorCliente(Long clienteId);

    // Validar transições
    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);

    // Apenas se permitido
    void cancelarPedido(Long id);
}