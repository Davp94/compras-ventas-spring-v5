package com.blumbit.compras_ventas.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PageableResponse<T> {

    private List<T> content;
    //metadata
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

}
