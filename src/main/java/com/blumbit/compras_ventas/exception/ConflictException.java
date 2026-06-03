package com.blumbit.compras_ventas.exception;

public class ConflictException extends DomainException {

    public ConflictException(String message) {
        super(message, 409, "CONFLICT");
    }
}
