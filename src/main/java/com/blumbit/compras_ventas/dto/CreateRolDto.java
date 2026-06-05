package com.blumbit.compras_ventas.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class CreateRolDto {

    @NotBlank
    private String nombre;

    private String descripcion;

    @NotEmpty
    private List<Integer> permisosIds;
}
