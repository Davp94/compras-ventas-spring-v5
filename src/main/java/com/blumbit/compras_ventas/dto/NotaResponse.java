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
public class NotaResponse {
    private Integer id;
    private String fecha;
    private String tipoNota;
    private BigDecimal impuestos;
    private BigDecimal descuentos;
    private String estadoNota;
    private String observaciones;
    private Integer usuarioId;
    private String usuarioNombre;
    private Integer clienteProveedorId;
    private String clienteProveedorRazonSocial;
    private List<MovimientoResponse> movimientos;

    public static NotaResponse fromEntity(Nota nota) {
        return NotaResponse.builder()
        .id(nota.getId())
        .fecha(nota.getFecha().toString())
        .tipoNota(nota.getTipoNota())
        .impuestos(nota.getImpuestos())
        .descuentos(nota.getDescuentos())
        .estadoNota(nota.getEstadoNota())
        .observaciones(nota.getObservaciones())
        .usuarioId(nota.getUsuario().getId())
        .usuarioNombre(nota.getUsuario().getNombre())
        .clienteProveedorId(nota.getClienteProveedor().getId())
        .clienteProveedorRazonSocial(nota.getClienteProveedor().getRazonSocial())
        .build();
    }
}
