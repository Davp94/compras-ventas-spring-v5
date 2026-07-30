package com.blumbit.compras_ventas.dto;

import com.blumbit.compras_ventas.entity.ClienteProveedor;
import com.blumbit.compras_ventas.entity.ClienteProveedorTipo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteProveedorDto {

    private Integer id;
    private ClienteProveedorTipo tipo;
    private String razonSocial;
    private String nroIdentificacion;
    private String telefono;
    private String direccion;
    private String correo;
    private Boolean estado;

    public static ClienteProveedorDto fromEntity(ClienteProveedor clienteProveedor) {
        return ClienteProveedorDto.builder()
                .id(clienteProveedor.getId())
                .tipo(clienteProveedor.getTipo())
                .razonSocial(clienteProveedor.getRazonSocial())
                .nroIdentificacion(clienteProveedor.getNroIdentificacion())
                .telefono(clienteProveedor.getTelefono())
                .direccion(clienteProveedor.getDireccion())
                .correo(clienteProveedor.getCorreo())
                .estado(clienteProveedor.getEstado())
                .build();
    }
}
