package marenas.pe.service;

import java.util.List;
import java.util.Optional;


import marenas.pe.model.Rol;

public interface IRolService {

	public List<Rol>      getAllRol();
	public Rol            createRol(Rol rol);
	public void              deleteRol(int id);
	public Optional<Rol>  searchRol(int id);
}
