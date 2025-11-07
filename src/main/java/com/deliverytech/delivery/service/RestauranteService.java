package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> listarAtivos() {
        return restauranteRepository.findByAtivoTrueOrderByAvaliacaoDesc();
    }

    public Restaurante buscarPorId(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Restaurante não encontrado"));
    }

    public Restaurante salvar(Restaurante restaurante) {
        // Aqui poderiam entrar outras validações, como CNPJ único
        return restauranteRepository.save(restaurante);
    }

    public List<Restaurante> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoriaAndAtivoTrue(categoria);
    }

    // Tarefa: Controle de status ativo/inativo 
    public Restaurante alterarStatusAtivo(Long id, boolean ativo) {
        Restaurante restaurante = buscarPorId(id);
        restaurante.setAtivo(ativo);
        return restauranteRepository.save(restaurante);
    }
}