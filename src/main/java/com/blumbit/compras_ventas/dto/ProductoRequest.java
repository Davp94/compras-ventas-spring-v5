package com.blumbit.compras_ventas.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

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
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private BigDecimal precioVentaActual;
    private String marca;
    private MultipartFile file;
    private String categoriaId;
}
