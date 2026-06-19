package com.blumbit.compras_ventas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.MovimientoRequest;
import com.blumbit.compras_ventas.dto.NotaRequest;
import com.blumbit.compras_ventas.dto.NotaResponse;
import com.blumbit.compras_ventas.entity.AlmacenProducto;
import com.blumbit.compras_ventas.entity.ClienteProveedor;
import com.blumbit.compras_ventas.entity.Movimiento;
import com.blumbit.compras_ventas.entity.Nota;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.exception.ResourceNotFoundException;
import com.blumbit.compras_ventas.repository.AlmacenProductoRepository;
import com.blumbit.compras_ventas.repository.AlmacenRepository;
import com.blumbit.compras_ventas.repository.ClienteProveedorRepository;
import com.blumbit.compras_ventas.repository.MovimientoRepository;
import com.blumbit.compras_ventas.repository.NotaRepository;
import com.blumbit.compras_ventas.repository.ProductoRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
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

    private final AlmacenRepository almacenRepository;

    private final AlmacenProductoRepository almacenProductoRepository;
    
    @Override
    public List<NotaResponse> getAllNotas() {
        return notaRepository.findAll().stream().map(NotaResponse::fromEntity)
        .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotaResponse createNota(NotaRequest notaRequest) {
        //create nota
        Usuario usuario = usuarioRepository.findById(notaRequest.getUsuarioId())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ClienteProveedor clienteProveedor = clienteProveedorRepository.findById(notaRequest.getClienteProveedorId())
        .orElseThrow(() -> new RuntimeException("Cluiente proveedor no encontrado"));

        Nota nota = NotaRequest.toEntity(notaRequest);
        nota.setUsuario(usuario);
        nota.setClienteProveedor(clienteProveedor);
        nota = notaRepository.save(nota);
        //create movimientos
        List<Movimiento> movimientosCreated = new ArrayList<>();
        for(MovimientoRequest movimientoRequest : notaRequest.getMovimientos()) {
            if(validStock(movimientoRequest))
            {
                Movimiento movimientoToCreate = MovimientoRequest.toEntity(movimientoRequest);
                    movimientoToCreate.setNota(nota);
                    movimientoToCreate.setProducto(productoRepository.findById(movimientoRequest.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
                    movimientoToCreate.setAlmacen(almacenRepository.findById(movimientoRequest.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado")));
                movimientosCreated.add(movimientoRepository.save(movimientoToCreate));
            }
           
        }
        //update stock
        for(Movimiento movimiento : movimientosCreated) {
            AlmacenProducto almacenProductoRetrieved = almacenProductoRepository.findByAlmacen_IdAndProducto_Id(
                movimiento.getAlmacen().getId(), movimiento.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Almacen producto no encontrado"));

                switch (movimiento.getTipoMovimiento()) {
                    case COMPRA:
                        almacenProductoRetrieved.setCantidadActual(almacenProductoRetrieved.getCantidadActual()+movimiento.getCantidad());
                        break;
                    case VENTA:
                        almacenProductoRetrieved.setCantidadActual(almacenProductoRetrieved.getCantidadActual()-movimiento.getCantidad());
                        break;
                    default:
                        break;
                }
                almacenProductoRepository.save(almacenProductoRetrieved);
        }
        //return nota
        return NotaResponse.fromEntity(nota);
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

    private boolean validStock(MovimientoRequest movimientoRequest) {
        try {
            if(movimientoRequest.getTipoMovimiento().equals("VENTA")) {
                boolean valid = movimientoRequest.getTipoMovimiento().equals("VENTA") &&
                movimientoRequest.getCantidad() <= almacenProductoRepository.findByAlmacen_IdAndProducto_Id(
                    movimientoRequest.getAlmacenId(), movimientoRequest.getProductoId()).get().getCantidadActual();
                return valid;
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el stock");
        }
    }

}
