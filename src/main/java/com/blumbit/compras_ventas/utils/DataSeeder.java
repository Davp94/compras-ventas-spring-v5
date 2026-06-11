package com.blumbit.compras_ventas.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.PermisoRepository;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;

    private final PermisoRepository permisoRepository;

    private final RolRepository rolRepository;

    private final PersonaRepository personaRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        List<String> acciones = List.of("crear","actualizar", "listar", "eliminar");

        List<String> recursos = List.of("usuarios", "roles", "permisos", "productos",
            "categorias", "notas", "movimientos", "sucursales", "almacenes"
        );

        List<Permiso> permisos = new ArrayList<>();

        if(permisoRepository.count() == 0) {
            for(String recurso : recursos) {
                for(String accion : acciones) {
                    Permiso permiso = Permiso.builder()
                    .nombre(accion.toUpperCase()+"_"+recurso.toUpperCase())
                    .recurso(recurso)
                    .action(accion)
                    .build();
                    permisos.add(permisoRepository.save(permiso));
                }
            }
        }

        List<Rol> roles = new ArrayList<>();

        if(rolRepository.count() == 0) {

            //rol ADMIN
            Rol adminRol = Rol.builder()
            .nombre("ADMIN")
            .descripcion("Rol administrador del sistema")
            .permisos(permisos)
            .build();
            roles.add(rolRepository.save(adminRol));

            //rol VENDEDOR
            List<Permiso> permisosVendedor = permisos.stream()
                .filter(p->(List.of("productos", "categorias", "movimientos", "notas").contains(p.getRecurso())
                && List.of("listar", "crear").contains(p.getAction()))).collect(Collectors.toList());
            
            Rol vendedorRol = Rol.builder()
            .nombre("VENDEDOR")
            .descripcion("Rol vendedor para gestion de compras ventas")
            .permisos(permisosVendedor)
            .build();
            roles.add(rolRepository.save(vendedorRol));

            //rol RRHH
            List<Permiso> permisosRrhh = permisos.stream()
                .filter(p->(List.of("usuarios", "roles", "permisos").contains(p.getRecurso())
                )).collect(Collectors.toList());
            
            Rol rrhhRol = Rol.builder()
            .nombre("RRHH")
            .descripcion("Rol usuario de RRHH")
            .permisos(permisosRrhh)
            .build();
            roles.add(rolRepository.save(rrhhRol));
        }

        if(usuarioRepository.count() == 0) {
            for(int i = 0; i < 10; i++) {
                
                String correo = faker.internet().emailAddress();

                Usuario usuario = Usuario.builder()
                    .email(correo)
                    .nombre(faker.name().username())
                    .password(passwordEncoder.encode("123456"))
                    .roles(roles)
                    .build();
                
                usuario = usuarioRepository.save(usuario);

                Persona persona = Persona.builder()
                .apellidos(faker.name().lastName())
                .nombres(faker.name().firstName())
                .direccion(faker.address().fullAddress())
                .genero(List.of("masculino", "femenino", "otros").get(random.nextInt(3)))
                .telefono(faker.phoneNumber().cellPhone())
                .usuario(usuario)
                .nacionalidad(faker.address().country())
                .fechaNacimiento(LocalDate.now().minusYears(18 + random.nextInt(50-18+1)).minusDays(random.nextInt(365)))
                .build();
                personaRepository.save(persona);
            }
        }  
    }

}
