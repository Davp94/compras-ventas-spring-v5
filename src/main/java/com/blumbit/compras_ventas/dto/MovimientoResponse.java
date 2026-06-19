package com.blumbit.compras_ventas.dto;

import java.math.BigDecimal;

import com.blumbit.compras_ventas.entity.Movimiento;

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
public class MovimientoResponse {
    private Integer id;
    private Integer notaId;
    private Integer productoId;
    private String productoNombre;
    private Integer almacenId;
    private Integer cantidad;
    private String tipoMovimiento;
    private String observaciones;
    private BigDecimal precioUnitarioCompra;
    private BigDecimal precioUnitarioVenta;

    public static MovimientoResponse fromEntity(Movimiento movimiento) {
        return MovimientoResponse.builder()
        .id(movimiento.getId())
        .notaId(movimiento.getNota().getId())
        .productoId(movimiento.getProducto().getId())
        .productoNombre(movimiento.getProducto().getNombre())
        .almacenId(movimiento.getAlmacen().getId())
        .cantidad(movimiento.getCantidad())
        .tipoMovimiento(movimiento.getTipoMovimiento().toString())
        .precioUnitarioCompra(movimiento.getPrecioUnitarioCompra())
        .precioUnitarioVenta(movimiento.getPrecioUnitarioVenta())
        .observaciones(movimiento.getObservaciones())
        .build();
    }
}
