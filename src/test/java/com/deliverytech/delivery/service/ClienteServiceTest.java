package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.ClienteDTO;
import com.deliverytech.delivery.dto.ClienteResponseDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.service.exception.BusinessException;
import com.deliverytech.delivery.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock // Simula o repositório (não vai no banco real)
    private ClienteRepository clienteRepository;

    @Mock // Simula o ModelMapper
    private ModelMapper modelMapper;

    @InjectMocks // Injeta os simuladores dentro do Service
    private ClienteServiceImpl clienteService;

    @Test
    void deveCadastrarClienteComSucesso() {
        // DADOS (Arrange)
        ClienteDTO dto = new ClienteDTO();
        dto.setNome("Teste");
        dto.setEmail("teste@email.com");
        dto.setEndereco("Rua Teste");

        Cliente clienteEntidade = new Cliente();
        clienteEntidade.setEmail("teste@email.com");

        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setId(1L);
        clienteSalvo.setEmail("teste@email.com");

        ClienteResponseDTO responseDTO = new ClienteResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setEmail("teste@email.com");

        // SIMULAÇÃO (When)
        // Quando perguntar se existe o email, diga NÃO (false)
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        // Quando converter o DTO, retorne a entidade
        when(modelMapper.map(dto, Cliente.class)).thenReturn(clienteEntidade);
        // Quando salvar, retorne o cliente salvo com ID
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);
        // Quando converter para resposta, retorne o DTO de resposta
        when(modelMapper.map(clienteSalvo, ClienteResponseDTO.class)).thenReturn(responseDTO);

        // EXECUÇÃO (Act)
        ClienteResponseDTO resultado = clienteService.cadastrarCliente(dto);

        // VERIFICAÇÃO (Assert)
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("teste@email.com", resultado.getEmail());
        
        // Verifica se o repositório foi chamado exatamente uma vez
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void naoDeveCadastrarClienteComEmailDuplicado() {
        // DADOS
        ClienteDTO dto = new ClienteDTO();
        dto.setEmail("duplicado@email.com");

        // SIMULAÇÃO: O email JÁ existe
        when(clienteRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        // EXECUÇÃO E VERIFICAÇÃO
        // Espera que o serviço lance uma BusinessException
        assertThrows(BusinessException.class, () -> {
            clienteService.cadastrarCliente(dto);
        });

        // Verifica que o método save NUNCA foi chamado
        verify(clienteRepository, never()).save(any());
    }
}