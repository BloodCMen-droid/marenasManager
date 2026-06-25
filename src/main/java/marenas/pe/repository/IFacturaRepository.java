package marenas.pe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Factura;

public interface IFacturaRepository extends JpaRepository<Factura, Long> {

	Optional<Factura> findByPedidoId(Long pedidoId);
}
