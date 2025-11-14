package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema; // <-- IMPORT ADICIONADO
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "DTO para criar ou atualizar um restaurante") // <-- ANOTAÇÃO NA CLASSE
public class RestauranteDTO {

    @Schema(description = "Nome fantasia do restaurante", example = "Pizzaria do Zé") // <--
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;
    
    @Schema(description = "Categoria principal do restaurante", example = "Italiana") // <--
    @NotBlank(message = "A categoria não pode ser vazia")
    private String categoria;
    
    @Schema(description = "Nota de avaliação média (0.0 a 5.0)", example = "4.5") // <--
    @NotNull(message = "A avaliação não pode ser nula")
    @DecimalMin(value = "0.0", message = "Avaliação deve ser positiva")
    private Double avaliacao;
    
    @Schema(description = "Valor da taxa de entrega padrão", example = "5.00") // <--
    @NotNull(message = "A taxa de entrega não pode ser nula")
    @DecimalMin(value = "0.0", message = "Taxa de entrega deve ser positiva")
    private BigDecimal taxaEntrega;

    // Construtor vazio
    public RestauranteDTO() {
    }

    // Getters e Setters (sem alteração)
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