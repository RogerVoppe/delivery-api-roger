package com.deliverytech.delivery.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery.dto.RelatorioTopProdutosDTO; // <-- IMPORT ADICIONADO
import com.deliverytech.delivery.dto.RelatorioVendasDTO; // <-- IMPORT ADICIONADO
import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.StatusPedido; 

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // (Roteiro 3)
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    List<Pedido> findTop10ByOrderByDataPedidoDesc();
    
    // (Roteiro 3 - @Query)
    @Query("SELECT p FROM Pedido p WHERE p.valorTotal > ?1")
    List<Pedido> findByValorTotalMaiorQue(BigDecimal valor);
    
    @Query("SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN ?1 AND ?2 AND p.status = ?3")
    List<Pedido> findPorPeriodoEStatus(LocalDateTime inicio, LocalDateTime fim, StatusPedido status);
        
    // (Roteiro 5 - Filtros)
    @Query("SELECT DISTINCT p FROM Pedido p JOIN p.itens i WHERE " +
           "(:restauranteId IS NULL OR i.produto.restaurante.id = :restauranteId) AND " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:dataInicio IS NULL OR p.dataPedido >= :dataInicio) AND " +
           "(:dataFim IS NULL OR p.dataPedido <= :dataFim)")
    List<Pedido> findComFiltros(
        @Param("restauranteId") Long restauranteId,
        @Param("status") StatusPedido status,
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("dataFim") LocalDateTime dataFim
    );
    
    @Query("SELECT DISTINCT p FROM Pedido p JOIN p.itens i WHERE i.produto.restaurante.id = :restauranteId")
    List<Pedido> findByItensProdutoRestauranteId(@Param("restauranteId") Long restauranteId);

    // --- NOVOS (Roteiro 5 - Relatórios) ---
    
    @Query("SELECT new com.deliverytech.delivery.dto.RelatorioVendasDTO(COUNT(p.id), SUM(p.valorTotal)) " +
           "FROM Pedido p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim AND p.status = 'ENTREGUE'")
    RelatorioVendasDTO getRelatorioVendasPorPeriodo(
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("dataFim") LocalDateTime dataFim
    );

    @Query("SELECT new com.deliverytech.delivery.dto.RelatorioTopProdutosDTO(i.produto.id, i.produto.nome, SUM(i.quantidade)) " +
           "FROM Pedido p JOIN p.itens i " +
           "WHERE p.status = 'ENTREGUE' " +
           "GROUP BY i.produto.id, i.produto.nome " +
           "ORDER BY SUM(i.quantidade) DESC " +
           "LIMIT 5")
    List<RelatorioTopProdutosDTO> getTop5ProdutosVendidos();
}