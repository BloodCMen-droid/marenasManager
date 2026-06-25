package marenas.pe.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import marenas.pe.dto.ListaCompraDTO;
import marenas.pe.model.Insumo;
import marenas.pe.repository.IInsumoRepository;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

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

	
	@Override
	public byte[] exportarListaCompras() throws Exception {

	    List<ListaCompraDTO> lista = insuRepo.findAll()
	        .stream()
	        .filter(i -> i.getEstado().equals("CRÍTICO") 
	                  || i.getEstado().equals("AGOTADO"))
	        .map(i -> new ListaCompraDTO(
	            i.getNombre(),
	            i.getUnidadMedida(),
	            i.getStock(),
	            i.getStockMinimo()
	        ))
	        .collect(Collectors.toList());

	    File file = ResourceUtils.getFile("classpath:listaCompras.jrxml");
	    JasperReport jasperReport = JasperCompileManager
	        .compileReport(file.getAbsolutePath());

	    JRBeanCollectionDataSource dataSource = 
	        new JRBeanCollectionDataSource(lista);

	    Map<String, Object> parameters = new HashMap<>();

	    // ← AQUÍ EL FIX, usa el dataSource directamente, no JREmptyDataSource
	    JasperPrint jasperPrint = JasperFillManager
	        .fillReport(jasperReport, parameters, dataSource);

	    return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	 

}
