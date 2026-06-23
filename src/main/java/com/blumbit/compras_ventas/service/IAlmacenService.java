package com.blumbit.compras_ventas.service;

import java.util.List;

import com.blumbit.compras_ventas.dto.AlmacenDto;
import com.blumbit.compras_ventas.dto.CreateAlmacenDto;

public interface IAlmacenService {

    List<AlmacenDto> listarAlmacenes();

    AlmacenDto obtenerAlmacenPorId(Integer id);

    AlmacenDto crearAlmacen(CreateAlmacenDto almacen);

    AlmacenDto actualizarAlmacen(Integer id, CreateAlmacenDto almacenActualizado);

    void deleteAlmacen(Integer id);
}
