package marenas.pe.service;




import java.util.List;

import marenas.pe.model.Pedido;

public interface IPedidoService {
	
    Pedido createPedido(Pedido pedido);
    
    List<Pedido> getAllPedido();
}
