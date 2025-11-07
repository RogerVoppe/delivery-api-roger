package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.ClienteDTO;
import com.deliverytech.delivery.dto.ClienteResponseDTO;
import com.deliverytech.delivery.service.ClienteService;
import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.service.PedidoService;

import jakarta.validation.Valid; // Importa a validação
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes") // Prefixo da URL para todos os endpoints de cliente
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    private PedidoService pedidoService;

    // Cenário 1: POST /api/clientes - Cadastrar cliente
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@Valid @RequestBody ClienteDTO dto) {
        // @Valid -> Dispara as validações que colocámos no DTO (@NotBlank, @Email)
        ClienteResponseDTO clienteSalvo = clienteService.cadastrarCliente(dto);
        // Retorna 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    // GET /api/clientes/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente); // Retorna 200 OK
    }

    // GET /api/clientes - Listar clientes ativos
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientesAtivos();
        return ResponseEntity.ok(clientes);
    }

    // GET /api/clientes/email/{email} - Buscar por email
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorEmail(@PathVariable String email) {
        ClienteResponseDTO cliente = clienteService.buscarClientePorEmail(email);
        return ResponseEntity.ok(cliente);
    }

    // PUT /api/clientes/{id} - Atualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        ClienteResponseDTO clienteAtualizado = clienteService.atualizarCliente(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }

    // PATCH /api/clientes/{id}/status - Ativar/desativar
    @PatchMapping("/{id}/status")
    public ResponseEntity<ClienteResponseDTO> ativarDesativarCliente(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.ativarDesativarCliente(id);
        return ResponseEntity.ok(cliente);
    }
    @GetMapping("/{clienteId}/pedidos")
public ResponseEntity<List<PedidoResumoDTO>> buscarPedidosPorCliente(@PathVariable Long clienteId) {
    List<PedidoResumoDTO> response = pedidoService.buscarPedidosPorCliente(clienteId);
    return ResponseEntity.ok(response);
}
}