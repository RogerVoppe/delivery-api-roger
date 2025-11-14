package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criar ou atualizar um cliente")
public class ClienteDTO {

    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String nome;
    
    @Schema(description = "E-mail único do cliente", example = "joao.silva@email.com")
    @NotBlank(message = "O e-mail não pode ser vazio")
    @Email(message = "Formato de e-mail inválido")
    private String email;
    
    @Schema(description = "Telefone de contato (opcional)", example = "11987654321")
    private String telefone;
    
    @Schema(description = "Endereço principal de entrega", example = "Rua das Flores, 123")
    @NotBlank(message = "O endereço não pode ser vazio")
    private String endereco;

    // Construtor vazio (necessário para o ModelMapper)
    public ClienteDTO() {
    }

    // Getters e Setters
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
}