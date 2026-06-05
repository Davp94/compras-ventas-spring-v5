package com.blumbit.compras_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.CreatePermisoDto;
import com.blumbit.compras_ventas.dto.PermisoDto;
import com.blumbit.compras_ventas.service.IPermisoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/permisos")
public class PermisoController {

    @Autowired
    private IPermisoService permisoService;

    @GetMapping
    public ResponseEntity<List<PermisoDto>> getAllPermisos() {
        return ResponseEntity.ok(permisoService.getAllPermisos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisoDto> getPermisoById(@PathVariable Integer id) {
        return ResponseEntity.ok(permisoService.getPermisoById(id));
    }

    @PostMapping
    public ResponseEntity<PermisoDto> createPermiso(@Valid @RequestBody CreatePermisoDto createPermisoDto) {
        return ResponseEntity.ok(permisoService.createPermiso(createPermisoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermisoDto> updatePermiso(
            @PathVariable Integer id,
            @Valid @RequestBody CreatePermisoDto createPermisoDto) {
        return ResponseEntity.ok(permisoService.updatePermiso(id, createPermisoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermiso(@PathVariable Integer id) {
        permisoService.deletePermiso(id);
        return ResponseEntity.noContent().build();
    }
}
