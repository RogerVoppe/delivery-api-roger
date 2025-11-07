package com.deliverytech.delivery.config;

import com.deliverytech.delivery.entity.*; // Importa todas as entidades
import com.deliverytech.delivery.repository.*; // Importa todos os repositórios
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component // Diz ao Spring para gerenciar esta classe
public class DataLoader implements CommandLineRunner {

    // 1. Injeta todos os repositórios que vamos usar
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override // Este método roda automaticamente ao iniciar a aplicação
    public void run(String... args) throws Exception {
        
        System.out.println("--- 🚀 INICIANDO DATALOADER (ROTEIRO 3) 🚀 ---");

        // --- ATIVIDADE 2: INSERIR DADOS DE TESTE ---
        System.out.println("\n--- 1. Inserindo dados de teste ---");

        // Tarefa: 3 Clientes [cite: 370]
        Cliente c1 = new Cliente();
        c1.setNome("João Silva");
        c1.setEmail("joao@email.com");
        c1.setTelefone("11999990001");
        c1.setEndereco("Rua A, 10");
        clienteRepository.save(c1); // Salva no H2

        Cliente c2 = new Cliente();
        c2.setNome("Maria Santos");
        c2.setEmail("maria@email.com");
        c2.setTelefone("11999990002");
        c2.setEndereco("Rua B, 20");
        clienteRepository.save(c2);

        Cliente c3 = new Cliente();
        c3.setNome("Pedro Almeida");
        c3.setEmail("pedro@email.com");
        c3.setTelefone("11999990003");
        c3.setEndereco("Rua C, 30");
        clienteRepository.save(c3);
        System.out.println("... 3 Clientes salvos.");

        // Tarefa: 2 Restaurantes [cite: 371]
        Restaurante r1 = new Restaurante();
        r1.setNome("Pizzaria do Zé");
        r1.setCategoria("Italiana");
        r1.setAvaliacao(4.5);
        r1.setTaxaEntrega(new BigDecimal("5.00")); // Usa BigDecimal
        restauranteRepository.save(r1);

        Restaurante r2 = new Restaurante();
        r2.setNome("Sushi Rápido");
        r2.setCategoria("Japonesa");
        r2.setAvaliacao(4.8);
        r2.setTaxaEntrega(new BigDecimal("7.50"));
        restauranteRepository.save(r2);
        System.out.println("... 2 Restaurantes salvos.");
        
        // Tarefa: 5 Produtos [cite: 372]
        Produto p1 = new Produto();
        p1.setNome("Pizza Margherita");
        p1.setCategoria("Pizza");
        p1.setPreco(new BigDecimal("45.00"));
        p1.setRestaurante(r1); // Associa ao restaurante 1
        produtoRepository.save(p1);

        Produto p2 = new Produto();
        p2.setNome("Lasanha Bolonhesa");
        p2.setCategoria("Massa");
        p2.setPreco(new BigDecimal("55.00"));
        p2.setRestaurante(r1);
        produtoRepository.save(p2);
        
        Produto p3 = new Produto();
        p3.setNome("Combinado Salmão (20 peças)");
        p3.setCategoria("Sushi");
        p3.setPreco(new BigDecimal("80.00"));
        p3.setRestaurante(r2); // Associa ao restaurante 2
        produtoRepository.save(p3);
        
        Produto p4 = new Produto();
        p4.setNome("Temaki Filadélfia");
        p4.setCategoria("Sushi");
        p4.setPreco(new BigDecimal("28.00"));
        p4.setRestaurante(r2);
        produtoRepository.save(p4);
        
        Produto p5 = new Produto();
        p5.setNome("Guioza (6 unidades)");
        p5.setCategoria("Entrada");
        p5.setPreco(new BigDecimal("22.00"));
        p5.setRestaurante(r2);
        p5.setDisponivel(false); // Produto indisponível
        produtoRepository.save(p5);
        System.out.println("... 5 Produtos salvos.");

        // Tarefa: 2 Pedidos [cite: 373]
        Pedido ped1 = new Pedido();
        ped1.setCliente(c1); // Pedido do João
        ped1.setDataPedido(LocalDateTime.now().minusDays(1)); // Ontem
        ped1.setStatus(StatusPedido.ENTREGUE); // Usa o Enum
        ped1.setValorTotal(new BigDecimal("100.00")); // p1 + p2
        pedidoRepository.save(ped1);

        Pedido ped2 = new Pedido();
        ped2.setCliente(c2); // Pedido da Maria
        ped2.setDataPedido(LocalDateTime.now()); // Hoje
        ped2.setStatus(StatusPedido.EM_PREPARO);
        ped2.setValorTotal(new BigDecimal("80.00")); // p3
        pedidoRepository.save(ped2);
        System.out.println("... 2 Pedidos salvos.");

        System.out.println("\n--- 2. Validando consultas (Cenários de Teste) ---");

        // --- CENÁRIO 1: Busca de Cliente por Email [cite: 422] ---
        System.out.println("\n[Cenário 1: Busca por 'joao@email.com']");
        Cliente clienteEmail = clienteRepository.findByEmail("joao@email.com").orElse(null);
        if (clienteEmail != null) {
            System.out.println("Resultado: Cliente encontrado -> " + clienteEmail.getNome());
        } else {
            System.out.println("Resultado: Cliente NÃO encontrado.");
        }

        // --- CENÁRIO 2: Produtos por Restaurante [cite: 425] ---
        System.out.println("\n[Cenário 2: Produtos do Restaurante ID " + r1.getId() + " (" + r1.getNome() + ")]");
        List<Produto> produtosR1 = produtoRepository.findByRestauranteId(r1.getId());
        for (Produto p : produtosR1) {
            System.out.println("Resultado: Produto -> " + p.getNome() + " | Preço: " + p.getPreco());
        }

        // --- CENÁRIO 3: Pedidos Recentes [cite: 428] ---
        System.out.println("\n[Cenário 3: Top 10 Pedidos Recentes]");
        List<Pedido> pedidosRecentes = pedidoRepository.findTop10ByOrderByDataPedidoDesc();
        for (Pedido p : pedidosRecentes) {
            System.out.println("Resultado: Pedido ID " + p.getId() + " | Cliente: " + p.getCliente().getNome() + " | Status: " + p.getStatus());
        }

        // --- CENÁRIO 4: Restaurantes por Taxa [cite: 431] ---
        System.out.println("\n[Cenário 4: Restaurantes com taxa <= R$ 5.00]");
        List<Restaurante> restaurantesTaxa = restauranteRepository.findByTaxaEntregaLessThanEqual(new BigDecimal("5.00"));
        for (Restaurante r : restaurantesTaxa) {
            System.out.println("Resultado: Restaurante -> " + r.getNome() + " | Taxa: " + r.getTaxaEntrega());
        }
        
        // --- Outros Testes (Roteiro 3) ---
        System.out.println("\n[Teste Extra: Produtos por Categoria 'Sushi']");
        List<Produto> produtosSushi = produtoRepository.findByCategoria("Sushi");
        System.out.println("... Encontrados " + produtosSushi.size() + " produtos 'Sushi'.");

        System.out.println("\n[Teste Extra: Clientes por Nome 'maria']");
        List<Cliente> clientesNome = clienteRepository.findByNomeContainingIgnoreCase("maria");
        System.out.println("... Encontrados " + clientesNome.size() + " clientes com 'maria' no nome.");
        
        System.out.println("\n[Teste Extra: Verificar se 'pedro@email.com' existe]");
        boolean existe = clienteRepository.existsByEmail("pedro@email.com");
        System.out.println("... O email 'pedro@email.com' existe? -> " + existe);
        
        System.out.println("\n--- 3. Validando consultas @Query (Atividade 3) ---");

        // Teste: Pedidos com valor acima de 85.00
        System.out.println("\n[Teste @Query: Pedidos com valor > R$ 85.00]");
        List<Pedido> pedidosCaros = pedidoRepository.findByValorTotalMaiorQue(new BigDecimal("85.00"));
        for (Pedido p : pedidosCaros) {
            System.out.println("Resultado: Pedido ID " + p.getId() + " | Valor: " + p.getValorTotal());
        }

        // Teste: Relatório por período e status (Pedidos ENTREGUES de ontem)
        System.out.println("\n[Teste @Query: Pedidos ENTREGUES no período]");
        LocalDateTime inicio = LocalDateTime.now().minusDays(2);
        LocalDateTime fim = LocalDateTime.now();
        List<Pedido> pedidosEntregues = pedidoRepository.findPorPeriodoEStatus(inicio, fim, StatusPedido.ENTREGUE);
        for (Pedido p : pedidosEntregues) {
            System.out.println("Resultado: Pedido ID " + p.getId() + " | Status: " + p.getStatus());
        }

        // Teste: Restaurante por Nome Similar e Categoria
        System.out.println("\n[Teste @Query: Restaurante por Nome 'Sushi' e Categoria 'Japonesa']");
        List<Restaurante> restaurantesQuery = restauranteRepository.findPorNomeSimilarECategoria("Sushi", "Japonesa");
        for (Restaurante r : restaurantesQuery) {
            System.out.println("Resultado: Restaurante -> " + r.getNome());
        }

        System.out.println("\n--- 🏁 DATALOADER FINALIZADO 🏁 ---");
    }
}