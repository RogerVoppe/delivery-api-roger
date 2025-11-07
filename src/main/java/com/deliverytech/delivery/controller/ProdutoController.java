package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.service.ProdutoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
// Nota: O prefixo /api/produtos é genérico
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // POST /api/produtos - Cadastrar produto
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoResponseDTO response = produtoService.cadastrarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/produtos/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoResponseDTO response = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(response);
    }
    
    // GET /api/restaurantes/{restauranteId}/produtos - Produtos do restaurante
    // Este endpoint é mais RESTful, então o colocamos no RestauranteController.
    // Vamos criar um endpoint alternativo aqui:
    
    // GET /api/produtos/restaurante/{restauranteId}
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> response = produtoService.buscarProdutosPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }


    // PUT /api/produtos/{id} - Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        ProdutoResponseDTO response = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok(response);
    }

    // PATCH /api/produtos/{id}/disponibilidade - Alterar disponibilidade
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<ProdutoResponseDTO> alterarDisponibilidade(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        boolean disponivel = body.get("disponivel");
        ProdutoResponseDTO response = produtoService.alterarDisponibilidade(id, disponivel);
        return ResponseEntity.ok(response);
    }

    // GET /api/produtos/categoria/{categoria} - Por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorCategoria(@PathVariable String categoria) {
        List<ProdutoResponseDTO> response = produtoService.buscarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(response);
    }
}