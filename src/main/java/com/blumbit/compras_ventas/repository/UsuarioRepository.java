package com.blumbit.compras_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blumbit.compras_ventas.entity.Usuario;
import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //QUERY METHODS
    Usuario findByNombre(String nombre);

    Optional<Usuario> findByEmail(String email);
    boolean existsByNombre(String nombre);

    @Query(value="SELECT EXISTS(SELECT 1 FROM usuarios WHERE email = ?1)", nativeQuery = true)
    boolean existByEmail(String email);
}
