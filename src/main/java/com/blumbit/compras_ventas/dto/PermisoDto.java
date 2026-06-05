package com.blumbit.compras_ventas.dto;

import com.blumbit.compras_ventas.entity.Permiso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermisoDto {

    private Integer id;

    private String nombre;

    private String recurso;

    private String action;

    public static PermisoDto fromEntity(Permiso permiso) {
        return PermisoDto.builder()
                .id(permiso.getId())
                .nombre(permiso.getNombre())
                .recurso(permiso.getRecurso())
                .action(permiso.getAction())
                .build();
    }
}
