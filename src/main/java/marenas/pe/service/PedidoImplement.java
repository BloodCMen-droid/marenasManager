package marenas.pe.service;



import java.util.List;

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


}