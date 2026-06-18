package com.blumbit.compras_ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>{

    List<Movimiento> findByNota_Id(Integer notaId);

}
