package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.dto.RestauranteResponseDTO;
import com.deliverytech.delivery.service.ProdutoService;
import com.deliverytech.delivery.service.RestauranteService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired; // <-- Verifique este import
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    @Autowired // <-- ESTA É A ANOTAÇÃO CRÍTICA (pode ter faltado)
    private RestauranteService restauranteService;
    
    @Autowired // (Este é para o endpoint de /produtos)
    private ProdutoService produtoService;

    // POST /api/restaurantes - Cadastrar restaurante
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@Valid @RequestBody RestauranteDTO dto) {
        RestauranteResponseDTO response = restauranteService.cadastrarRestaurante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/restaurantes/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable Long id) {
        RestauranteResponseDTO response = restauranteService.buscarRestaurantePorId(id);
        return ResponseEntity.ok(response);
    }

    // GET /api/restaurantes - Listar disponíveis
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listarDisponiveis() {
        List<RestauranteResponseDTO> response = restauranteService.buscarRestaurantesDisponiveis();
        return ResponseEntity.ok(response);
    }

    // GET /api/restaurantes/categoria/{categoria} - Por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        List<RestauranteResponseDTO> response = restauranteService.buscarRestaurantesPorCategoria(categoria);
        return ResponseEntity.ok(response);
    }

    // PUT /api/restaurantes/{id} - Atualizar restaurante
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(@PathVariable Long id, @Valid @RequestBody RestauranteDTO dto) {
        RestauranteResponseDTO response = restauranteService.atualizarRestaurante(id, dto);
        return ResponseEntity.ok(response);
    }

    // GET /api/restaurantes/{id}/taxa-entrega/{cep} - Calcular taxa
    @GetMapping("/{id}/taxa-entrega/{cep}")
    public ResponseEntity<Map<String, BigDecimal>> calcularTaxa(@PathVariable Long id, @PathVariable String cep) {
        BigDecimal taxa = restauranteService.calcularTaxaEntrega(id, cep);
        // Retorna um JSON simples: { "taxaCalculada": 10.00 }
        return ResponseEntity.ok(Map.of("taxaCalculada", taxa));
    }
    
    // GET /api/restaurantes/{restauranteId}/produtos - Produtos do restaurante
    @GetMapping("/{restauranteId}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> response = produtoService.buscarProdutosPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }
}