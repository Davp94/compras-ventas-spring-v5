package com.blumbit.compras_ventas.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.auth.dto.AuthRequest;
import com.blumbit.compras_ventas.auth.dto.AuthResponse;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${application.jwt.access-token-expiration}")
    private Long expiration;

    private final JwtService jwtService;

    private final UsuarioRepository usuarioRepository;

    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), 
                    authRequest.getPassword(), null));
            Usuario usuario = usuarioRepository.findByEmail(authRequest.getUsername())
            .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
            String accessToken = jwtService.generateAccessToken(usuario);
            String refreshToken = jwtService.generateRefreshToken(usuario);
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .identifier(usuario.getId())
                    .expiration(expiration)
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }

    public AuthResponse refreshToken(String authentication) {
        try {
            if(authentication == null || !authentication.startsWith("Bearer ")){
                throw new RuntimeException("Token inválido");
            }
            String token = authentication.substring(7);
            String username = jwtService.extractUsername(token);
            Usuario usuario = usuarioRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
            String accessToken = jwtService.generateAccessToken(usuario);
            String refreshToken = jwtService.generateRefreshToken(usuario);
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .identifier(usuario.getId())
                    .expiration(expiration)
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }
}
