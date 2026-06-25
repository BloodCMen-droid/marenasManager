package marenas.pe.service;

import java.util.List;

import marenas.pe.model.Demora;

public interface IDemoraService {
	
	Demora crearDemora(Demora demora);
    List<Demora> getDemorasPorPedido(Long pedidoId);
    Demora notificarDemora(Long id);

}
