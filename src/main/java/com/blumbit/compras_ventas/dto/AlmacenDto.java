package com.blumbit.compras_ventas.dto;

import com.blumbit.compras_ventas.entity.Almacen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AlmacenDto {

    private Integer id;

    private String nombre;

    private String codigo;

    private String descripcion;

    private String direccion;

    private String telefono;

    private String ciudad;

    private Integer sucursalId;

    private String sucursalNombre;

}
