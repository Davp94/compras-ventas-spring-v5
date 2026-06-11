package com.blumbit.compras_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.CategoriaDto;
import com.blumbit.compras_ventas.dto.CreateCategoriaDto;
import com.blumbit.compras_ventas.service.ICategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping   // - /categorias
    @PreAuthorize("hasAuthority('ROL_VENDEDOR')")
    public List<CategoriaDto> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("/{id}")   // - /categorias/XXX
    public CategoriaDto buscarCategoriaPorId(@PathVariable Integer id) {
        return categoriaService.obtenerCategoriaPorId(id); // - /categorias/100
    }

    @PreAuthorize("hasAuthority('ROL_VENDEDOR', 'ROL_ADMIN')")
    @PostMapping       // - /categorias
    public CategoriaDto crearCategoria(@RequestBody CreateCategoriaDto categoria) {
        return categoriaService.crearCategoria(categoria);
    }

    @PreAuthorize("hasAuthority('ACTUALIZAR_CATEGORIAS')")
    @PutMapping("/{id}")
    public CategoriaDto actualizarCategoria(@PathVariable Integer id, @RequestBody CreateCategoriaDto categoriaActualizada) {
        return categoriaService.actualizarCategoria(id, categoriaActualizada);
    }

    @PreAuthorize("hasRole('ADMIN')") //ROLE_ADMIN
    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Integer id) {
        categoriaService.deleteCategoria(id);
    }


}
