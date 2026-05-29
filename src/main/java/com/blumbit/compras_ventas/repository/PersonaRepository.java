package com.blumbit.compras_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}
