package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca o usuário pelo email para fazer o login
    Optional<Usuario> findByEmail(String email);
    
    // Verifica se o email já existe antes de cadastrar
    boolean existsByEmail(String email);
}