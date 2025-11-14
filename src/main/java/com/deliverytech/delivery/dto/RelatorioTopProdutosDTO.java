package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta para o relatório de produtos mais vendidos")
public class RelatorioTopProdutosDTO {

    @Schema(description = "ID do produto", example = "1")
    private Long produtoId;
    
    @Schema(description = "Nome do produto", example = "Pizza Margherita")
    private String nomeProduto;
    
    @Schema(description = "Total de unidades vendidas", example = "50")
    private Long totalVendido;

    public RelatorioTopProdutosDTO(Long produtoId, String nomeProduto, Long totalVendido) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.totalVendido = totalVendido;
    }

    // Getters
    public Long getProdutoId() {
        return produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Long getTotalVendido() {
        return totalVendido;
    }
}