package com.blumbit.compras_ventas.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notas")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "tipo_nota", nullable = false, length = 30)
    private String tipoNota;

    @Column(precision = 13, scale = 2)
    private BigDecimal impuestos;

    @Column(precision = 13, scale = 2)
    private BigDecimal descuentos;

    @Column(name = "estado_nota", length = 50)
    private String estadoNota;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_proveedor_id", nullable = false)
    private ClienteProveedor clienteProveedor;
}
