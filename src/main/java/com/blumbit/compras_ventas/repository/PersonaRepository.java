package com.blumbit.compras_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Usuario;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Persona findByUsuario(Usuario usuario);
}
