DeliveryTech API

Este é um projeto de API REST para um aplicativo de delivery, construído com Java 21 e Spring Boot.

É um projeto acadêmico que evoluiu em 6 etapas (roteiros). Ele cobre tudo, desde a configuração inicial, passando pela criação de uma API REST completa (com DTOs, Services e lógica de transação), até a implementação de documentação interativa com Swagger e validações customizadas.

🛠️ Tecnologias Usadas

* Java 21
* Spring Boot 3.x
* Spring Web (API REST)
* Spring Data JPA (Persistência)
* H2 (Banco de dados em memória)
* Spring Boot Validation
* springdoc-openapi (Swagger): Para documentação da API
* ModelMapper: Para conversão de Entidade/DTO
* Maven

🏃‍♂️ Como Rodar o Projeto

1.  Você precisa ter o **JDK 21** instalado.
2.  Clone o repositório:
    ```bash
    git clone [https://github.com/RogerVoppe/delivery-api-roger.git](https://github.com/RogerVoppe/delivery-api-roger.git)
    
3.  Entre na pasta e rode o projeto com o Maven Wrapper:
    ```bash
    mvnw.cmd spring-boot:run
    
4.  A API vai ligar e estará rodando em `http://localhost:8080`.


📄 Como Testar (com Swagger UI)

Acesse a documentação no seu navegador:
`http://localhost:8080/swagger-ui.html`

Você verá todos os seus controllers (Clientes, Pedidos, Restaurantes, Relatórios) listados e prontos para testar.


❗️ IMPORTANTE: Banco em Memória

Para testar a API (especialmente o `POST /api/pedidos`), você precisa cadastrar os dados em sequência, usando o botão **"Try it out"** no Swagger:

1.  **`POST /api/clientes`**: Cadastre um cliente.
2.  **`POST /api/restaurantes`**: Cadastre um restaurante.
3.  **`POST /api/produtos`**: Cadastre um ou dois produtos (lembrando de usar o ID do restaurante que você acabou de criar).
4.  **`POST /api/pedidos`**: Agora sim, crie o pedido usando os IDs do cliente e dos produtos.


🚀 Oportunidades 

* Segurança: A API está 100% aberta. O próximo passo seria adicionar Spring Security (com JWT, por exemplo) para proteger os endpoints e criar rotas de login.
* Banco de Dados Real: Estamos usando um banco H2 em memória. Para produção, o ideal seria migrar para um banco persistente como MySQL ou PostgreSQL.
* Testes de Unidade: Testamos a API manualmente (com Postman e Swagger). Um próximo passo seria escrever testes de unidade (com JUnit e Mockito) para a camada de serviço, garantindo que as regras de negócio funcionem isoladamente.

👨‍💻 Desenvolvedor

Roger Voppe
