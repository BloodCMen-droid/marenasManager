package marenas.pe.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.Pedido;
import marenas.pe.repository.IPedidoRepository;


@Service
public class PedidoImplement implements IPedidoService{


    @Autowired
    private IPedidoRepository pedidoRepo;

	@Override
	public Pedido createPedido(Pedido pedido) {
		return pedidoRepo.save(pedido);
	}

	@Override
	public List<Pedido> getAllPedido(){

	    return pedidoRepo.findAll();

	}

	@Override
	public Optional<Pedido> searchPedido(Long id) {
		return pedidoRepo.findById(id);
	}

	@Override
	public void deleteDelete(Long id) {
		pedidoRepo.deleteById(id);
		
	}

	@Override
	public void verificarYActualizarEstado(Long pedidoId) {
		    Pedido pedido = pedidoRepo.findById(pedidoId).orElseThrow();
		
		    boolean todosPreparados = pedido.getDetalles()
		        .stream()
		        .allMatch(d -> d.getEstadoDProducto().equals("PREPARADO"));
		    if(todosPreparados && !pedido.getDetalles().isEmpty()){
		        pedido.setEstado("LISTO");
		        pedidoRepo.save(pedido);
		    }
	}


}