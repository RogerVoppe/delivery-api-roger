package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "DTO de resposta com os dados completos do produto")
public class ProdutoResponseDTO {

    @Schema(description = "ID único do produto", example = "101")
    private Long id;

    @Schema(description = "Nome do produto", example = "Pizza Margherita")
    private String nome;

    @Schema(description = "Categoria do produto", example = "Pizza")
    private String categoria;

    @Schema(description = "Preço do produto", example = "45.00")
    private BigDecimal preco;

    @Schema(description = "Indica se o produto está disponível para venda", example = "true")
    private boolean disponivel;

    @Schema(description = "Objeto do restaurante ao qual o produto pertence")
    private RestauranteResponseDTO restaurante;

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