package com.blumbit.compras_ventas.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "almacen_productos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private Integer cantidadActual;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    private String unidadMedida;

    private BigDecimal precioVentaActual;

    @ManyToOne
    @JoinColumn(name = "almacen_id", nullable = false)
    private Almacen almacen;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;


}
