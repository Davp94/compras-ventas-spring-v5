package com.blumbit.compras_ventas.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Integer id;

    private String username;

    private String nombres;

    private String apellidos;

    private String correo;

    private String telefono;

    private String direccion;

    private String nacionalidad;

    private String fechaNacimiento;

    private String genero;

    private List<Integer> roles;

    private List<DocumentoDto> documentos;

    public static UsuarioDto fromEntityUsuario(Usuario usuario, Persona persona){
        return UsuarioDto.builder()
        .id(usuario.getId())
        .username(usuario.getNombre())
        .nombres(persona.getNombres())
        .apellidos(persona.getApellidos())
        .correo(usuario.getEmail())
        .telefono(persona.getTelefono())
        .direccion(persona.getDireccion())
        .nacionalidad(persona.getNacionalidad())
        .fechaNacimiento(persona.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .genero(persona.getGenero())
        .roles(usuario.getRoles().stream().map(rol->rol.getId()).toList())
        .documentos(persona.getDocumentos().stream().map(DocumentoDto::fromEntity).toList())
        .build();
    }
}
