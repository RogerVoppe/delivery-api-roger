package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "DTO de resposta para o relatório de vendas por período")
public class RelatorioVendasDTO {

    @Schema(description = "Total de pedidos no período", example = "15")
    private Long totalPedidos;

    @Schema(description = "Faturamento total no período", example = "2500.50")
    private BigDecimal faturamentoTotal;

    public RelatorioVendasDTO(Long totalPedidos, BigDecimal faturamentoTotal) {
        this.totalPedidos = totalPedidos;
        // Garante que o faturamento não seja nulo se não houver vendas
        this.faturamentoTotal = (faturamentoTotal == null) ? BigDecimal.ZERO : faturamentoTotal;
    }

    // Getters
    public Long getTotalPedidos() {
        return totalPedidos;
    }

    public BigDecimal getFaturamentoTotal() {
        return faturamentoTotal;
    }
}