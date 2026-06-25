package marenas.pe.service;

import java.util.List;

import marenas.pe.model.DetalleProductoInsumo;

public interface IDetalleProductoInsumoService {
	
	DetalleProductoInsumo crear(DetalleProductoInsumo detalle);
	
    List<DetalleProductoInsumo> getByProducto(Long productoId);
    
    void eliminar(Long id);

}
