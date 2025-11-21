package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.dto.RestauranteResponseDTO;
import com.deliverytech.delivery.service.PedidoService;
import com.deliverytech.delivery.service.ProdutoService;
import com.deliverytech.delivery.service.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement; // Importante
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importante
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurantes")
@Tag(name = "Restaurantes", description = "Operações para gerenciamento de restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoService pedidoService;

    // POST /api/restaurantes - Protegido (Apenas ADMIN)
    @Operation(summary = "Cadastrar um novo restaurante", 
               description = "Cria um novo restaurante no sistema. Requer permissão de ADMIN.",
               security = @SecurityRequirement(name = "bearerAuth")) // Indica que precisa do cadeado
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado (Não é Admin)"),
        @ApiResponse(responseCode = "422", description = "Dados inválidos")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // <--- A REGRA DE OURO
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@Valid @RequestBody RestauranteDTO dto) {
        RestauranteResponseDTO response = restauranteService.cadastrarRestaurante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar restaurantes (Público)",
               description = "Retorna uma lista de restaurantes com filtros.")
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listarRestaurantes(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) Boolean ativo
    ) {
        List<RestauranteResponseDTO> response = restauranteService.listarRestaurantes(categoria, ativo);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar restaurante por ID (Público)")
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable Long id) {
        RestauranteResponseDTO response = restauranteService.buscarRestaurantePorId(id);
        return ResponseEntity.ok(response);
    }

    // PUT - Protegido (ADMIN)
    @Operation(summary = "Atualizar um restaurante", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(@PathVariable Long id, @Valid @RequestBody RestauranteDTO dto) {
        RestauranteResponseDTO response = restauranteService.atualizarRestaurante(id, dto);
        return ResponseEntity.ok(response);
    }

    // PATCH - Protegido (ADMIN)
    @Operation(summary = "Ativar ou desativar um restaurante", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestauranteResponseDTO> ativarDesativarRestaurante(@PathVariable Long id) {
        RestauranteResponseDTO response = restauranteService.ativarDesativarRestaurante(id);
        return ResponseEntity.ok(response);
    }
    
    // ... (Outros endpoints GET continuam públicos e iguais) ...
    
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        List<RestauranteResponseDTO> response = restauranteService.listarRestaurantes(categoria, null);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/proximos/{cep}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarRestaurantesProximos(@PathVariable String cep) {
        List<RestauranteResponseDTO> response = restauranteService.buscarRestaurantesProximos(cep);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/taxa-entrega/{cep}")
    public ResponseEntity<Map<String, BigDecimal>> calcularTaxa(@PathVariable Long id, @PathVariable String cep) {
        BigDecimal taxa = restauranteService.calcularTaxaEntrega(id, cep);
        return ResponseEntity.ok(Map.of("taxaCalculada", taxa));
    }
    
    @GetMapping("/{restauranteId}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> response = produtoService.buscarProdutosPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{restauranteId}/pedidos")
    public ResponseEntity<List<PedidoResumoDTO>> buscarPedidosPorRestaurante(@PathVariable Long restauranteId) {
        List<PedidoResumoDTO> response = pedidoService.buscarPedidosPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }
}