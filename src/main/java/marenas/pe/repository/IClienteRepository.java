package marenas.pe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByDni(String dni);
}
