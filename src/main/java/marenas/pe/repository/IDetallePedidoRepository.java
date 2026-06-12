package marenas.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.DetallePedido;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

}
