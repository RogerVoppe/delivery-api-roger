package com.deliverytech.delivery.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.StatusPedido; // Importa o Enum

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(StatusPedido status);

    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Pedido> findTop10ByOrderByDataPedidoDesc();

    @Query("SELECT p FROM Pedido p WHERE p.valorTotal > ?1")
    List<Pedido> findByValorTotalMaiorQue(BigDecimal valor);
    
    // Tarefa: Relatório por período e status
    @Query("SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN ?1 AND ?2 AND p.status = ?3")
    List<Pedido> findPorPeriodoEStatus(LocalDateTime inicio, LocalDateTime fim, StatusPedido status);
}