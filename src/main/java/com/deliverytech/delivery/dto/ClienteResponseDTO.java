package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta com os dados completos do cliente")
public class ClienteResponseDTO {

    @Schema(description = "ID único do cliente", example = "1")
    private Long id;
    
    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    private String nome;
    
    @Schema(description = "E-mail único do cliente", example = "joao.silva@email.com")
    private String email;
    
    @Schema(description = "Telefone de contato", example = "11987654321")
    private String telefone;
    
    @Schema(description = "Endereço principal de entrega", example = "Rua das Flores, 123")
    private String endereco;
    
    @Schema(description = "Indica se o cliente está ativo no sistema", example = "true")
    private boolean ativo;

    // Construtor vazio (O ModelMapper precisa disso!)
    public ClienteResponseDTO() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}