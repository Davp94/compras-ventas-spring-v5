package com.blumbit.compras_ventas.service;

import java.util.Map;

public interface IPdfService {

    byte[] generatePdfReport(String templateName, Map<String, Object> data);
}
