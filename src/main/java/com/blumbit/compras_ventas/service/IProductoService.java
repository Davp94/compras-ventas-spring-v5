package com.blumbit.compras_ventas.service;

import java.util.List;

import com.blumbit.compras_ventas.common.dto.PageableRequest;
import com.blumbit.compras_ventas.common.dto.PageableResponse;
import com.blumbit.compras_ventas.dto.ProductoFilterCriteria;
import com.blumbit.compras_ventas.dto.ProductoRequest;
import com.blumbit.compras_ventas.dto.ProductoResponse;

public interface IProductoService {

    PageableResponse<ProductoResponse> getProductosPagination(PageableRequest<ProductoFilterCriteria> pageableRequest);

    ProductoResponse createProducto(ProductoRequest productoRequest);

    List<ProductoResponse> getProductosByAlmacen(Integer almacenId);

}
