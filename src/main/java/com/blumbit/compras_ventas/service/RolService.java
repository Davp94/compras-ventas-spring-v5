package com.blumbit.compras_ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.CreateRolDto;
import com.blumbit.compras_ventas.dto.RolDto;
import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.exception.BadRequestException;
import com.blumbit.compras_ventas.exception.ConflictException;
import com.blumbit.compras_ventas.exception.ResourceNotFoundException;
import com.blumbit.compras_ventas.repository.PermisoRepository;
import com.blumbit.compras_ventas.repository.RolRepository;

@Service
public class RolService implements IRolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public List<RolDto> listarRoles() {
        return rolRepository.findAll().stream()
                .map(RolDto::fromEntity)
                .toList();
    }

    @Override
    public RolDto obtenerRolPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", id));
        return RolDto.fromEntity(rol);
    }

    @Override
    public RolDto crearRol(CreateRolDto dto) {
        List<Permiso> permisos = obtenerPermisosValidos(dto.getPermisosIds());
        Rol rol = new Rol();
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
        rol.setPermisos(permisos);
        try {
            return RolDto.fromEntity(rolRepository.save(rol));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("El nombre del rol ya existe");
        }
    }

    @Override
    public RolDto actualizarRol(Integer id, CreateRolDto dto) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", id));
        List<Permiso> permisos = obtenerPermisosValidos(dto.getPermisosIds());
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
        rol.setPermisos(permisos);
        try {
            return RolDto.fromEntity(rolRepository.save(rol));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("El nombre del rol ya existe");
        }
    }

    @Override
    public void deleteRol(Integer id) {
        if (!rolRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rol", id);
        }
        rolRepository.deleteById(id);
    }

    private List<Permiso> obtenerPermisosValidos(List<Integer> permisosIds) {
        if (permisosIds == null || permisosIds.isEmpty()) {
            return List.of();
        }
        List<Permiso> permisos = permisoRepository.findAllById(permisosIds);
        if (permisos.size() != permisosIds.size()) {
            throw new BadRequestException("Uno o más IDs de permiso no existen");
        }
        return permisos;
    }
}
