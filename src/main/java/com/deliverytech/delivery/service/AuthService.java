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
        user.setNome(request.getNome()); // <-- CORRIGIDO: getNome()
        user.setEmail(request.getEmail()); // <-- CORRIGIDO: getEmail()
        user.setSenha(passwordEncoder.encode(request.getSenha())); // <-- CORRIGIDO: getSenha()
        user.setRole(request.getRole()); // <-- CORRIGIDO: getRole()
        
        usuarioRepository.save(user);
        
        // Gera o token
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        // O AuthenticationManager verifica email e senha automaticamente
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), // <-- CORRIGIDO: getEmail()
                request.getSenha()  // <-- CORRIGIDO: getSenha()
            )
        );
        
        // Se chegou aqui, a senha está correta. Vamos gerar o token.
        Usuario user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}