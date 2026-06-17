package com.blumbit.compras_ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.AlmacenProducto;

public interface AlmacenProductoRepository extends JpaRepository<AlmacenProducto, Integer>{

    List<AlmacenProducto> findByAlmacenId(Integer almacenId);

}
