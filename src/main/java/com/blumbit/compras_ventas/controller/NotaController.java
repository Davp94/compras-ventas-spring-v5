package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.NotaRequest;
import com.blumbit.compras_ventas.dto.NotaResponse;
import com.blumbit.compras_ventas.service.INotaService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/nota")
public class NotaController {

    private final INotaService notaService;

    @GetMapping("{notaId}/report")
    public ResponseEntity<byte[]> getReportNota(@PathVariable Integer notaId) {
        return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=nota-report.pdf")
        .body(notaService.generateReport(notaId));
    }

    @GetMapping
    public ResponseEntity<List<NotaResponse>> getAllNotas(@RequestParam String param) {
        return ResponseEntity.ok(notaService.getAllNotas());
    }
    
    @PostMapping
    public ResponseEntity<NotaResponse> createNota(@RequestBody NotaRequest notaRequest) {
        
        return ResponseEntity.ok(notaService.createNota(notaRequest));
    }
    
    

}
