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

import com.blumbit.compras_ventas.dto.AlmacenDto;
import com.blumbit.compras_ventas.dto.CreateAlmacenDto;
import com.blumbit.compras_ventas.service.IAlmacenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/almacenes")
public class AlmacenController {

    @Autowired
    private IAlmacenService almacenService;

    @GetMapping
    public List<AlmacenDto> listarAlmacenes() {
        return almacenService.listarAlmacenes();
    }

    @GetMapping("/{id}")
    public AlmacenDto buscarAlmacenPorId(@PathVariable Integer id) {
        return almacenService.obtenerAlmacenPorId(id);
    }

    @PostMapping
    public AlmacenDto crearAlmacen(@Valid @RequestBody CreateAlmacenDto almacen) {
        return almacenService.crearAlmacen(almacen);
    }

    @PutMapping("/{id}")
    public AlmacenDto actualizarAlmacen(@PathVariable Integer id, @Valid @RequestBody CreateAlmacenDto almacenActualizado) {
        return almacenService.actualizarAlmacen(id, almacenActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminarAlmacen(@PathVariable Integer id) {
        almacenService.deleteAlmacen(id);
    }
}
