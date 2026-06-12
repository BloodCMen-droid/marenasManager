package marenas.pe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marenas.pe.model.Empleado;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {

}
