package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

// Convertido para 'class'
public class RestauranteDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;
    
    @NotBlank(message = "A categoria não pode ser vazia")
    private String categoria;
    
    @NotNull(message = "A avaliação não pode ser nula")
    @DecimalMin(value = "0.0", message = "Avaliação deve ser positiva")
    private Double avaliacao;
    
    @NotNull(message = "A taxa de entrega não pode ser nula")
    @DecimalMin(value = "0.0", message = "Taxa de entrega deve ser positiva")
    private BigDecimal taxaEntrega;

    // Construtor vazio
    public RestauranteDTO() {
    }

    // Getters e Setters
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

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }
}