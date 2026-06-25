package marenas.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import marenas.pe.model.Empleado;
import marenas.pe.model.UsuarioCredential;
import marenas.pe.repository.IEmpleadoRepository;
import marenas.pe.repository.IUsuarioCredentialRepository;

@Service
public class EmpleadoImplement implements IEmpleadoService{
	
	@Autowired
	private IEmpleadoRepository empRepo;
	
	@Autowired
	private IUsuarioCredentialRepository usuarioRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Empleado> getAllEmpleado() {
		
		return empRepo.findAll();
	}

	
	@Override
	public Empleado createEmple(Empleado empleado) {
	    try {
	        UsuarioCredential usuario = empleado.getUsuarioCredential();
	        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
	        return empRepo.save(empleado);
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        return null; // retorna null en caso de error
	    }
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
