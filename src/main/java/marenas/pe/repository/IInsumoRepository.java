package marenas.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Insumo;

public interface IInsumoRepository extends JpaRepository<Insumo, Long> {

}
