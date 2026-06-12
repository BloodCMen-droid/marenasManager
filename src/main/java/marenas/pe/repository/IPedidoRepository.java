package marenas.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Pedido;

public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

}
