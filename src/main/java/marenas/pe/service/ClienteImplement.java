package marenas.pe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.Cliente;
import marenas.pe.repository.IClienteRepository;

@Service
public class ClienteImplement implements IClienteService {
	
	 @Autowired
	    private IClienteRepository clienteRepo;

	    @Override
	    public Optional<Cliente> buscarPorDni(String dni){
	        return clienteRepo.findByDni(dni);
	    }

	    @Override
	    public Cliente crearCliente(Cliente cliente){
	        return clienteRepo.save(cliente);
	    }

}
