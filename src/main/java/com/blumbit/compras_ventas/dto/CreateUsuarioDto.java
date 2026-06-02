package com.blumbit.compras_ventas.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.validation.UniqueName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsuarioDto {

   @NotBlank(message = "El nombre de usuario es requerido") 
   @Size(max=50, message = "El username debe tener maximo 50 caracteres")
   @UniqueName(fieldName = "username")
   private String username;

   @NotBlank(message = "El correo electrónico es requerido")
   @Email(message = "El correo no tiene un formato valido")
   private String correo;

   @NotBlank(message = "El correo electrónico es requerido")
   @Pattern(
       regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,16}$",
       message = "La contraseña debe tener entre 8 y 16 caracteres, e incluir al menos una letra mayúscula, una minúscula, un número y un carácter especial."
   )
   private String password;

   @NotBlank(message = "El nombre es requerido")
   @Size(max=100, message = "El nombre debe tener maximo 100 caracteres")
   private String nombres;

   @NotBlank(message = "El apellido es requerido")
   @Size(max=100, message = "Los apellidos deben tener maximo 100 caracteres")
   private String apellidos;

   @Pattern(
       regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$",
       message = "La fecha de nacimiento debe tener el formato dd/MM/yyyy y ser una fecha válida."
   )
   private String fechaNacimiento;

   @Size(max=20, message = "El género debe tener maximo 20 caracteres")
   private String genero;

   @Size(max=20, message = "El teléfono debe tener maximo 20 caracteres")
   private String telefono;

   @Size(max=100, message = "La dirección debe tener maximo 100 caracteres")    
   private String direccion;

   @NotBlank(message = "La nacionalidad es requerido")
   @Size(max=50, message = "La nacionalidad debe tener maximo 50 caracteres")
   private String nacionalidad;

   //TODO add documentos

    public static Usuario toEntityUsuario(CreateUsuarioDto createUsuarioDto){
        return Usuario.builder()
        .nombre(createUsuarioDto.getUsername())
        .email(createUsuarioDto.getCorreo())
        .password(createUsuarioDto.getPassword())
        .build();
    }

    public static Persona toEntityPersona(CreateUsuarioDto createUsuarioDto, Usuario usuario){
        return Persona.builder()
        .nombres(createUsuarioDto.getNombres())
        .apellidos(createUsuarioDto.getApellidos())
        .fechaNacimiento(LocalDate.parse(createUsuarioDto.getFechaNacimiento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .genero(createUsuarioDto.getGenero())
        .telefono(createUsuarioDto.getTelefono())
        .direccion(createUsuarioDto.getDireccion())
        .nacionalidad(createUsuarioDto.getNacionalidad())
        .usuario(usuario)
        .build();
    }
}
