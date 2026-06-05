package com.blumbit.compras_ventas.service;

import java.util.List;

import com.blumbit.compras_ventas.dto.CreateRolDto;
import com.blumbit.compras_ventas.dto.RolDto;

public interface IRolService {

    List<RolDto> listarRoles();

    RolDto obtenerRolPorId(Integer id);

    RolDto crearRol(CreateRolDto rol);

    RolDto actualizarRol(Integer id, CreateRolDto rolActualizado);

    void deleteRol(Integer id);
}
