package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.RelatorioTopProdutosDTO;
import com.deliverytech.delivery.dto.RelatorioVendasDTO;
import com.deliverytech.delivery.service.RelatorioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@Tag(name = "Relatórios", description = "Endpoints para consulta de dados e métricas de negócio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/vendas-por-periodo")
    @Operation(summary = "Relatório de Vendas por Período",
               description = "Calcula o faturamento total e o número de pedidos 'ENTREGUES' dentro de um período de datas.")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    public ResponseEntity<RelatorioVendasDTO> getVendasPorPeriodo(
        @Parameter(description = "Data de início (formato ISO: YYYY-MM-DDTHH:MM:SS)", required = true, example = "2025-11-01T00:00:00")
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
        
        @Parameter(description = "Data final (formato ISO: YYYY-MM-DDTHH:MM:SS)", required = true, example = "2025-11-30T23:59:59")
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim
    ) {
        RelatorioVendasDTO relatorio = relatorioService.gerarRelatorioVendas(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/produtos-mais-vendidos")
    @Operation(summary = "Relatório Top 5 Produtos Mais Vendidos",
               description = "Retorna uma lista dos 5 produtos mais vendidos (em unidades), baseado em pedidos 'ENTREGUES'.")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    public ResponseEntity<List<RelatorioTopProdutosDTO>> getTopProdutos() {
        List<RelatorioTopProdutosDTO> relatorio = relatorioService.gerarRelatorioTopProdutos();
        return ResponseEntity.ok(relatorio);
    }
}