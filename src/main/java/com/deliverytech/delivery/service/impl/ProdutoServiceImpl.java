package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.ProdutoService;
import com.deliverytech.delivery.service.exception.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository; 

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ProdutoResponseDTO cadastrarProduto(ProdutoDTO dto) {
        
        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + dto.getRestauranteId()));
        
        Produto produto = modelMapper.map(dto, Produto.class);
        produto.setRestaurante(restaurante);
        produto.setDisponivel(true); 

        Produto produtoSalvo = produtoRepository.save(produto);
        
        return modelMapper.map(produtoSalvo, ProdutoResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscarProdutosPorRestaurante(Long restauranteId) {
        List<Produto> produtos = produtoRepository.findByRestauranteId(restauranteId);
        
        return produtos.stream()
            .filter(Produto::isDisponivel) 
            .map(p -> modelMapper.map(p, ProdutoResponseDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        
        if (!produto.isDisponivel()) {
             throw new ResourceNotFoundException("Produto não disponível com ID: " + id);
        }
        
        return modelMapper.map(produto, ProdutoResponseDTO.class);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoDTO dto) {
        Produto produtoExistente = produtoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        
        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + dto.getRestauranteId()));
        
        modelMapper.map(dto, produtoExistente);
        produtoExistente.setRestaurante(restaurante);

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        
        return modelMapper.map(produtoAtualizado, ProdutoResponseDTO.class);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO alterarDisponibilidade(Long id, boolean disponivel) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        
        produto.setDisponivel(disponivel);
        
        Produto produtoAtualizado = produtoRepository.save(produto);
        return modelMapper.map(produtoAtualizado, ProdutoResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscarProdutosPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        
        return produtos.stream()
            .map(p -> modelMapper.map(p, ProdutoResponseDTO.class))
            .collect(Collectors.toList());
    }
    
    // --- NOVO MÉTODO (Roteiro 5) ---
    @Override
    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        
        // Adicionaríamos uma verificação de "soft delete" ou se o produto está em um pedido
        // Mas para este roteiro, vamos deletar diretamente
        produtoRepository.delete(produto);
    }
    
    // --- NOVO MÉTODO (Roteiro 5) ---
    @Override
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscarProdutosPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        
        return produtos.stream()
            .map(p -> modelMapper.map(p, ProdutoResponseDTO.class))
            .collect(Collectors.toList());
    }
}