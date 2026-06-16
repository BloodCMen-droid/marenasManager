package marenas.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import marenas.pe.model.Rol;

import marenas.pe.repository.IRolRepository;
@Service
public class RolImplement implements IRolService {

	@Autowired
	private IRolRepository rolRepo;

	
	@Override
	public List<Rol> getAllRol() {
		return rolRepo.findAll();
	}

	@Override
	public Rol createRol(Rol rol) {
		return rolRepo.save(rol);
	}

	@Override
	public void deleteRol(int id) {
		rolRepo.deleteById(id);
		
	}

	@Override
	public Optional<Rol> searchRol(int id) {
		return rolRepo.findById(id);
	}





}
