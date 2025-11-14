package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.dto.RestauranteResponseDTO;
import java.math.BigDecimal;
import java.util.List;

public interface RestauranteService {

    RestauranteResponseDTO cadastrarRestaurante(RestauranteDTO dto); 

    RestauranteResponseDTO buscarRestaurantePorId(Long id); 

    // --- ATUALIZADO (Roteiro 5) ---
    // Substitui buscarRestaurantesPorCategoria e buscarRestaurantesDisponiveis
    List<RestauranteResponseDTO> listarRestaurantes(String categoria, Boolean ativo);

    RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteDTO dto); 

    BigDecimal calcularTaxaEntrega(Long restauranteId, String cep);
    
    // --- NOVOS (Roteiro 5) ---
    RestauranteResponseDTO ativarDesativarRestaurante(Long id);

    List<RestauranteResponseDTO> buscarRestaurantesProximos(String cep);
}