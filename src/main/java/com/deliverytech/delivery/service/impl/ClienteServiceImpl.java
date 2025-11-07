package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.ClienteDTO;
import com.deliverytech.delivery.dto.ClienteResponseDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.service.ClienteService;
import com.deliverytech.delivery.service.exception.BusinessException;
import com.deliverytech.delivery.service.exception.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importa o @Transactional

import java.util.List;
import java.util.stream.Collectors;

@Service // Anotação que define a classe como um Serviço Spring
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper; // Ferramenta para converter DTO <-> Entidade

    @Override
    @Transactional // Garante que a operação é atômica (ou tudo ou nada)
    public ClienteResponseDTO cadastrarCliente(ClienteDTO dto) {
        
        // --- CORREÇÃO APLICADA AQUI ---
        // Tarefa: Validar email único
        // Trocamos dto.email() por dto.getEmail()
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("E-mail já cadastrado"); //
        }
        // --- FIM DA CORREÇÃO ---
        
        // 1. Converte o DTO (requisição) para a Entidade (banco)
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        cliente.setAtivo(true); // Regra de negócio: todo cliente novo é ativo
        
        // 2. Salva a entidade no banco
        Cliente clienteSalvo = clienteRepository.save(cliente);
        
        // 3. Converte a Entidade (salva) para o DTO (resposta) e retorna
        return modelMapper.map(clienteSalvo, ClienteResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true) // readOnly = true (otimização para consultas)
    public ClienteResponseDTO buscarClientePorId(Long id) {
        // Tarefa: Com tratamento de não encontrado
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarClientePorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com E-mail: " + email));
        
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    @Transactional
    public ClienteResponseDTO atualizarCliente(Long id, ClienteDTO dto) {
        // 1. Busca o cliente existente (reusei a lógica de busca)
        Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        // 2. Atualiza os dados do cliente existente com os dados do DTO
        // O ModelMapper é inteligente e só atualiza os campos
        modelMapper.map(dto, clienteExistente);
        
        // 3. Salva o cliente atualizado
        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        
        return modelMapper.map(clienteAtualizado, ClienteResponseDTO.class);
    }

    @Override
    @Transactional
    public ClienteResponseDTO ativarDesativarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        // Tarefa: Toggle status ativo
        cliente.setAtivo(!cliente.isAtivo());
        
        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return modelMapper.map(clienteAtualizado, ClienteResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientesAtivos() {
        // Tarefa: Apenas clientes ativos
        List<Cliente> clientesAtivos = clienteRepository.findByAtivoTrue();
        
        // Converte a Lista de Entidades para uma Lista de DTOs de Resposta
        return clientesAtivos.stream()
            .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
            .collect(Collectors.toList());
    }
}