package com.blumbit.compras_ventas.dto;

import java.math.BigDecimal;

import com.blumbit.compras_ventas.entity.Producto;

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
public class ProductoResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioVentaActual;
    private String imagen;
    private String nombreCategoria;

    public static ProductoResponse fromEntity(Producto producto) {
        if (producto == null) {
            return null;
        }
        return ProductoResponse.builder()
        .id(producto.getId())
        .nombre(producto.getNombre())
        .descripcion(producto.getDescripcion())
        .precioVentaActual(producto.getPrecioVentaActual())
        .imagen(producto.getImagen())
        .nombreCategoria(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null)
        .build();
    }
}
