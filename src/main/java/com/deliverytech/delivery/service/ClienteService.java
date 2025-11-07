package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.service.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findByAtivoTrue();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Cliente não encontrado"));
    }

    public Cliente salvar(Cliente cliente) {
        // Regra de negócio: Validação de e-mail único 
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        
        if (clienteExistente.isPresent() && !clienteExistente.get().getId().equals(cliente.getId())) {
            throw new ValidacaoException("E-mail já cadastrado");
        }
        
        return clienteRepository.save(cliente);
    }
    
    // Tarefa: Inativação 
    public void inativar(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }
}