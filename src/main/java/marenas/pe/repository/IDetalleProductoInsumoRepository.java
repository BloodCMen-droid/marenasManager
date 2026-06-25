package marenas.pe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.DetalleProductoInsumo;

public interface IDetalleProductoInsumoRepository extends JpaRepository<DetalleProductoInsumo, Long> {

	
	List<DetalleProductoInsumo> findByProductoId(Long productoId);
}
