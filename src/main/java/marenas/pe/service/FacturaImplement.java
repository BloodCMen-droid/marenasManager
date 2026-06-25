package marenas.pe.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.DetallePedido;
import marenas.pe.model.Factura;
import marenas.pe.repository.IFacturaRepository;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import marenas.pe.dto.FacturaPDFDTO;

@Service
public class FacturaImplement implements IFacturaService {
	
	
	@Autowired
    private IFacturaRepository facturaRepo;

    @Override
    public Factura crearFactura(Factura factura){
        return facturaRepo.save(factura);
    }

    @Override
    public Optional<Factura> buscarPorPedido(Long pedidoId){
        return facturaRepo.findByPedidoId(pedidoId);
    }

    @Override
    public Optional<Factura> buscarPorId(Long id){
        return facturaRepo.findById(id);
    }

    @Override
    public byte[] generarPDFFactura(Long facturaId) throws Exception {

        Factura factura = facturaRepo.findById(facturaId).orElseThrow();

        List<FacturaPDFDTO> filas = new ArrayList<>();

        for (DetallePedido dp : factura.getPedido().getDetalles()) {
            FacturaPDFDTO fila = new FacturaPDFDTO();
            fila.setFacturaId(factura.getId());
            fila.setFechaEmision(factura.getFechaEmision());
            fila.setMetodoPago(factura.getMetodoPago());
            fila.setClienteNombre(factura.getCliente().getNombre());
            fila.setClienteDni(factura.getCliente().getDni());
            fila.setClienteTelefono(factura.getCliente().getTelefono());
            fila.setPedidoId(factura.getPedido().getId());
            fila.setMesaNumero(factura.getPedido().getMesa().getNumero());
            fila.setMeseroNombre(
                factura.getPedido().getUsuarioCredential().getEmpleado().getNombre()
            );
            fila.setFechaPedido(factura.getPedido().getFechaHora());
            fila.setProductoNombre(dp.getProducto().getNombre());
            fila.setCantidad(dp.getCantidad());
            fila.setPrecioUnit(dp.getProducto().getPrecio());
            fila.setSubtotalLinea(
                dp.getProducto().getPrecio()
                  .multiply(BigDecimal.valueOf(dp.getCantidad()))
            );
            fila.setSubtotal(factura.getSubtotal());
            fila.setIgv(factura.getIgv());
            fila.setTotal(factura.getTotal());
            filas.add(fila);
        }
     // AHORA
        InputStream reportStream = getClass()
            .getResourceAsStream("/facturaReporte.jrxml");

        if(reportStream == null){
            System.out.println("ERROR: No encuentra el archivo jrxml");
            throw new Exception("Archivo jrxml no encontrado");
        }

        System.out.println("Archivo jrxml encontrado, compilando...");

        JasperReport jasperReport;
        try {
            jasperReport = JasperCompileManager.compileReport(reportStream);
            System.out.println("Compilado exitoso");
        } catch(Exception e){
            System.out.println("Error al compilar: " + e.getMessage());
            Throwable cause = e.getCause();
            while(cause != null){
                System.out.println("Causa: " + cause.getMessage());
                cause = cause.getCause();
            }
            throw e;
        }

        JRBeanCollectionDataSource dataSource =
            new JRBeanCollectionDataSource(filas);

        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager
            .fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
