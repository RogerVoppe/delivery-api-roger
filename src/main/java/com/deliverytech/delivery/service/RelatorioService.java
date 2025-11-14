package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.RelatorioTopProdutosDTO;
import com.deliverytech.delivery.dto.RelatorioVendasDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface RelatorioService {

    RelatorioVendasDTO gerarRelatorioVendas(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    List<RelatorioTopProdutosDTO> gerarRelatorioTopProdutos();
}