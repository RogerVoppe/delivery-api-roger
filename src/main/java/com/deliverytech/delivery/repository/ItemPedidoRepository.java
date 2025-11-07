package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Este repositório é necessário para o PedidoService salvar os itens
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    // Nenhuma consulta customizada necessária por enquanto
}