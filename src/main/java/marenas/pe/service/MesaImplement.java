package marenas.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.Mesa;
import marenas.pe.repository.IMesaRepository;

@Service
public class MesaImplement implements IMesaService{
	
	@Autowired
	private IMesaRepository mesRepo;

	@Override
	public List<Mesa> getAllMesa() {
		return mesRepo.findAll();
	}

	@Override
	public Mesa createMesa(Mesa mesa) {
		return  mesRepo.save(mesa);
	}

	@Override
	public void deleteMesa(Long id) {
		mesRepo.deleteById(id);
		
	}

	@Override
	public Optional<Mesa> searchMesa(Long id) {
		return mesRepo.findById(id);
	}

}
