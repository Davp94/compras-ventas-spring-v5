package com.blumbit.compras_ventas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.CreateUsuarioDto;
import com.blumbit.compras_ventas.dto.UsuarioDto;

@Service
public class UsuarioService implements IUsuarioService {

    @Override
    public List<UsuarioDto> getAllUsuarios() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsuarios'");
    }

    @Override
    public UsuarioDto getUsuarioById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsuarioById'");
    }

    @Override
    public UsuarioDto createusuario(CreateUsuarioDto createUsuarioDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createusuario'");
    }

    @Override
    public UsuarioDto updateUsuario(Integer id, CreateUsuarioDto createUsuarioDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUsuario'");
    }

    @Override
    public void deleteUsuario(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUsuario'");
    }

}
