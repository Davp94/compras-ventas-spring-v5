package com.blumbit.compras_ventas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.NotaRequest;
import com.blumbit.compras_ventas.dto.NotaResponse;
import com.blumbit.compras_ventas.entity.Nota;
import com.blumbit.compras_ventas.exception.ResourceNotFoundException;
import com.blumbit.compras_ventas.repository.ClienteProveedorRepository;
import com.blumbit.compras_ventas.repository.MovimientoRepository;
import com.blumbit.compras_ventas.repository.NotaRepository;
import com.blumbit.compras_ventas.repository.ProductoRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotaService implements INotaService {
    
    private final NotaRepository notaRepository;

    private final ClienteProveedorRepository clienteProveedorRepository;

    private final UsuarioRepository usuarioRepository;

    private final ProductoRepository productoRepository;

    private final MovimientoRepository movimientoRepository;

    private final IPdfService pdfService;
    
    @Override
    public List<NotaResponse> getAllNotas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllNotas'");
    }

    @Override
    public NotaResponse createNota(NotaRequest notaRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNota'");
    }

    @Override
    public byte[] generateReport(Integer notaId) {
        Nota notaRetrieved = notaRepository.findById(notaId)
        .orElseThrow(() -> new ResourceNotFoundException("Notas", notaId));
        Map<String, Object> dataReport = new HashMap<>();
        dataReport.put("nota", notaRetrieved);
        dataReport.put("movimientos", movimientoRepository.findByNota_Id(notaId));

        return pdfService.generatePdfReport("nota-report", dataReport);
    }


}
