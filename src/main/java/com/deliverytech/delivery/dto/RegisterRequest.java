package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.UserRole;

public record RegisterRequest(
    String nome,
    String email,
    String senha,
    UserRole role
) {
}