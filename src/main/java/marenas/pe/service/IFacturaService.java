package marenas.pe.service;

import java.util.Optional;

import marenas.pe.model.Factura;

public interface IFacturaService {
	
	    Factura crearFactura(Factura factura);
	    Optional<Factura> buscarPorPedido(Long pedidoId);
	    Optional<Factura> buscarPorId(Long id);
	    
	    byte[] generarPDFFactura(Long facturaId) throws Exception;

}
