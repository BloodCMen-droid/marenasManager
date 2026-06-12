package marenas.pe.service;

import java.util.List;
import java.util.Optional;

import marenas.pe.model.Categoria;



public interface ICategoriaService  {
	public List<Categoria>      getAllCategoria();
	public Categoria            createCate(Categoria categoria);
	public void              deleteCategoria(Long id);
	public Optional<Categoria>  searchCategoria(Long id);

}
