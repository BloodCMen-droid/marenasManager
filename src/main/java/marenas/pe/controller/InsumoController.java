package marenas.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import marenas.pe.model.Insumo;

import marenas.pe.service.InsumoImplement;

@Controller
public class InsumoController {
	
	@Autowired
	private InsumoImplement insuImp;
	

	@GetMapping("/listInsumos")
	public String listarInsumos(Model modelo) {
		modelo.addAttribute("insumos",insuImp.getAllInsumo());
		return "administrador/mantenimiento-insumo";
	}
	
	@GetMapping("/newInsumo")
	public String formularioInsumo(Model modelo) {
		Insumo insumo = new Insumo();
		modelo.addAttribute("insumo",insumo);
		return "administrador/insumo-form";
	}
	
	@PostMapping("/saveInsumo")
	public String GuardarInsumo(Insumo insumo) {
		insuImp.createInsumo(insumo);
		return "redirect:/listInsumos";
	}
	
	@GetMapping("/deleteInsumo/{id}")
	public String deleteInsumo(@PathVariable Long id) {
		insuImp.deleteInsumo(id);
		return "redirect:/listInsumos";
	}
	
	@GetMapping("/editInsumo/{id}")
	public String editInsumo(@PathVariable Long id, Model modelo) {
		modelo.addAttribute("insumo",insuImp.searchInsumo(id).orElse(null));
		return "administrador/insumo-form";
	}
	
	

	@GetMapping("/addStock/{id}")
	public String formularioStock(@PathVariable Long id, Model modelo){
	    Insumo insumo = insuImp.searchInsumo(id).orElse(null);
	    modelo.addAttribute("insumo", insumo);
	    return "administrador/anadir-insumo";
	}
	
	@PostMapping("/saveStock")
	public String guardarStock(
	        Long id,
	        int cantidad){

	    insuImp.agregarStock(id, cantidad);

	    return "redirect:/listInsumos";
	}

}
