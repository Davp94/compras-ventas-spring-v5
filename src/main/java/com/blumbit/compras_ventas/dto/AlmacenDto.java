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

    public static AlmacenDto fromEntity(Almacen almacen) {
        return AlmacenDto.builder()
                .id(almacen.getId())
                .nombre(almacen.getNombre())
                .codigo(almacen.getCodigo())
                .descripcion(almacen.getDescripcion())
                .direccion(almacen.getDireccion())
                .telefono(almacen.getTelefono())
                .ciudad(almacen.getCiudad())
                .sucursalId(almacen.getSucursal().getId())
                .sucursalNombre(almacen.getSucursal().getNombre())
                .build();
    }
}
