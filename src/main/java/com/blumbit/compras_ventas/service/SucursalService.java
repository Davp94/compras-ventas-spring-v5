package com.blumbit.compras_ventas.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.CreateSucursalDto;
import com.blumbit.compras_ventas.dto.SucursalDto;
import com.blumbit.compras_ventas.entity.Sucursal;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.exception.BadRequestException;
import com.blumbit.compras_ventas.exception.ConflictException;
import com.blumbit.compras_ventas.exception.ResourceNotFoundException;
import com.blumbit.compras_ventas.repository.SucursalRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;

@Service
public class SucursalService implements ISucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<SucursalDto> listarSucursales() {
        return sucursalRepository.findAll().stream()
                .map(SucursalDto::fromEntity)
                .toList();
    }

    @Override
    public SucursalDto obtenerSucursalPorId(Integer id) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", id));
        return SucursalDto.fromEntity(sucursal);
    }

    @Override
    public SucursalDto crearSucursal(CreateSucursalDto dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());
        sucursal.setCiudad(dto.getCiudad());
        if (dto.getUsuariosIds() != null) {
            List<Usuario> usuarios = validarUsuarios(dto.getUsuariosIds());
            sincronizarUsuarios(sucursal, usuarios);
        }
        try {
            return SucursalDto.fromEntity(sucursalRepository.save(sucursal));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("El nombre de la sucursal ya existe");
        }
    }

    @Override
    public SucursalDto actualizarSucursal(Integer id, CreateSucursalDto dto) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal", id));
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());
        sucursal.setCiudad(dto.getCiudad());
        if (dto.getUsuariosIds() != null) {
            List<Usuario> nuevosUsuarios = validarUsuarios(dto.getUsuariosIds());
            sincronizarUsuarios(sucursal, nuevosUsuarios);
        }
        try {
            return SucursalDto.fromEntity(sucursalRepository.save(sucursal));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("El nombre de la sucursal ya existe");
        }
    }

    @Override
    public void deleteSucursal(Integer id) {
        if (!sucursalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sucursal", id);
        }
        sucursalRepository.deleteById(id);
    }

    private List<Usuario> validarUsuarios(List<Integer> usuariosIds) {
        List<Usuario> usuarios = usuarioRepository.findAllById(usuariosIds);
        if (usuarios.size() != usuariosIds.size()) {
            throw new BadRequestException("Uno o más IDs de usuario no existen");
        }
        return usuarios;
    }

    private void sincronizarUsuarios(Sucursal sucursal, List<Usuario> nuevosUsuarios) {
        List<Usuario> usuariosActuales = sucursal.getUsuarios();
        if (usuariosActuales == null) {
            usuariosActuales = List.of();
        }

        Set<Integer> nuevosIds = nuevosUsuarios.stream().map(Usuario::getId).collect(Collectors.toSet());
        Set<Integer> actualesIds = usuariosActuales.stream().map(Usuario::getId).collect(Collectors.toSet());

        for (Usuario usuario : usuariosActuales) {
            if (!nuevosIds.contains(usuario.getId())) {
                usuario.getSucursales().remove(sucursal);
            }
        }
        for (Usuario usuario : nuevosUsuarios) {
            if (!actualesIds.contains(usuario.getId())) {
                usuario.getSucursales().add(sucursal);
            }
        }
        sucursal.setUsuarios(nuevosUsuarios);
    }
}
