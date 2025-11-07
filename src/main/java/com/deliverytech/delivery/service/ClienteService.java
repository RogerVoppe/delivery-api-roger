package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.ClienteDTO;
import com.deliverytech.delivery.dto.ClienteResponseDTO;
import java.util.List;

// Esta é a INTERFACE do serviço 
// Ela define o "contrato"
public interface ClienteService {

    ClienteResponseDTO cadastrarCliente(ClienteDTO dto); // [cite: 358]

    ClienteResponseDTO buscarClientePorId(Long id); // [cite: 359]

    ClienteResponseDTO buscarClientePorEmail(String email); // [cite: 360]

    ClienteResponseDTO atualizarCliente(Long id, ClienteDTO dto); // [cite: 361]

    ClienteResponseDTO ativarDesativarCliente(Long id); // [cite: 362]

    List<ClienteResponseDTO> listarClientesAtivos(); // [cite: 363]
}