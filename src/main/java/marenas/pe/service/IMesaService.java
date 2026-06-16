package marenas.pe.service;

import java.util.List;
import java.util.Optional;


import marenas.pe.model.Mesa;
public interface IMesaService {
	
	public List<Mesa>      getAllMesa();
	public Mesa            createMesa(Mesa mesa);
	public void              deleteMesa(Long id);
	public Optional<Mesa>  searchMesa(Long id);

}
