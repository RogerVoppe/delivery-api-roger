package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.service.ClienteService;
import com.deliverytech.delivery.service.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes") // Todos os métodos aqui começarão com /clientes
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clienteService.buscarPorId(id));
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cliente cliente) {
        try {
            Cliente clienteSalvo = clienteService.salvar(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            cliente.setId(id); // Garante que estamos atualizando o cliente correto
            Cliente clienteAtualizado = clienteService.salvar(cliente);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            clienteService.inativar(id);
            return ResponseEntity.ok("Cliente inativado com sucesso");
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}