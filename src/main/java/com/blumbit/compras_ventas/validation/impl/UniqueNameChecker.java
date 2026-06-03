package com.blumbit.compras_ventas.validation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.repository.UsuarioRepository;
import com.blumbit.compras_ventas.validation.spec.IUniqueNameChecker;

@Service
public class UniqueNameChecker implements IUniqueNameChecker {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isUniqueName(String name) {
        return !usuarioRepository.existsByNombre(name);
    }

}
