package com.blumbit.compras_ventas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.CreateUsuarioDto;
import com.blumbit.compras_ventas.dto.UsuarioDto;
import com.blumbit.compras_ventas.service.IUsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public  ResponseEntity<List<UsuarioDto>>  getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UsuarioDto>  GetusuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@Valid @RequestBody CreateUsuarioDto createUsuarioDto) {
        return ResponseEntity.ok(usuarioService.createusuario(createUsuarioDto));
    }
    
    
}
