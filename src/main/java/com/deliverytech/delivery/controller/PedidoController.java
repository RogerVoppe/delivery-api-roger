package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.PedidoDTO;
import com.deliverytech.delivery.dto.PedidoResponseDTO;
import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.entity.StatusPedido;
import com.deliverytech.delivery.service.PedidoService;

// --- Imports do Swagger ---
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
// --- Fim dos Imports ---

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operações para gerenciamento de pedidos") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // POST /api/pedidos
    @Operation(summary = "Criar um novo pedido",
               description = "Cria um novo pedido validando cliente, restaurante e disponibilidade de produtos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso",
                     content = @Content(schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Regra de negócio violada (ex: Cliente inativo, Produto indisponível)"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado (ex: Cliente ID ou Produto ID não existe)"),
        @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos (ex: lista de itens vazia)")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoDTO dto) {
        PedidoResponseDTO response = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/pedidos
    @Operation(summary = "Listar pedidos (com filtros)",
               description = "Retorna uma lista de pedidos resumidos, com filtros opcionais.")
    @GetMapping
    public ResponseEntity<List<PedidoResumoDTO>> listarPedidos(
        @Parameter(description = "Filtrar por ID do restaurante", example = "1")
        @RequestParam(required = false) Long restauranteId,
        
        @Parameter(description = "Filtrar por status do pedido", example = "ENTREGUE")
        @RequestParam(required = false) StatusPedido status,
        
        @Parameter(description = "Filtrar por data de início (formato ISO: YYYY-MM-DDTHH:MM:SS)", example = "2025-11-01T00:00:00")
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
        
        @Parameter(description = "Filtrar por data final (formato ISO: YYYY-MM-DDTHH:MM:SS)", example = "2025-11-30T23:59:59")
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim
    ) {
        List<PedidoResumoDTO> response = pedidoService.listarPedidos(restauranteId, status, dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    // GET /api/pedidos/{id}
    @Operation(summary = "Buscar pedido por ID",
               description = "Retorna os dados detalhados de um pedido, incluindo seus itens.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(response);
    }
    
    // PATCH /api/pedidos/{id}/status
    @Operation(summary = "Atualizar status de um pedido",
               description = "Atualiza o status de um pedido (ex: PENDENTE -> EM_PREPARO).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado"),
        @ApiResponse(responseCode = "400", description = "Transição de status inválida (ex: não pode cancelar pedido ENTREGUE)"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(
        @PathVariable Long id, 
        @Parameter(description = "Corpo JSON simples contendo o novo status", 
                   example = "{\"status\": \"EM_PREPARO\"}")
        @RequestBody Map<String, String> body
    ) {
        StatusPedido novoStatus = StatusPedido.valueOf(body.get("status").toUpperCase());
        PedidoResponseDTO response = pedidoService.atualizarStatusPedido(id, novoStatus);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/pedidos/{id}
    @Operation(summary = "Cancelar um pedido",
               description = "Muda o status de um pedido para CANCELADO (se a regra de negócio permitir).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Não é mais possível cancelar este pedido (ex: já foi entregue)"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}