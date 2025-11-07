package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.service.PedidoService;
import com.deliverytech.delivery.service.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/cliente/{id}")
    public List<Pedido> listarPorCliente(@PathVariable Long id) {
        return pedidoService.listarPorCliente(id);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody PedidoRequest pedidoRequest) {
        try {
            Pedido pedido = pedidoService.criar(
                pedidoRequest.clienteId(), 
                pedidoRequest.valorTotal()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String novoStatus = body.get("status");
            if (novoStatus == null || novoStatus.trim().isEmpty()) {
                throw new ValidacaoException("O novo status não pode ser nulo ou vazio");
            }
            Pedido pedido = pedidoService.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(pedido);
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

// Um 'record' para facilitar o recebimento do JSON de criação de pedido
record PedidoRequest(Long clienteId, Double valorTotal) {}