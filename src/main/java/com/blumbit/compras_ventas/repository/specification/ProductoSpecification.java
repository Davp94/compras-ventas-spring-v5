package com.blumbit.compras_ventas.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.blumbit.compras_ventas.dto.ProductoFilterCriteria;
import com.blumbit.compras_ventas.entity.Producto;

import jakarta.persistence.criteria.Predicate;

public class ProductoSpecification {

    public static Specification<Producto> createSpecification(ProductoFilterCriteria criterials, String filterValue){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList();

            if(filterValue != null && !filterValue.trim().isEmpty()){
                String likeFilterValue = "%" + filterValue.toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                    //where nombre like '%{filtervalue}% or descripciooon or ....'
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), likeFilterValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("descripcion")), likeFilterValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("marca")), likeFilterValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("categoria").get("nombre")), likeFilterValue)
                ));
            }

            if(criterials.getNombre() != null && !criterials.getNombre().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), 
                "%"+criterials.getNombre().toLowerCase()+"%"));
            }

            if(criterials.getDescripcion() != null && !criterials.getDescripcion().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("descripcion")), 
                "%"+criterials.getDescripcion().toLowerCase()+"%"));
            }

            if(criterials.getMarca() != null && !criterials.getMarca().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("marca")), 
                "%"+criterials.getMarca().toLowerCase()+"%"));
            }
            
            if(criterials.getNombreCategoria() != null && !criterials.getNombreCategoria().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("categoria").get("nombre")), 
                "%"+criterials.getNombreCategoria().toLowerCase()+"%"));
            }

            return criteriaBuilder.and(predicates);
        };
    }
}
