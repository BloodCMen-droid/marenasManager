package marenas.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Empleado;

public interface IRepository extends JpaRepository<Empleado, Long> {

}
