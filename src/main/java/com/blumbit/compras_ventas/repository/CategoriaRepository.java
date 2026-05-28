package com.blumbit.compras_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
