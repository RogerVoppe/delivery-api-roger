package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.dto.RestauranteResponseDTO;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.RestauranteService;
import com.deliverytech.delivery.service.exception.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service 
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public RestauranteResponseDTO cadastrarRestaurante(RestauranteDTO dto) {
        Restaurante restaurante = modelMapper.map(dto, Restaurante.class);
        restaurante.setAtivo(true); 

        Restaurante restauranteSalvo = restauranteRepository.save(restaurante);

        return modelMapper.map(restauranteSalvo, RestauranteResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public RestauranteResponseDTO buscarRestaurantePorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));
        
        return modelMapper.map(restaurante, RestauranteResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> listarRestaurantes(String categoria, Boolean ativo) {
        
        List<Restaurante> restaurantes = restauranteRepository.findComFiltros(categoria, ativo);
        
        return restaurantes.stream()
            .map(r -> modelMapper.map(r, RestauranteResponseDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteDTO dto) {
        Restaurante restauranteExistente = restauranteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));
        
        modelMapper.map(dto, restauranteExistente);
        
        Restaurante restauranteAtualizado = restauranteRepository.save(restauranteExistente);
        
        return modelMapper.map(restauranteAtualizado, RestauranteResponseDTO.class);
    }

    @Override
    public BigDecimal calcularTaxaEntrega(Long restauranteId, String cep) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + restauranteId));
        
        if (cep != null && cep.startsWith("0")) {
            return restaurante.getTaxaEntrega();
        } else {
            return restaurante.getTaxaEntrega().add(new BigDecimal("5.00"));
        }
    }
    
    @Override
    @Transactional
    public RestauranteResponseDTO ativarDesativarRestaurante(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));
        
        restaurante.setAtivo(!restaurante.isAtivo());
        
        Restaurante restauranteAtualizado = restauranteRepository.save(restaurante);
        return modelMapper.map(restauranteAtualizado, RestauranteResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> buscarRestaurantesProximos(String cep) {

        return restauranteRepository.findTop5ByOrderByNomeAsc() 
            .stream()
            .map(r -> modelMapper.map(r, RestauranteResponseDTO.class))
            .collect(Collectors.toList());
    }
}