package com.blumbit.compras_ventas.dto;

import java.math.BigDecimal;
import java.util.List;

import com.blumbit.compras_ventas.entity.Nota;

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
public class NotaRequest {

    private String tipoNota;

    private BigDecimal impuestos;

    private BigDecimal descuentos;

    private String observaciones;

    private Integer usuarioId;

    private Integer clienteProveedorId;

    private List<MovimientoRequest> movimientos;

    public static Nota toEntity(NotaRequest notaRequest) {
        return Nota.builder()
            .tipoNota(notaRequest.getTipoNota())
            .impuestos(notaRequest.getImpuestos())
            .descuentos(notaRequest.getDescuentos())
            .observaciones(notaRequest.getObservaciones())
            .build();
    }
}
