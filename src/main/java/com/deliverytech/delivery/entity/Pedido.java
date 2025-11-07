package com.deliverytech.delivery.entity;

import java.math.BigDecimal; // Importa o tipo correto para dinheiro
import java.time.LocalDateTime;

import com.deliverytech.delivery.entity.StatusPedido; // Importa o Enum que criamos

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType; // Importação necessária
import jakarta.persistence.Enumerated; // Importação necessária
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relação: Muitos pedidos pertencem a UM cliente
    @ManyToOne
    private Cliente cliente;

    private LocalDateTime dataPedido;

    // --- CORREÇÕES DO ROTEIRO 3 ---

    // Informa ao JPA para salvar o NOME do Enum (ex: "ENTREGUE") 
    // em vez do número (ex: 2)
    @Enumerated(EnumType.STRING) 
    private StatusPedido status; // Mudou de String para o Enum StatusPedido

    private BigDecimal valorTotal; // Mudou de Double para BigDecimal (melhor para dinheiro)
    
    // --- FIM DAS CORREÇÕES ---


    // Construtor padrão
    public Pedido() {
    }

    
    // --- GETTERS E SETTERS ATUALIZADOS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    // Getter/Setter CORRETO para StatusPedido
    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    // Getter/Setter CORRETO para BigDecimal
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}