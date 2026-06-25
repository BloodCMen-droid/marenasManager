package marenas.pe.service;


import java.util.Optional;

import marenas.pe.model.DetallePedido;


public interface IDetallePedidoService {
	
	DetallePedido creatrDetalleP(DetallePedido detalle);

    Optional<DetallePedido> searchDetalle(Long id);

    public void              deleteDetalle(Long id);
    
    public void verificarEstadoPedido(Long pedidoId);
    
    DetallePedido actualizarEstado(Long id, String estado);
}
