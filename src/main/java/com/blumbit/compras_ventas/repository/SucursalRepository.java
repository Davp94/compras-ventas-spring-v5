package com.blumbit.compras_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

}
