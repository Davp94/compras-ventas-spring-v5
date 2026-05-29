package com.blumbit.compras_ventas.service;

import java.util.List;

import com.blumbit.compras_ventas.dto.CreateUsuarioDto;
import com.blumbit.compras_ventas.dto.UsuarioDto;

public interface IUsuarioService {

    List<UsuarioDto> getAllUsuarios();

    UsuarioDto getUsuarioById(Integer id);

    UsuarioDto createusuario(CreateUsuarioDto createUsuarioDto);

    UsuarioDto updateUsuario(Integer id, CreateUsuarioDto createUsuarioDto);

    void deleteUsuario(Integer id);
}
