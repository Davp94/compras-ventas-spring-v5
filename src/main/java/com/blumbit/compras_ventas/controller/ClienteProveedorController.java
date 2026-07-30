package com.blumbit.compras_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.ClienteProveedorDto;
import com.blumbit.compras_ventas.service.IClienteProveedorService;

@RestController
@RequestMapping("/clientes-proveedores")
public class ClienteProveedorController {

    @Autowired
    private IClienteProveedorService clienteProveedorService;

    @GetMapping
    public ResponseEntity<List<ClienteProveedorDto>> getAllClientesProveedores() {
        return ResponseEntity.ok(clienteProveedorService.getAllClienteProveedores());
    }
}
