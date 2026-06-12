package marenas.pe.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.Categoria;
import marenas.pe.repository.ICategoriaRepository;


@Service
public class CategoriaImplement implements ICategoriaService {
	
	@Autowired
	private ICategoriaRepository cateRep;

	@Override
	public List<Categoria> getAllCategoria() {	
		return cateRep.findAll();
	}

	@Override
	public Categoria createCate(Categoria categoria) {
		return cateRep.save(categoria);
	}

	@Override
	public void deleteCategoria(Long id) {
		cateRep.deleteById(id);
		
	}

	@Override
	public Optional<Categoria> searchCategoria(Long id) {
		return cateRep.findById(id);
	}
	
	

}
