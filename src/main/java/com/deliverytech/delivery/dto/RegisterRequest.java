package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para registrar um novo usuário")
public class RegisterRequest {

    @Schema(description = "Nome completo", example = "Roger Admin")
    private String nome;

    @Schema(description = "E-mail válido", example = "admin@delivery.com")
    private String email;

    @Schema(description = "Senha segura", example = "123")
    private String senha;

    @Schema(description = "Perfil de acesso", example = "ADMIN")
    private UserRole role;

    public RegisterRequest() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}