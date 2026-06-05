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

import com.blumbit.compras_ventas.dto.CreateRolDto;
import com.blumbit.compras_ventas.dto.RolDto;
import com.blumbit.compras_ventas.service.IRolService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping
    public List<RolDto> listarRoles() {
        return rolService.listarRoles();
    }

    @GetMapping("/{id}")
    public RolDto buscarRolPorId(@PathVariable Integer id) {
        return rolService.obtenerRolPorId(id);
    }

    @PostMapping
    public RolDto crearRol(@Valid @RequestBody CreateRolDto rol) {
        return rolService.crearRol(rol);
    }

    @PutMapping("/{id}")
    public RolDto actualizarRol(@PathVariable Integer id, @Valid @RequestBody CreateRolDto rolActualizado) {
        return rolService.actualizarRol(id, rolActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminarRol(@PathVariable Integer id) {
        rolService.deleteRol(id);
    }
}
