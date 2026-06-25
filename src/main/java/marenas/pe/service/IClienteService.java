package marenas.pe.service;

import java.util.Optional;

import marenas.pe.model.Cliente;

public interface IClienteService {

	public Optional<Cliente> buscarPorDni(String dni);
	
	public Cliente crearCliente(Cliente cliente);
}
