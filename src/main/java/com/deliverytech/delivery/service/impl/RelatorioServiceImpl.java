package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.RelatorioTopProdutosDTO;
import com.deliverytech.delivery.dto.RelatorioVendasDTO;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public RelatorioVendasDTO gerarRelatorioVendas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        // A lógica complexa já está na @Query do repositório
        return pedidoRepository.getRelatorioVendasPorPeriodo(dataInicio, dataFim);
    }

    @Override
    public List<RelatorioTopProdutosDTO> gerarRelatorioTopProdutos() {
        // A lógica complexa já está na @Query do repositório
        return pedidoRepository.getTop5ProdutosVendidos();
    }
}