package com.blumbit.compras_ventas.dto;

import com.blumbit.compras_ventas.entity.Documento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDto {
    private Integer id;

    private String archivo;

    private String detalle;

    private Boolean estado;

    private String tipo;

    public static DocumentoDto fromEntity(Documento documento) {
        return DocumentoDto.builder()
        .archivo(documento.getArchivo())
        .detalle(documento.getDetalle())
        .estado(documento.getEstado())
        .tipo(documento.getTipo())
        .build();
    }
}
