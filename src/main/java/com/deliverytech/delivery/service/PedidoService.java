package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.PedidoDTO;
import com.deliverytech.delivery.dto.PedidoResponseDTO;
import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.entity.StatusPedido;
import java.time.LocalDateTime; // <-- IMPORT ADICIONADO
import java.util.List;

public interface PedidoService {

    // (Roteiro 4)
    PedidoResponseDTO criarPedido(PedidoDTO dto);

    // (Roteiro 4)
    PedidoResponseDTO buscarPedidoPorId(Long id);

    // (Roteiro 4)
    List<PedidoResumoDTO> buscarPedidosPorCliente(Long clienteId);

    // (Roteiro 4)
    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);

    // (Roteiro 4)
    void cancelarPedido(Long id);

    // --- NOVOS (Roteiro 5) ---
    
    List<PedidoResumoDTO> listarPedidos(
        Long restauranteId, 
        StatusPedido status, 
        LocalDateTime dataInicio, 
        LocalDateTime dataFim
    );

    List<PedidoResumoDTO> buscarPedidosPorRestaurante(Long restauranteId);
}