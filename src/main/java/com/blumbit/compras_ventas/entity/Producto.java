package com.blumbit.compras_ventas.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Builder
@Entity
@Table(name="productos")
public class Producto { 

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(length = 20)
    private String unidadMedida;

    @Column(length = 100)
    private String marca;

    @Column(precision = 12, scale = 2)
    private BigDecimal precioVentaActual;

    @Column(length = 255)
    private String imagen;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    private Categoria categoria;

    public Producto(Integer id, String nombre, String descripcion, String unidadMedida, String marca,
            BigDecimal precioVentaActual, String imagen, Boolean estado, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.marca = marca;
        this.precioVentaActual = precioVentaActual;
        this.imagen = imagen;
        this.estado = estado;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPrecioVentaActual() {
        return precioVentaActual;
    }

    public void setPrecioVentaActual(BigDecimal precioVentaActual) {
        this.precioVentaActual = precioVentaActual;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
