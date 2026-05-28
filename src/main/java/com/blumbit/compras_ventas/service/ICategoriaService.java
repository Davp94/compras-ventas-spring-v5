package com.blumbit.compras_ventas.service;

import java.util.List;

import com.blumbit.compras_ventas.dto.CategoriaDto;
import com.blumbit.compras_ventas.dto.CreateCategoriaDto;

public interface ICategoriaService {

    List<CategoriaDto> listarCategorias();

    CategoriaDto obtenerCategoriaPorId(Integer id);

    CategoriaDto crearCategoria(CreateCategoriaDto categoria);

    CategoriaDto actualizarCategoria(Integer id, CreateCategoriaDto categoriaActualizada);

    void deleteCategoria(Integer id);

}
