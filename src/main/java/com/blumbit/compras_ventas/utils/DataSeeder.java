package com.blumbit.compras_ventas.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blumbit.compras_ventas.entity.Almacen;
import com.blumbit.compras_ventas.entity.AlmacenProducto;
import com.blumbit.compras_ventas.entity.Categoria;
import com.blumbit.compras_ventas.entity.ClienteProveedor;
import com.blumbit.compras_ventas.entity.ClienteProveedorTipo;
import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Producto;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.entity.Sucursal;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.AlmacenProductoRepository;
import com.blumbit.compras_ventas.repository.AlmacenRepository;
import com.blumbit.compras_ventas.repository.CategoriaRepository;
import com.blumbit.compras_ventas.repository.ClienteProveedorRepository;
import com.blumbit.compras_ventas.repository.PermisoRepository;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.ProductoRepository;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.repository.SucursalRepository;
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

    private final SucursalRepository sucursalRepository;

    private final AlmacenRepository almacenRepository;

    private final CategoriaRepository categoriaRepository;

    private final ProductoRepository productoRepository;

    private final AlmacenProductoRepository almacenProductoRepository;

    private final ClienteProveedorRepository clienteProveedorRepository;
    
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

        List<Sucursal> sucursales = new ArrayList<>();
        if (sucursalRepository.count() == 0) {
            sucursales = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                sucursales.add(buildSucursal(
                    String.format("Sucursal %d", i),
                    faker.address().streetAddress(),
                    faker.phoneNumber().phoneNumber(),
                    faker.address().city()
                ));
            }
            sucursales = sucursalRepository.saveAll(sucursales);
        } else {
            sucursales = sucursalRepository.findAll();
        }

        List<Almacen> almacenes = new ArrayList<>();
        if (almacenRepository.count() == 0) {
            almacenes = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                Sucursal sucursal = sucursales.get(random.nextInt(sucursales.size()));
                almacenes.add(Almacen.builder()
                    .nombre(String.format("Almacén %02d", i))
                    .codigo(String.format("ALM-%03d", i))
                    .descripcion(faker.lorem().sentence(8))
                    .direccion(faker.address().streetAddress())
                    .telefono(faker.phoneNumber().cellPhone())
                    .ciudad(faker.address().city())
                    .sucursal(sucursal)
                    .build());
            }
            almacenes = almacenRepository.saveAll(almacenes);
        } else {
            almacenes = almacenRepository.findAll();
        }

        List<Categoria> categorias = new ArrayList<>();
        if (categoriaRepository.count() == 0) {
            categorias = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                categorias.add(buildCategoria(
                    faker.commerce().department(),
                    faker.lorem().sentence(6)
                ));
            }
            categorias = categoriaRepository.saveAll(categorias);
        } else {
            categorias = categoriaRepository.findAll();
        }

        List<Producto> productos = new ArrayList<>();
        if (productoRepository.count() == 0) {
            productos = new ArrayList<>();
            for (int i = 1; i <= 1000; i++) {
                Categoria categoria = categorias.get(random.nextInt(categorias.size()));
                BigDecimal price = BigDecimal.valueOf(1 + random.nextDouble() * 199)
                    .setScale(2, RoundingMode.HALF_UP);
                productos.add(Producto.builder()
                    .nombre(String.format("%s %s", faker.commerce().productName(), i))
                    .descripcion(faker.commerce().material() + " " + faker.commerce().productName())
                    .unidadMedida(List.of("Unidad", "Kg", "Litro", "Metro").get(random.nextInt(4)))
                    .marca(faker.company().name())
                    .precioVentaActual(price)
                    .imagen(faker.internet().avatar())
                    .estado(true)
                    .categoria(categoria)
                    .build());
            }
            productos = productoRepository.saveAll(productos);
        } else {
            productos = productoRepository.findAll();
        }

        if (clienteProveedorRepository.count() == 0) {
            List<ClienteProveedor> clientesProveedores = new ArrayList<>();
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.CLIENTE, "Cliente Express SAC", "20612345678", "011223400", "Av. Comercio 101", "cliente.express@correo.com", true));
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.CLIENTE, "Compras Unidas SRL", "20598765432", "011223401", "Jr. Comercio 42", "compras.unidas@correo.com", true));
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.PROVEEDOR, "Proveedora Nacional SAC", "20456781234", "011223402", "Av. Proveedor 78", "ventas@proveedora.com", true));
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.PROVEEDOR, "Distribuciones Andinas SRL", "20412345678", "011223403", "Jr. Distribución 20", "contacto@andinas.com", true));
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.CLIENTE, "Retail Fast SAC", "20633322110", "011223404", "Av. Retail 56", "retail.fast@correo.com", true));
            clienteProveedorRepository.saveAll(clientesProveedores);
        }

        if (almacenProductoRepository.count() == 0 && !almacenes.isEmpty() && !productos.isEmpty()) {
            List<AlmacenProducto> almacenProductos = new ArrayList<>();
            for (Producto producto : productos) {
                Almacen almacen = almacenes.get(random.nextInt(almacenes.size()));
                almacenProductos.add(AlmacenProducto.builder()
                    .almacen(almacen)
                    .producto(producto)
                    .cantidadActual(10 + random.nextInt(491))
                    .fechaActualizacion(LocalDateTime.now().minusDays(random.nextInt(60)))
                    .unidadMedida(producto.getUnidadMedida() != null ? producto.getUnidadMedida() : "UN")
                    .precioVentaActual(producto.getPrecioVentaActual())
                    .build());
            }
            // add a few extra stock entries across more almacenes
            for (int i = 0; i < 200; i++) {
                Producto producto = productos.get(random.nextInt(productos.size()));
                Almacen almacen = almacenes.get(random.nextInt(almacenes.size()));
                almacenProductos.add(AlmacenProducto.builder()
                    .almacen(almacen)
                    .producto(producto)
                    .cantidadActual(5 + random.nextInt(95))
                    .fechaActualizacion(LocalDateTime.now().minusDays(random.nextInt(60)))
                    .unidadMedida(producto.getUnidadMedida() != null ? producto.getUnidadMedida() : "UN")
                    .precioVentaActual(producto.getPrecioVentaActual())
                    .build());
            }
            almacenProductoRepository.saveAll(almacenProductos);
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

        if (clienteProveedorRepository.count() == 0) {
            List<ClienteProveedor> clientesProveedores = new ArrayList<>();
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.CLIENTE, "Cliente Express SAC", "20612345678", "011223400", "Av. Comercio 101", "cliente.express@correo.com", true));
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.CLIENTE, "Compras Unidas SRL", "20598765432", "011223401", "Jr. Comercio 42", "compras.unidas@correo.com", true));
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.PROVEEDOR, "Proveedora Nacional SAC", "20456781234", "011223402", "Av. Proveedor 78", "ventas@proveedora.com", true));
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.PROVEEDOR, "Distribuciones Andinas SRL", "20412345678", "011223403", "Jr. Distribución 20", "contacto@andinas.com", true));
            clientesProveedores.add(buildClienteProveedor(ClienteProveedorTipo.CLIENTE, "Retail Fast SAC", "20633322110", "011223404", "Av. Retail 56", "retail.fast@correo.com", true));
            clienteProveedorRepository.saveAll(clientesProveedores);
        }

        if (almacenProductoRepository.count() == 0 && !almacenes.isEmpty() && !productos.isEmpty()) {
            List<AlmacenProducto> almacenProductos = new ArrayList<>();
            for (Almacen almacen : almacenes) {
                List<Producto> shuffleProductos = new ArrayList<>(productos);
                Collections.shuffle(shuffleProductos, random);
                for (int i = 0; i < Math.min(4, shuffleProductos.size()); i++) {
                    Producto producto = shuffleProductos.get(i);
                    almacenProductos.add(AlmacenProducto.builder()
                        .almacen(almacen)
                        .producto(producto)
                        .cantidadActual(10 + random.nextInt(91))
                        .fechaActualizacion(LocalDateTime.now().minusDays(random.nextInt(30)))
                        .unidadMedida(producto.getUnidadMedida() != null ? producto.getUnidadMedida() : "UN")
                        .precioVentaActual(producto.getPrecioVentaActual())
                        .build());
                }
            }
            almacenProductoRepository.saveAll(almacenProductos);
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

    private Sucursal buildSucursal(String nombre, String direccion, String telefono, String ciudad) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(nombre);
        sucursal.setDireccion(direccion);
        sucursal.setTelefono(telefono);
        sucursal.setCiudad(ciudad);
        return sucursal;
    }

    private Categoria buildCategoria(String nombre, String descripcion) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        return categoria;
    }

    private ClienteProveedor buildClienteProveedor(ClienteProveedorTipo tipo, String razonSocial, String nroIdentificacion, String telefono, String direccion, String correo, Boolean estado) {
        ClienteProveedor clienteProveedor = new ClienteProveedor();
        clienteProveedor.setTipo(tipo);
        clienteProveedor.setRazonSocial(razonSocial);
        clienteProveedor.setNroIdentificacion(nroIdentificacion);
        clienteProveedor.setTelefono(telefono);
        clienteProveedor.setDireccion(direccion);
        clienteProveedor.setCorreo(correo);
        clienteProveedor.setEstado(estado);
        return clienteProveedor;
    }

}
