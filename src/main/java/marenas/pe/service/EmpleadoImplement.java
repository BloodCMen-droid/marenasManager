package marenas.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.Empleado;
import marenas.pe.repository.IEmpleadoRepository;

@Service
public class EmpleadoImplement implements IEmpleadoService{
	
	@Autowired
	private IEmpleadoRepository empRepo;

	@Override
	public List<Empleado> getAllEmpleado() {
		return empRepo.findAll();
	}

	@Override
	public Empleado createEmple(Empleado empleado) {
		return empRepo.save(empleado);
	}

	@Override
	public void deleteEmpleado(Long id) {
		empRepo.deleteById(id);
		
	}

	@Override
	public Optional<Empleado> searchEmpleado(Long id) {
		return empRepo.findById(id);
	}

	
}
