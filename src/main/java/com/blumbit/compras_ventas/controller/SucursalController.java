package com.blumbit.compras_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.CreateSucursalDto;
import com.blumbit.compras_ventas.dto.SucursalDto;
import com.blumbit.compras_ventas.service.ISucursalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private ISucursalService sucursalService;

    @GetMapping
    public List<SucursalDto> listarSucursales() {
        return sucursalService.listarSucursales();
    }

    @GetMapping("/{id}")
    public SucursalDto buscarSucursalPorId(@PathVariable Integer id) {
        return sucursalService.obtenerSucursalPorId(id);
    }

    @PostMapping
    public SucursalDto crearSucursal(@Valid @RequestBody CreateSucursalDto sucursal) {
        return sucursalService.crearSucursal(sucursal);
    }

    @PutMapping("/{id}")
    public SucursalDto actualizarSucursal(@PathVariable Integer id, @Valid @RequestBody CreateSucursalDto sucursalActualizada) {
        return sucursalService.actualizarSucursal(id, sucursalActualizada);
    }

    @DeleteMapping("/{id}")
    public void eliminarSucursal(@PathVariable Integer id) {
        sucursalService.deleteSucursal(id);
    }
}
