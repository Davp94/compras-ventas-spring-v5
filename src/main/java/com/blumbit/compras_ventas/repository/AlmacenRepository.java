package com.blumbit.compras_ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.Almacen;

public interface AlmacenRepository extends JpaRepository<Almacen, Integer>{

    List<Almacen> findBySucursalId(Integer sucursalId);
}
