package com.blumbit.compras_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //QUERY METHODS
    Usuario findByNombre(String nombre);
    boolean existsByNombre(String nombre);

    boolean existByEmail(String email);
}
