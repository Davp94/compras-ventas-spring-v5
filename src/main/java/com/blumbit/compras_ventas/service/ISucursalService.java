package com.blumbit.compras_ventas.service;

import java.util.List;

import com.blumbit.compras_ventas.dto.CreateSucursalDto;
import com.blumbit.compras_ventas.dto.SucursalDto;

public interface ISucursalService {

    List<SucursalDto> listarSucursales();

    SucursalDto obtenerSucursalPorId(Integer id);

    SucursalDto crearSucursal(CreateSucursalDto sucursal);

    SucursalDto actualizarSucursal(Integer id, CreateSucursalDto sucursalActualizada);

    void deleteSucursal(Integer id);
}
