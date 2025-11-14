package com.deliverytech.delivery.repository;

import java.math.BigDecimal; 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <-- IMPORT ADICIONADO
import org.springframework.data.repository.query.Param; // <-- IMPORT ADICIONADO
import org.springframework.stereotype.Repository;
import com.deliverytech.delivery.entity.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByCategoria(String categoria);

    List<Restaurante> findByAtivoTrue();
    
    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxa);

    List<Restaurante> findTop5ByOrderByNomeAsc();

    @Query("SELECT r FROM Restaurante r WHERE " +
           "(:categoria IS NULL OR r.categoria = :categoria) AND " +
           "(:ativo IS NULL OR r.ativo = :ativo)")
    List<Restaurante> findComFiltros(
        @Param("categoria") String categoria, 
        @Param("ativo") Boolean ativo
    );
}