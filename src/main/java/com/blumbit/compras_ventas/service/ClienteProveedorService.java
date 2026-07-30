package com.blumbit.compras_ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.ClienteProveedorDto;
import com.blumbit.compras_ventas.repository.ClienteProveedorRepository;

@Service
public class ClienteProveedorService implements IClienteProveedorService {

    @Autowired
    private ClienteProveedorRepository clienteProveedorRepository;

    @Override
    public List<ClienteProveedorDto> getAllClienteProveedores() {
        return clienteProveedorRepository.findAll().stream()
                .map(ClienteProveedorDto::fromEntity)
                .toList();
    }
}
