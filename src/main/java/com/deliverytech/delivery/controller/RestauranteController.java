package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.service.RestauranteService;
import com.deliverytech.delivery.service.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteService.listarAtivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restauranteService.buscarPorId(id));
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/categoria")
    public List<Restaurante> buscarPorCategoria(@RequestParam String categoria) {
        return restauranteService.buscarPorCategoria(categoria);
    }
    
    @PostMapping
    public Restaurante salvar(@RequestBody Restaurante restaurante) {
        return restauranteService.salvar(restaurante);
    }

    @PutMapping("/{id}/alterar-status")
    public ResponseEntity<?> alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
         try {
            Restaurante restaurante = restauranteService.alterarStatusAtivo(id, ativo);
            return ResponseEntity.ok(restaurante);
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}