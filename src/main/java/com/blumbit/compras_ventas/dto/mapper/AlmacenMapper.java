package com.blumbit.compras_ventas.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.blumbit.compras_ventas.dto.AlmacenDto;
import com.blumbit.compras_ventas.dto.CreateAlmacenDto;
import com.blumbit.compras_ventas.entity.Almacen;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface AlmacenMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    Almacen toEntity(CreateAlmacenDto request);

    AlmacenDto toResponseDto(Almacen almacen);
}
