package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.service.ProdutoService;
import com.deliverytech.delivery.service.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/restaurante/{id}")
    public List<Produto> listarPorRestaurante(@PathVariable Long id) {
        return produtoService.listarPorRestaurante(id);
    }

    // Endpoint para CADASTRAR um novo produto PARA UM RESTAURANTE
    @PostMapping("/restaurante/{id}")
    public ResponseEntity<?> salvar(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.salvar(produto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/alterar-disponibilidade")
    public ResponseEntity<?> alterarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel) {
        try {
            Produto produto = produtoService.alterarDisponibilidade(id, disponivel);
            return ResponseEntity.ok(produto);
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}