package com.blumbit.compras_ventas.service;

import java.util.List;

import com.blumbit.compras_ventas.dto.CreatePermisoDto;
import com.blumbit.compras_ventas.dto.PermisoDto;

public interface IPermisoService {

    List<PermisoDto> getAllPermisos();

    PermisoDto getPermisoById(Integer id);

    PermisoDto createPermiso(CreatePermisoDto createPermisoDto);

    PermisoDto updatePermiso(Integer id, CreatePermisoDto createPermisoDto);

    void deletePermiso(Integer id);
}
