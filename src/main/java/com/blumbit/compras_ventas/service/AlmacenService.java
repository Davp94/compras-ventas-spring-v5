package com.blumbit.compras_ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.AlmacenDto;
import com.blumbit.compras_ventas.dto.CreateAlmacenDto;
import com.blumbit.compras_ventas.entity.Almacen;
import com.blumbit.compras_ventas.entity.Sucursal;
import com.blumbit.compras_ventas.exception.ConflictException;
import com.blumbit.compras_ventas.exception.ResourceNotFoundException;
import com.blumbit.compras_ventas.repository.AlmacenRepository;
import com.blumbit.compras_ventas.repository.SucursalRepository;

@Service
public class AlmacenService implements IAlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<AlmacenDto> listarAlmacenes() {
        return almacenRepository.findAll().stream()
                .map(AlmacenDto::fromEntity)
                .toList();
    }

    @Override
    public AlmacenDto obtenerAlmacenPorId(Integer id) {
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Almacen", id));
        return AlmacenDto.fromEntity(almacen);
    }

    @Override
    public AlmacenDto crearAlmacen(CreateAlmacenDto dto) {
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId())
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", dto.getSucursalId()));
        Almacen almacen = new Almacen();
        almacen.setNombre(dto.getNombre());
        almacen.setCodigo(dto.getCodigo());
        almacen.setDescripcion(dto.getDescripcion());
        almacen.setDireccion(dto.getDireccion());
        almacen.setTelefono(dto.getTelefono());
        almacen.setCiudad(dto.getCiudad());
        almacen.setSucursal(sucursal);
        try {
            return AlmacenDto.fromEntity(almacenRepository.save(almacen));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("No se pudo crear el almacén");
        }
    }

    @Override
    public AlmacenDto actualizarAlmacen(Integer id, CreateAlmacenDto dto) {
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Almacen", id));
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId())
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", dto.getSucursalId()));
        almacen.setNombre(dto.getNombre());
        almacen.setCodigo(dto.getCodigo());
        almacen.setDescripcion(dto.getDescripcion());
        almacen.setDireccion(dto.getDireccion());
        almacen.setTelefono(dto.getTelefono());
        almacen.setCiudad(dto.getCiudad());
        almacen.setSucursal(sucursal);
        try {
            return AlmacenDto.fromEntity(almacenRepository.save(almacen));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("No se pudo actualizar el almacén");
        }
    }

    @Override
    public void deleteAlmacen(Integer id) {
        if (!almacenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Almacen", id);
        }
        almacenRepository.deleteById(id);
    }
}
