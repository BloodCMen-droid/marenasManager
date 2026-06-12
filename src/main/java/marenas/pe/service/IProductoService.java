package marenas.pe.service;

import java.util.List;
import java.util.Optional;

import marenas.pe.model.Producto;



public interface IProductoService {
	public List<Producto>      getAllProducto();
	public Producto            createProdu(Producto producto);
	public void              deleteProducto(Long id);
	public Optional<Producto>  searchProducto(Long id);

}
