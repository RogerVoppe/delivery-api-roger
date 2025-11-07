package com.deliverytech.delivery.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deliverytech.delivery.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(String status);
    
    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Pedido> findByClienteIdAndStatus(Long clienteId, String status);
}