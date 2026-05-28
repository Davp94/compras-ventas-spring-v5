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
public class CreateCategoriaDto {

    private String nombre;
    private String descripcion;

    public static Categoria toEntity(CreateCategoriaDto createCategoriaDto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(createCategoriaDto.getNombre());
        categoria.setDescripcion(createCategoriaDto.getDescripcion());
        return categoria;
    }
}
