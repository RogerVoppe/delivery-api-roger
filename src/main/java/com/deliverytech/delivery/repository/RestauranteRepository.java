package com.deliverytech.delivery.repository;

import java.math.BigDecimal; // Importa o tipo de dinheiro
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deliverytech.delivery.entity.Restaurante;
import org.springframework.data.jpa.repository.Query;
import com.deliverytech.delivery.repository.projection.RelatorioFaturamento;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByCategoria(String categoria);

    List<Restaurante> findByAtivoTrue();

    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxa);

    List<Restaurante> findTop5ByOrderByNomeAsc();
    @Query("SELECT r FROM Restaurante r WHERE r.nome LIKE %?1% AND r.categoria = ?2")
    List<Restaurante> findPorNomeSimilarECategoria(String nome, String categoria);
}