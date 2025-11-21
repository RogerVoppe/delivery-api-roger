package com.deliverytech.delivery.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Verifica se o header Authorization existe e começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extrai o token
        jwt = authHeader.substring(7);
        
        try {
            userEmail = jwtUtil.extractUsername(jwt); // Extrai o email do token
        } catch (Exception e) {
            // Se o token for inválido ou expirado, apenas segue sem autenticar
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Se tem email e ainda não está autenticado no contexto
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Carrega os dados do usuário do banco
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 4. Valida se o token pertence a esse usuário e não expirou
            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                
                // Cria o objeto de autenticação
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 5. Define o usuário como "Logado" no contexto do Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 6. Passa para o próximo filtro
        filterChain.doFilter(request, response);
    }
}