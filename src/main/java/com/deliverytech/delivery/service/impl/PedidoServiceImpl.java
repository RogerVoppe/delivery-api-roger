package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.*;
import com.deliverytech.delivery.entity.*;
import com.deliverytech.delivery.repository.*;
import com.deliverytech.delivery.service.PedidoService;
import com.deliverytech.delivery.service.exception.BusinessException;
import com.deliverytech.delivery.service.exception.ResourceNotFoundException;

import org.modelmapper.ModelMapper; // <-- Este import está aqui
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository; // <-- Este campo está aqui
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private ModelMapper modelMapper; // <-- Este campo está aqui

    @Override
    @Transactional
    public PedidoResponseDTO criarPedido(PedidoDTO dto) {

        // 1. Validar Cliente
        Cliente cliente = clienteRepository.findById(dto.clienteId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + dto.clienteId()));
        if (!cliente.isAtivo()) {
            throw new BusinessException("Não é possível criar pedido: Cliente " + cliente.getNome() + " está inativo");
        }

        // 2. Validar Restaurante
        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + dto.restauranteId()));
        if (!restaurante.isAtivo()) {
            throw new BusinessException("Não é possível criar pedido: Restaurante " + restaurante.getNome() + " está inativo");
        }

        // 3. Preparar o Pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setEnderecoEntrega(dto.enderecoEntrega());

        BigDecimal totalPedido = BigDecimal.ZERO;

        // 4. Validar Produtos e Itens
        for (ItemPedidoDTO itemDTO : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemDTO.produtoId()));

            if (!produto.isDisponivel()) {
                throw new BusinessException("Produto " + produto.getNome() + " não está disponível");
            }
            
            if (!produto.getRestaurante().getId().equals(restaurante.getId())) {
                throw new BusinessException("Produto " + produto.getNome() + " não pertence ao restaurante " + restaurante.getNome());
            }

            // 5. Calcular Total e criar ItemPedido
            BigDecimal preco = produto.getPreco();
            BigDecimal subtotalItem = preco.multiply(BigDecimal.valueOf(itemDTO.quantidade()));
            totalPedido = totalPedido.add(subtotalItem);

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setPrecoUnitario(preco);
            item.setQuantidade(itemDTO.quantidade());
            
            pedido.adicionarItem(item);
        }

        // 5. Adicionar Taxa de Entrega
        totalPedido = totalPedido.add(restaurante.getTaxaEntrega());
        pedido.setValorTotal(totalPedido);

        // 6. Salvar Transacionalmente
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 7. Mapear para DTO de Resposta
        return mapToPedidoResponseDTO(pedidoSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
        
        return mapToPedidoResponseDTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResumoDTO> buscarPedidosPorCliente(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        
        return pedidos.stream()
            .map(this::mapToPedidoResumoDTO) // <-- Mapeamento auxiliar
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

        if (pedido.getStatus() == StatusPedido.ENTREGUE || pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new BusinessException("Não é possível alterar o status de um pedido " + pedido.getStatus());
        }
        
        pedido.setStatus(novoStatus);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        
        return mapToPedidoResponseDTO(pedidoAtualizado);
    }

    @Override
    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

        if (pedido.getStatus() != StatusPedido.PENDENTE && pedido.getStatus() != StatusPedido.EM_PREPARO) {
            throw new BusinessException("Não é possível cancelar um pedido que já está " + pedido.getStatus());
        }
        
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }
    
    // --- NOVO MÉTODO (Roteiro 5) ---
    @Override
    @Transactional(readOnly = true)
    public List<PedidoResumoDTO> listarPedidos(Long restauranteId, StatusPedido status, LocalDateTime dataInicio, LocalDateTime dataFim) {
        // Usa a nova query de filtro
        List<Pedido> pedidos = pedidoRepository.findComFiltros(restauranteId, status, dataInicio, dataFim);
        
        return pedidos.stream()
            .map(this::mapToPedidoResumoDTO)
            .collect(Collectors.toList());
    }

    // --- NOVO MÉTODO (Roteiro 5) ---
    @Override
    @Transactional(readOnly = true)
    public List<PedidoResumoDTO> buscarPedidosPorRestaurante(Long restauranteId) {
        // Valida se o restaurante existe
        if (!restauranteRepository.existsById(restauranteId)) {
            throw new ResourceNotFoundException("Restaurante não encontrado com ID: " + restauranteId);
        }
        
        List<Pedido> pedidos = pedidoRepository.findByItensProdutoRestauranteId(restauranteId);
        
        return pedidos.stream()
            .map(this::mapToPedidoResumoDTO)
            .collect(Collectors.toList());
    }
    
    // --- MÉTODOS AUXILIARES DE MAPEAMENTO ---
    
    private PedidoResponseDTO mapToPedidoResponseDTO(Pedido pedido) {
        
        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
            .map(item -> new ItemPedidoResponseDTO(
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario()
            ))
            .collect(Collectors.toList());

        // Pega o nome do restaurante do primeiro item (todos são do mesmo restaurante)
        String nomeRestaurante = "N/A";
        if (!pedido.getItens().isEmpty()) {
            nomeRestaurante = pedido.getItens().get(0).getProduto().getRestaurante().getNome();
        }

        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getCliente().getNome(),
            nomeRestaurante,
            pedido.getEnderecoEntrega(),
            pedido.getDataPedido(),
            pedido.getStatus(),
            pedido.getValorTotal(),
            itensDTO
        );
    }
    
    private PedidoResumoDTO mapToPedidoResumoDTO(Pedido pedido) {
        // Pega o nome do restaurante do primeiro item
        String nomeRestaurante = "N/A";
        if (!pedido.getItens().isEmpty()) {
            nomeRestaurante = pedido.getItens().get(0).getProduto().getRestaurante().getNome();
        }
        
        return new PedidoResumoDTO(
            pedido.getId(),
            nomeRestaurante,
            pedido.getDataPedido(),
            pedido.getStatus(),
            pedido.getValorTotal()
        );
    }
}