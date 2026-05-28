package com.blumbit.compras_ventas.dto;

import com.blumbit.compras_ventas.entity.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CategoriaDto {

    private Integer id;
    private String nombre;
    private String descripcion;

    public static CategoriaDto fromEntity(Categoria categoria) {
        return CategoriaDto.builder()
        .id(categoria.getId())
        .nombre(categoria.getNombre())
        .descripcion(categoria.getDescripcion())
        .build();
    }
}
