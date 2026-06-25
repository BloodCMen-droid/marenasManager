package marenas.pe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.DetallePedido;
import marenas.pe.model.Pedido;
import marenas.pe.repository.IDetallePedidoRepository;
import marenas.pe.repository.IPedidoRepository;

@Service
public class DetallePedidoImplement implements IDetallePedidoService {
	

    @Autowired
    private IDetallePedidoRepository detallePRepo;
    
    @Autowired
    private IPedidoRepository pedidoRepo;

    public DetallePedido creatrDetalleP(DetallePedido detalle) {
        return detallePRepo.save(detalle);
    }

	@Override
	public Optional<DetallePedido> searchDetalle(Long id) {
		return detallePRepo.findById(id);
	}

	@Override
	public void deleteDetalle(Long id) {
		detallePRepo.deleteById(id);
		
	}

	@Override
	public void verificarEstadoPedido(Long pedidoId) {
	    Pedido pedido = pedidoRepo.findById(pedidoId).orElseThrow();
	    
	    boolean todosPreparados = pedido.getDetalles()
	        .stream()
	        .allMatch(d -> d.getEstadoDProducto().equals("PREPARADO"));
	    
	    if (todosPreparados) {
	        pedido.setEstado("LISTO");
	        pedidoRepo.save(pedido);
	    }
		
	}

	@Override
	public DetallePedido actualizarEstado(Long id, String estado) {
		 DetallePedido detalle = detallePRepo.findById(id).orElseThrow();
		    detalle.setEstadoDProducto(estado);
		    return detallePRepo.save(detalle);
	}
	

}
