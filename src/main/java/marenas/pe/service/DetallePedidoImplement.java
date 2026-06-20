package marenas.pe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.DetallePedido;
import marenas.pe.repository.IDetallePedidoRepository;

@Service
public class DetallePedidoImplement implements IDetallePedidoService {
	

    @Autowired
    private IDetallePedidoRepository repository;

    public DetallePedido creatrDetalleP(DetallePedido detalle) {
        return repository.save(detalle);
    }

}
