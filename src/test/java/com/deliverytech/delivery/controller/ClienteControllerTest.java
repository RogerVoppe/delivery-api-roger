package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.ClienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Carrega todo o contexto da aplicação
@AutoConfigureMockMvc // Configura o "simulador" de requisições HTTP
@ActiveProfiles("test") // Usa o application-test.properties
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Ferramenta para fazer requisições fake

    @Autowired
    private ObjectMapper objectMapper; // Transforma Objetos em JSON

    @Test
    @WithMockUser(roles = "ADMIN") // Simula que estamos logados como ADMIN (para passar pelo Security)
    void deveCriarClienteComSucesso() throws Exception {
        // DADOS
        ClienteDTO dto = new ClienteDTO();
        dto.setNome("Cliente Integração");
        dto.setEmail("integracao@teste.com");
        dto.setTelefone("11999999999");
        dto.setEndereco("Rua Teste Integração");

        // EXECUÇÃO E VERIFICAÇÃO
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))) // Converte o objeto para JSON String
                .andExpect(status().isCreated()) // Espera 201 Created
                .andExpect(jsonPath("$.id").exists()) // Espera que tenha um ID na resposta
                .andExpect(jsonPath("$.nome").value("Cliente Integração")) // Espera o nome correto
                .andExpect(jsonPath("$.ativo").value(true)); // Espera que esteja ativo
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveRetornarErroComDadosInvalidos() throws Exception {
        // DADOS (Cliente sem nome e sem email)
        ClienteDTO dto = new ClienteDTO();
        dto.setEndereco("Rua Sem Nome");

        // EXECUÇÃO E VERIFICAÇÃO
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity()); // Espera 422 (Erro de Validação)
    }
}