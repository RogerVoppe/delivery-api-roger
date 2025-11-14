package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.service.ProdutoService;

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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações para gerenciamento de produtos e cardápios") // <-- Agrupa no Swagger
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // POST /api/produtos - Cadastrar produto
    @Operation(summary = "Cadastrar um novo produto",
               description = "Cria um novo produto e o associa a um restaurante existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso",
                     content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante (restauranteId) não encontrado"),
        @ApiResponse(responseCode = "422", description = "Dados inválidos (ex: nome ou preço em branco)")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoResponseDTO response = produtoService.cadastrarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/produtos/{id} - Buscar por ID
    @Operation(summary = "Buscar produto por ID",
               description = "Retorna os dados de um produto específico. (Nota: Retorna 404 se o produto estiver indisponível)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado ou indisponível")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoResponseDTO response = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(response);
    }
    
    // GET /api/produtos/buscar?nome={nome}
    @Operation(summary = "Buscar produtos por nome",
               description = "Retorna uma lista de produtos cujo nome contenha o texto buscado (ignorando maiúsculas/minúsculas).")
    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorNome(
        @Parameter(description = "Texto para buscar no nome do produto", example = "pizza")
        @RequestParam String nome
    ) {
        List<ProdutoResponseDTO> response = produtoService.buscarProdutosPorNome(nome);
        return ResponseEntity.ok(response);
    }

    // GET /api/produtos/restaurante/{restauranteId}
    @Operation(summary = "Listar produtos de um restaurante (LEGADO)",
               description = "Retorna produtos de um restaurante. (Use o endpoint em /api/restaurantes/{restauranteId}/produtos)")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> response = produtoService.buscarProdutosPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    // PUT /api/produtos/{id} - Atualizar produto
    @Operation(summary = "Atualizar um produto",
               description = "Atualiza todos os dados de um produto existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto ou Restaurante não encontrado"),
        @ApiResponse(responseCode = "422", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        ProdutoResponseDTO response = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok(response);
    }

    // PATCH /api/produtos/{id}/disponibilidade - Alterar disponibilidade
    @Operation(summary = "Alterar disponibilidade de um produto",
               description = "Muda o status 'disponivel' de um produto (ex: para 'Esgotado').")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Disponibilidade alterada"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<ProdutoResponseDTO> alterarDisponibilidade(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        boolean disponivel = body.get("disponivel");
        ProdutoResponseDTO response = produtoService.alterarDisponibilidade(id, disponivel);
        return ResponseEntity.ok(response);
    }
    
    // --- NOVO ENDPOINT (Roteiro 5) ---
    // DELETE /api/produtos/{id} - Remover produto
    @Operation(summary = "Deletar um produto",
               description = "Remove um produto permanentemente do banco de dados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // GET /api/produtos/categoria/{categoria} - Por categoria
    @Operation(summary = "Listar produtos por categoria (em todos restaurantes)",
               description = "Retorna uma lista de produtos de uma categoria específica, de qualquer restaurante.")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorCategoria(@PathVariable String categoria) {
        List<ProdutoResponseDTO> response = produtoService.buscarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(response);
    }
}