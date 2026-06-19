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
public class MovimientoRequest {

    private Integer productoId;

    private Integer almacenId;

    private Integer cantidad;

    private String tipoMovimiento;

    private BigDecimal precioUnitarioCompra;

    private BigDecimal precioUnitarioVenta;

    private String observaciones;

    public static Movimiento toEntity(MovimientoRequest movimientoRequest){
        return Movimiento.builder()
        .cantidad(movimientoRequest.getCantidad())
        .precioUnitarioCompra(movimientoRequest.getPrecioUnitarioCompra())
        .precioUnitarioVenta(movimientoRequest.getPrecioUnitarioVenta())
        .observaciones(movimientoRequest.getObservaciones())
        .build();
    }

}
