package com.blumbit.compras_ventas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.common.dto.PageableRequest;
import com.blumbit.compras_ventas.common.dto.PageableResponse;
import com.blumbit.compras_ventas.dto.ProductoFilterCriteria;
import com.blumbit.compras_ventas.dto.ProductoRequest;
import com.blumbit.compras_ventas.dto.ProductoResponse;
import com.blumbit.compras_ventas.entity.Producto;
import com.blumbit.compras_ventas.repository.CategoriaRepository;
import com.blumbit.compras_ventas.repository.ProductoRepository;
import com.blumbit.compras_ventas.repository.specification.ProductoSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository;

    @Override
    public PageableResponse<ProductoResponse> getProductosPagination(
            PageableRequest<ProductoFilterCriteria> pageableRequest) {
        try {
            Sort sort = pageableRequest.getSortOrder().equalsIgnoreCase("asc")
            ? Sort.by(pageableRequest.getSortField()).ascending()
            : Sort.by(pageableRequest.getSortField()).descending();

            Pageable pageable = PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize(), sort);

            Specification<Producto> specification = null;
            if(pageableRequest.getCriterials() != null)
            {
                specification = ProductoSpecification.createSpecification(pageableRequest.getCriterials(), pageableRequest.getFilterValue());
            }

            Page<Producto> productoPage = productoRepository.findAll(specification, pageable);

            return PageableResponse.<ProductoResponse>builder()
            .content(productoPage.getContent().stream().map(ProductoResponse::fromEntity).toList())
            .pageNumber(productoPage.getNumber())
            .pageSize(productoPage.getSize())
            .totalElements(productoPage.getTotalElements())
            .totalPages(productoPage.getTotalPages())
            .build();
        } catch (Exception e) {
            throw new RuntimeException("error paginacion productos")
        }
    }

    @Override
    public List<ProductoResponse> createProducto(ProductoRequest productoRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProducto'");
    }

    @Override
    public List<ProductoResponse> getProductosByAlmacen(Integer almacenId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductosByAlmacen'");
    }





}
