package com.deliverytech.delivery.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deliverytech.delivery.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByRestauranteId(Long restauranteId);

    List<Produto> findByRestauranteIdAndDisponivelTrue(Long restauranteId);

    List<Produto> findByCategoria(String categoria);
}