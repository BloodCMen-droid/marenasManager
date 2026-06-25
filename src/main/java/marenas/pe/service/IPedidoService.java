package marenas.pe.service;




import java.util.List;
import java.util.Optional;

import marenas.pe.model.Pedido;

public interface IPedidoService {
	
    Pedido createPedido(Pedido pedido);
    
    List<Pedido> getAllPedido();
    
    Optional<Pedido> searchPedido(Long id);
    
    public void              deleteDelete(Long id);
    public void verificarYActualizarEstado(Long pedidoId);
}
