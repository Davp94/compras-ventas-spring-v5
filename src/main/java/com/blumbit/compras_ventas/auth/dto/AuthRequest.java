package com.blumbit.compras_ventas.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuthRequest {
    private String username;
    private String password;
}
