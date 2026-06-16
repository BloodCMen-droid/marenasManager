package marenas.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Mesa;

public interface IMesaRepository  extends JpaRepository<Mesa, Long> {

}
