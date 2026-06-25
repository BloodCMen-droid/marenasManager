package marenas.pe.service;

import java.util.List;
import java.util.Optional;


import marenas.pe.model.Insumo;

public interface IInsumoService {
	
	public List<Insumo>      getAllInsumo();
	public Insumo            createInsumo(Insumo insumo);
	public void              deleteInsumo(Long id);
	public Optional<Insumo>  searchInsumo(Long id);
	
	public void              agregarStock(Long id, int cantidad);
	
	public byte[] exportarListaCompras() throws Exception;

}
