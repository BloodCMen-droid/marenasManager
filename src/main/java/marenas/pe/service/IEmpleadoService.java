package marenas.pe.service;

import java.util.List;
import java.util.Optional;


import marenas.pe.model.Empleado;



public interface IEmpleadoService {
	
	public List<Empleado>      getAllEmpleado();
	public Empleado            createEmple(Empleado empleado);
	public void              deleteEmpleado(Long id);
	public Optional<Empleado>  searchEmpleado(Long id);

}
