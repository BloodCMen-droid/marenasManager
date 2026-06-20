package marenas.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.Insumo;
import marenas.pe.repository.IInsumoRepository;

@Service
public class InsumoImplement implements IInsumoService {

	@Autowired
	private IInsumoRepository insuRepo;
	
	
	private void validarEstado(Insumo insumo){

        if(insumo.getStock() == 0){
            insumo.setEstado("AGOTADO");
        }else if(insumo.getStock() < insumo.getStockMinimo()){
            insumo.setEstado("CRÍTICO");
        }else if(insumo.getStock() <= insumo.getStockMinimo()+5){
            insumo.setEstado("BAJO STOCK");
        }else{
            insumo.setEstado("DISPONIBLE");
        }
    }

	
	@Override
	public List<Insumo> getAllInsumo() {
		
		return insuRepo.findAll();
	}

	@Override
	public Insumo createInsumo(Insumo insumo) {
		  validarEstado(insumo);
		return insuRepo.save(insumo);
	}

	@Override
	public void deleteInsumo(Long id) {
		
		insuRepo.deleteById(id);
	}

	@Override
	public Optional<Insumo> searchInsumo(Long id) {
		
		return insuRepo.findById(id);
	}


	@Override
	public void agregarStock(Long id, int cantidad) {
		
		 Insumo insumo = insuRepo.findById(id).orElse(null);
	        if(insumo != null){

	            insumo.setStock(
	                insumo.getStock() + cantidad
	            );
	            validarEstado(insumo);
	            insuRepo.save(insumo);
	        }	
	}

	
	 

}
