package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.service.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService; // Reutiliza o service

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Pedido não encontrado"));
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    
    // Tarefa: Criação e Cálculo de valores 
    public Pedido criar(Long clienteId, Double valorTotal) {
        Cliente cliente = clienteService.buscarPorId(clienteId);
        
        // Tarefa: Validações 
        if (valorTotal == null || valorTotal <= 0) {
            throw new ValidacaoException("Valor total do pedido é inválido");
        }
        
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setValorTotal(valorTotal);
        
        return pedidoRepository.save(pedido);
    }

    // Tarefa: Mudança de status 
    public Pedido atualizarStatus(Long id, String novoStatus) {
        Pedido pedido = buscarPorId(id);

        // Aqui poderiam existir regras de fluxo de status
        // Ex: Não pode voltar de "ENTREGUE" para "PENDENTE"
        
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
}