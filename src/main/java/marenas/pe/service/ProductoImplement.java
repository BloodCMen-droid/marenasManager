package marenas.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.Producto;
import marenas.pe.repository.IProductoRepository;

@Service
public class ProductoImplement implements IProductoService {

	@Autowired
	private IProductoRepository proRep;
	
	@Override
	public List<Producto> getAllProducto() {
		return proRep.findAll();
	}

	@Override
	public Producto createProdu(Producto producto) {
		return proRep.save(producto);
	}

	@Override
	public void deleteProducto(Long id) {
		proRep.deleteById(id);
		
	}

	@Override
	public Optional<Producto> searchProducto(Long id) {
		return proRep.findById(id);
	}

	@Override
	public List<Producto> findByCategoriaId(Long categoriaId) {
		return proRep.getProductosByCategoria(categoriaId);
	}

	

}
