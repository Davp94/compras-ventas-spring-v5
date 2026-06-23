package com.blumbit.compras_ventas.dto;

import java.util.List;

import com.blumbit.compras_ventas.entity.Sucursal;
import com.blumbit.compras_ventas.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SucursalDto {

    private Integer id;

    private String nombre;

    private String direccion;

    private String telefono;

    private String ciudad;

    private List<Integer> usuariosIds;

    public static SucursalDto fromEntity(Sucursal sucursal) {
        return SucursalDto.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .direccion(sucursal.getDireccion())
                .telefono(sucursal.getTelefono())
                .ciudad(sucursal.getCiudad())
                .usuariosIds(sucursal.getUsuarios() != null
                        ? sucursal.getUsuarios().stream().map(Usuario::getId).toList()
                        : List.of())
                .build();
    }
}
