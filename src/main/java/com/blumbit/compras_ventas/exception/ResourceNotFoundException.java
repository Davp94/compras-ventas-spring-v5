package com.blumbit.compras_ventas.exception;

public class ResourceNotFoundException extends DomainException {

    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " con id " + id + " no encontrado", 404, "RESOURCE_NOT_FOUND");
        //TODO Auto-generated constructor stub
    }

}
