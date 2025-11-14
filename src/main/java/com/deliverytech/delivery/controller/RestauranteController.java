package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.dto.RestauranteResponseDTO;
import com.deliverytech.delivery.service.PedidoService;
import com.deliverytech.delivery.service.ProdutoService;
import com.deliverytech.delivery.service.RestauranteService;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // POST /api/restaurantes
    @Operation(summary = "Cadastrar um novo restaurante", description = "Cria um novo restaurante no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso", 
                     content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Dados inválidos (ex: nome em branco)")
    })
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@Valid @RequestBody RestauranteDTO dto) {
        RestauranteResponseDTO response = restauranteService.cadastrarRestaurante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/restaurantes
    @Operation(summary = "Listar restaurantes (com filtros)",
               description = "Retorna uma lista de restaurantes, com filtros opcionais por categoria e status de atividade.")
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listarRestaurantes(
        @Parameter(description = "Filtrar por categoria", example = "Italiana") 
        @RequestParam(required = false) String categoria,
        
        @Parameter(description = "Filtrar por status (true=ativos, false=inativos)") 
        @RequestParam(required = false) Boolean ativo
    ) {
        List<RestauranteResponseDTO> response = restauranteService.listarRestaurantes(categoria, ativo);
        return ResponseEntity.ok(response);
    }

    // GET /api/restaurantes/{id}
    @Operation(summary = "Buscar restaurante por ID",
               description = "Retorna os dados de um restaurante específico pelo seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable Long id) {
        RestauranteResponseDTO response = restauranteService.buscarRestaurantePorId(id);
        return ResponseEntity.ok(response);
    }

    // PUT /api/restaurantes/{id}
    @Operation(summary = "Atualizar um restaurante",
               description = "Atualiza todos os dados de um restaurante existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
        @ApiResponse(responseCode = "422", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(@PathVariable Long id, @Valid @RequestBody RestauranteDTO dto) {
        RestauranteResponseDTO response = restauranteService.atualizarRestaurante(id, dto);
        return ResponseEntity.ok(response);
    }

    // --- ESTE É O MÉTODO QUE ESTAVA QUEBRADO ---
    // PATCH /api/restaurantes/{id}/status
    @Operation(summary = "Ativar ou desativar um restaurante",
               description = "Muda o status 'ativo' de um restaurante. Se está ativo, fica inativo, e vice-versa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<RestauranteResponseDTO> ativarDesativarRestaurante(@PathVariable Long id) {
        RestauranteResponseDTO response = restauranteService.ativarDesativarRestaurante(id);
        return ResponseEntity.ok(response);
    }
    
    // GET /api/restaurantes/categoria/{categoria}
    @Operation(summary = "Buscar restaurantes por categoria (LEGADO)",
               description = "Retorna uma lista de restaurantes de uma categoria específica. (Use o GET /api/restaurantes?categoria=... para filtros)")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        List<RestauranteResponseDTO> response = restauranteService.listarRestaurantes(categoria, null);
        return ResponseEntity.ok(response);
    }
    
    // GET /api/restaurantes/proximos/{cep}
    @Operation(summary = "Listar restaurantes próximos (Simulado)",
               description = "Retorna uma lista de restaurantes próximos a um CEP. (Simulado: retorna os 5 mais bem avaliados)")
    @GetMapping("/proximos/{cep}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarRestaurantesProximos(@PathVariable String cep) {
        List<RestauranteResponseDTO> response = restauranteService.buscarRestaurantesProximos(cep);
        return ResponseEntity.ok(response);
    }

    // GET /api/restaurantes/{id}/taxa-entrega/{cep}
    @Operation(summary = "Calcular taxa de entrega (Simulado)",
               description = "Calcula (simula) a taxa de entrega de um restaurante para um CEP.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Taxa calculada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @GetMapping("/{id}/taxa-entrega/{cep}")
    public ResponseEntity<Map<String, BigDecimal>> calcularTaxa(@PathVariable Long id, @PathVariable String cep) {
        BigDecimal taxa = restauranteService.calcularTaxaEntrega(id, cep);
        return ResponseEntity.ok(Map.of("taxaCalculada", taxa));
    }
    
    // GET /api/restaurantes/{restauranteId}/produtos
    @Operation(summary = "Listar produtos de um restaurante",
               description = "Retorna todos os produtos disponíveis de um restaurante específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @GetMapping("/{restauranteId}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> response = produtoService.buscarProdutosPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    // GET /api/restaurantes/{restauranteId}/pedidos
    @Operation(summary = "Listar pedidos de um restaurante",
               description = "Retorna um histórico resumido de todos os pedidos de um restaurante específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @GetMapping("/{restauranteId}/pedidos")
    public ResponseEntity<List<PedidoResumoDTO>> buscarPedidosPorRestaurante(@PathVariable Long restauranteId) {
        List<PedidoResumoDTO> response = pedidoService.buscarPedidosPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }
}