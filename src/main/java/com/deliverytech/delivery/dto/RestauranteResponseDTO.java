package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema; // <-- IMPORT ADICIONADO
import java.math.BigDecimal;

@Schema(description = "DTO de resposta com os dados completos do restaurante") // <-- ANOTAÇÃO NA CLASSE
public class RestauranteResponseDTO {

    @Schema(description = "ID único do restaurante", example = "1") // <--
    private Long id;

    @Schema(description = "Nome fantasia do restaurante", example = "Pizzaria do Zé") // <--
    private String nome;

    @Schema(description = "Categoria principal do restaurante", example = "Italiana") // <--
    private String categoria;

    @Schema(description = "Nota de avaliação média", example = "4.5") // <--
    private Double avaliacao;

    @Schema(description = "Valor da taxa de entrega padrão", example = "5.00") // <--
    private BigDecimal taxaEntrega;

    @Schema(description = "Indica se o restaurante está ativo (aceitando pedidos)", example = "true") // <--
    private boolean ativo;

    // Construtor vazio
    public RestauranteResponseDTO() {
    }

    // Getters e Setters (sem alteração)
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}