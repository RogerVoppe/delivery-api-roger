package com.deliverytech.delivery.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deliverytech.delivery.entity.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByNomeContaining(String nome);

    List<Restaurante> findByCategoriaAndAtivoTrue(String categoria);

    List<Restaurante> findByAtivoTrue();
    
    List<Restaurante> findByAtivoTrueOrderByAvaliacaoDesc();
}