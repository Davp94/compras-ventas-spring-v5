package com.blumbit.compras_ventas.service;

import java.util.List;

import com.blumbit.compras_ventas.dto.NotaRequest;
import com.blumbit.compras_ventas.dto.NotaResponse;

public interface INotaService {

    List<NotaResponse> getAllNotas();

    NotaResponse createNota(NotaRequest notaRequest);

    byte[] generateReport(Integer notaId);
}
