package com.blumbit.compras_ventas.exception;

public class BadRequestException extends DomainException {

    public BadRequestException(String message) {
        super(message, 400, "BAD_REQUEST");
    }
}
