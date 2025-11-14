package com.deliverytech.delivery.repository;

import java.math.BigDecimal; 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deliverytech.delivery.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // (Roteiro 3)
    List<Produto> findByRestauranteId(Long restauranteId);

    // (Roteiro 3)
    List<Produto> findByCategoria(String categoria);
    
    // (Roteiro 3)
    List<Produto> findByDisponivelTrue();

    // (Roteiro 3)
    List<Produto> findByPrecoLessThanEqual(BigDecimal preco);
    
    // --- NOVO (Roteiro 5) ---
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}