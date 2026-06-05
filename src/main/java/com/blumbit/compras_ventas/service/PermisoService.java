package com.blumbit.compras_ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blumbit.compras_ventas.dto.CreatePermisoDto;
import com.blumbit.compras_ventas.dto.PermisoDto;
import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.exception.ConflictException;
import com.blumbit.compras_ventas.exception.ResourceNotFoundException;
import com.blumbit.compras_ventas.repository.PermisoRepository;

@Service
public class PermisoService implements IPermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public List<PermisoDto> getAllPermisos() {
        return permisoRepository.findAll().stream()
                .map(PermisoDto::fromEntity)
                .toList();
    }

    @Override
    public PermisoDto getPermisoById(Integer id) {
        return permisoRepository.findById(id)
                .map(PermisoDto::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Permiso", id));
    }

    @Transactional
    @Override
    public PermisoDto createPermiso(CreatePermisoDto createPermisoDto) {
        if (permisoRepository.existsByNombre(createPermisoDto.getNombre())) {
            throw new ConflictException("El nombre del permiso ya existe");
        }
        Permiso permiso = CreatePermisoDto.toEntity(createPermisoDto);
        try {
            permiso = permisoRepository.save(permiso);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("No se pudo crear el permiso");
        }
        return PermisoDto.fromEntity(permiso);
    }

    @Transactional
    @Override
    public PermisoDto updatePermiso(Integer id, CreatePermisoDto createPermisoDto) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permiso", id));

        Permiso existingByName = permisoRepository.findByNombre(createPermisoDto.getNombre());
        if (existingByName != null && !existingByName.getId().equals(id)) {
            throw new ConflictException("El nombre del permiso ya existe");
        }

        permiso.setNombre(createPermisoDto.getNombre());
        permiso.setRecurso(createPermisoDto.getRecurso());
        permiso.setAction(createPermisoDto.getAction());
        try {
            permiso = permisoRepository.save(permiso);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("No se pudo actualizar el permiso");
        }
        return PermisoDto.fromEntity(permiso);
    }

    @Override
    public void deletePermiso(Integer id) {
        if (!permisoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permiso", id);
        }
        permisoRepository.deleteById(id);
    }
}
