package com.blumbit.compras_ventas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.CreateUsuarioDto;
import com.blumbit.compras_ventas.dto.UsuarioDto;
import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.exception.ConflictException;
import com.blumbit.compras_ventas.exception.ResourceNotFoundException;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            return UsuarioDto.fromEntityUsuario(usuario, usuario.getPersona());
        }).toList();
    }

    @Override
    public UsuarioDto getUsuarioById(Integer id) {
        try {
            log.info("Buscando usuario con id: {}", id);
            return usuarioRepository.findById(id).map(usuario -> {
            return UsuarioDto.fromEntityUsuario(usuario, usuario.getPersona());
            }).orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        } catch (ResourceNotFoundException e) {
            throw e;
        }
       
    }

    @Transactional
    @Override
    public UsuarioDto createusuario(CreateUsuarioDto createUsuarioDto) {
        if(isEmailExist(createUsuarioDto.getCorreo())){
            log.error("El correo electronico ya está en uso");
            throw new RuntimeException("El correo electronico ya está en uso");
        }
        //add roles to usuario
        List<Rol> roles = rolRepository.findAllById(createUsuarioDto.getRoles());


        Usuario usuario = CreateUsuarioDto.toEntityUsuario(createUsuarioDto);
                usuario.setRoles(roles);
        usuario = usuarioRepository.save(usuario);
        Persona persona = CreateUsuarioDto.toEntityPersona(createUsuarioDto, usuario);
        persona = personaRepository.save(persona);
        //TODO add documento save
        return UsuarioDto.fromEntityUsuario(usuario, persona);
    }

    @Transactional
    @Override
    public UsuarioDto updateUsuario(Integer id, CreateUsuarioDto createUsuarioDto) {
        if(isEmailExist(createUsuarioDto.getCorreo())){
            throw new RuntimeException("El correo electronico ya está en uso");
        }
        Usuario usuarioRetrieved = usuarioRepository.findById(id).orElseThrow(() 
        -> new RuntimeException("Usuario no encontrado"));
        usuarioRetrieved.setRoles(rolRepository.findAllById(createUsuarioDto.getRoles()));
        usuarioRetrieved.setEmail(createUsuarioDto.getCorreo());
        usuarioRetrieved.setPassword(createUsuarioDto.getPassword());
        usuarioRetrieved.setNombre(createUsuarioDto.getNombres());
        Persona personaRetrieved = personaRepository.findByUsuario(usuarioRetrieved);
        personaRetrieved.setNombres(createUsuarioDto.getNombres());
        personaRetrieved.setApellidos(createUsuarioDto.getApellidos());
        personaRetrieved.setTelefono(createUsuarioDto.getTelefono());
        personaRetrieved.setDireccion(createUsuarioDto.getDireccion());
        personaRetrieved.setNacionalidad(createUsuarioDto.getNacionalidad());
        personaRetrieved.setFechaNacimiento(LocalDate.parse(createUsuarioDto.getFechaNacimiento()));
        return UsuarioDto.fromEntityUsuario(usuarioRepository.save(usuarioRetrieved), personaRepository.save(personaRetrieved));
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // VALIDATIONS
    boolean isEmailExist(String email) {
        return usuarioRepository.existByEmail(email);
    }

}
