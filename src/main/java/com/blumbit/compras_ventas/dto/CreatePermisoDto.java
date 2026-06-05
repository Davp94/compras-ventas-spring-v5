package com.blumbit.compras_ventas.dto;

import com.blumbit.compras_ventas.entity.Permiso;
import jakarta.validation.constraints.NotBlank;
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
public class CreatePermisoDto {

    @NotBlank(message = "El nombre del permiso es requerido")
    private String nombre;

    private String recurso;

    @NotBlank(message = "La acción del permiso es requerida")
    private String action;

    public static Permiso toEntity(CreatePermisoDto dto) {
        Permiso permiso = new Permiso();
        permiso.setNombre(dto.getNombre());
        permiso.setRecurso(dto.getRecurso());
        permiso.setAction(dto.getAction());
        return permiso;
    }
}
