package com.deliverytech.delivery.repository.projection;

import java.math.BigDecimal;

// Esta é uma Interface de Projeção (Atividade 3.3)
// Usada para otimizar relatórios
public interface RelatorioFaturamento {
    
    // Métodos "get" para cada coluna que queremos no relatório
    
    String getNomeRestaurante();
    
    BigDecimal getTotalVendido();
}