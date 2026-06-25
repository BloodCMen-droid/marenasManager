package marenas.pe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Demora;

public interface IDemoraRepository extends JpaRepository<Demora, Long> {
	 List<Demora> findByPedidoId(Long pedidoId);
}
