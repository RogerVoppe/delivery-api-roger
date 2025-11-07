package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.PedidoDTO; // Agora será usado
import com.deliverytech.delivery.dto.PedidoResponseDTO;
import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.entity.StatusPedido;
import com.deliverytech.delivery.service.PedidoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Agora será usado
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Cenário 2: POST /api/pedidos - Criar pedido (transação complexa)
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoDTO dto) {
        PedidoResponseDTO response = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/pedidos/{id} - Buscar pedido completo
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(response);
    }
    
    // PATCH /api/pedidos/{id}/status - Atualizar status
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(@PathVariable Long id, @RequestBody Map<String, String> body) {
        // Pega o status do corpo do JSON e converte para o Enum
        StatusPedido novoStatus = StatusPedido.valueOf(body.get("status").toUpperCase());
        PedidoResponseDTO response = pedidoService.atualizarStatusPedido(id, novoStatus);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/pedidos/{id} - Cancelar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}