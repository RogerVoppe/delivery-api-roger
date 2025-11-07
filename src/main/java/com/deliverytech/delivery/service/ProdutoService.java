package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import java.util.List;

public interface ProdutoService {

    ProdutoResponseDTO cadastrarProduto(ProdutoDTO dto); //

    List<ProdutoResponseDTO> buscarProdutosPorRestaurante(Long restauranteId); //

    ProdutoResponseDTO buscarProdutoPorId(Long id); //

    ProdutoResponseDTO atualizarProduto(Long id, ProdutoDTO dto); //

    ProdutoResponseDTO alterarDisponibilidade(Long id, boolean disponivel); //

    List<ProdutoResponseDTO> buscarProdutosPorCategoria(String categoria); //
}