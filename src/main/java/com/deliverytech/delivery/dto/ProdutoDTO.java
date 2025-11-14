package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "DTO para criar ou atualizar um produto")
public class ProdutoDTO {
    
    @Schema(description = "Nome do produto", example = "Pizza Margherita")
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;
    
    @Schema(description = "Categoria do produto", example = "Pizza")
    @NotBlank(message = "A categoria não pode ser vazia")
    private String categoria;
    
    @Schema(description = "Preço do produto", example = "45.00")
    @NotNull(message = "O preço não pode ser nulo")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal preco;
    
    @Schema(description = "ID do restaurante ao qual o produto pertence", example = "1")
    @NotNull(message = "O ID do restaurante não pode ser nulo")
    private Long restauranteId;

    // Construtor vazio
    public ProdutoDTO() {
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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }
}