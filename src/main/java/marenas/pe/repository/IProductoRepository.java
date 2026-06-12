package marenas.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Long> {

}
