package marenas.pe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Long> {
	List<Producto> getProductosByCategoria(Long categoriaId);

}
