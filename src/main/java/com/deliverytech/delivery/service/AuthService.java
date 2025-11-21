package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.AuthResponse;
import com.deliverytech.delivery.dto.LoginRequest;
import com.deliverytech.delivery.dto.RegisterRequest;
import com.deliverytech.delivery.entity.Usuario;
import com.deliverytech.delivery.repository.UsuarioRepository;
import com.deliverytech.delivery.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // Cria o usuário com a senha criptografada
        Usuario user = new Usuario();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setSenha(passwordEncoder.encode(request.senha())); // Criptografa a senha!
        user.setRole(request.role());
        
        usuarioRepository.save(user);
        
        // Gera o token
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        // O AuthenticationManager verifica email e senha automaticamente
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.senha()
            )
        );
        
        // Se chegou aqui, a senha está correta. Vamos gerar o token.
        Usuario user = usuarioRepository.findByEmail(request.email()).orElseThrow();
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}