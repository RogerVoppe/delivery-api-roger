package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.service.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteService restauranteService; // Reutiliza o service

    public List<Produto> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteIdAndDisponivelTrue(restauranteId);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Produto não encontrado"));
    }

    // Tarefa: Cadastro por restaurante e validação de preço 
    public Produto salvar(Produto produto, Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
        
        // Tarefa: Validação de preço
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new ValidacaoException("O preço do produto deve ser maior que zero");
        }

        produto.setRestaurante(restaurante);
        return produtoRepository.save(produto);
    }

    // Tarefa: Disponibilidade
    public Produto alterarDisponibilidade(Long id, boolean disponivel) {
        Produto produto = buscarPorId(id);
        produto.setDisponivel(disponivel);
        return produtoRepository.save(produto);
    }
}