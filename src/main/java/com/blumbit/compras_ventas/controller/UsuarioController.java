package com.blumbit.compras_ventas.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.CreateUsuarioDto;
import com.blumbit.compras_ventas.dto.UsuarioDto;
import com.blumbit.compras_ventas.service.IUsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public  ResponseEntity<List<UsuarioDto>>  getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UsuarioDto>  GetusuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@Valid @ModelAttribute CreateUsuarioDto createUsuarioDto) {
        logger.info("Datos recibidos para creacion de usuario: {}", createUsuarioDto.toString());
        return ResponseEntity.ok(usuarioService.createusuario(createUsuarioDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable Integer id, @Valid @RequestBody CreateUsuarioDto createUsuarioDto) {
        logger.info("Datos recibidos para actualizacion de usuario {}: {}", id, createUsuarioDto.toString());
        return ResponseEntity.ok(usuarioService.updateUsuario(id, createUsuarioDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        logger.info("Datos recibidos para eliminacion de usuario: {}", id);
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
