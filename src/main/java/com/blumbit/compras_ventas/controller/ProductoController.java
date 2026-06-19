package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.common.dto.PageableRequest;
import com.blumbit.compras_ventas.common.dto.PageableResponse;
import com.blumbit.compras_ventas.dto.ProductoFilterCriteria;
import com.blumbit.compras_ventas.dto.ProductoRequest;
import com.blumbit.compras_ventas.dto.ProductoResponse;
import com.blumbit.compras_ventas.service.IProductoService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> createProducto(@ModelAttribute ProductoRequest productoRequest) {
        
        return ResponseEntity.ok(productoService.createProducto(productoRequest));
    }

    @GetMapping("/almacen/{id}")
    public ResponseEntity<List<ProductoResponse>> findProductosByAlmacen(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.getProductosByAlmacen(id));
    }

    @GetMapping("/paginacion")
    public ResponseEntity<PageableResponse<ProductoResponse>> getProductosPaginacion(
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortOrder,
        @RequestParam(required = false) String filterValue,
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String descripcion,
        @RequestParam(required = false) String codigoBarra,
        @RequestParam(required = false) String marca,
        @RequestParam(required = false) String nombreCategoria
    ) {

        ProductoFilterCriteria criteria = ProductoFilterCriteria.builder()
        .nombre(nombre)
        .descripcion(descripcion)
        .codigoBarra(codigoBarra)
        .marca(marca)
        .nombreCategoria(nombreCategoria)
        .build();
        PageableRequest<ProductoFilterCriteria> pageableRequest = PageableRequest.<ProductoFilterCriteria>builder()
        .pageNumber(pageNumber)
        .pageSize(pageSize)
        .sortField(sortField)
        .sortOrder(sortOrder)
        .criterials(criteria)
        .build();
        return ResponseEntity.ok(productoService.getProductosPagination(pageableRequest));
    }
    
    
    
}
