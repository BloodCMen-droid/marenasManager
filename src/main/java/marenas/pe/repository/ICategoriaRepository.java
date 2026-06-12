package marenas.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

}
