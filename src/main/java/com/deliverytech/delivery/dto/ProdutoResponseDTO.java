package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

// Convertido para 'class'
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String categoria;
    private BigDecimal preco;
    private boolean disponivel;
    private RestauranteResponseDTO restaurante; // Mostra o restaurante completo

    // Construtor vazio
    public ProdutoResponseDTO() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public RestauranteResponseDTO getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(RestauranteResponseDTO restaurante) {
        this.restaurante = restaurante;
    }
}