package com.deliverytech.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo o Token de Acesso")
public class AuthResponse {

    @Schema(description = "Token JWT (Bearer Token)", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    public AuthResponse() {}

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}