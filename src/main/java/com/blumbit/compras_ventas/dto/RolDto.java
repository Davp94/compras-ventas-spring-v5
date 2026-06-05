package com.blumbit.compras_ventas.dto;

import java.util.List;

import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.entity.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RolDto {

    private Integer id;

    private String nombre;

    private String descripcion;

    private List<Integer> permisosIds;

    public static RolDto fromEntity(Rol rol) {
        return RolDto.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .descripcion(rol.getDescripcion())
                .permisosIds(rol.getPermisos().stream().map(Permiso::getId).toList())
                .build();
    }
}
