package com.blumbit.compras_ventas.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CustomErrorResponse<T> {

    private int statusCode; //400
    private String errorCode; //Bad Request
    private String timestamp;
    private T message;
    private String path;


}
