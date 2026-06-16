package marenas.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import marenas.pe.model.Mesa;
import marenas.pe.model.Rol;
import marenas.pe.repository.IMesaRepository;
import marenas.pe.service.MesaImplement;

@Controller
public class MesaController {
	
	@Autowired
	private MesaImplement mesImp;
	

	@GetMapping("/listMesas")
	public String listarMesa(Model modelo) {
		modelo.addAttribute("mesas",mesImp.getAllMesa());
		return "administrador/mantenimiento-mesa";
	}
	
	@GetMapping("/newMesa")
	public String formularioMesa(Model modelo) {
		Mesa mesa = new Mesa();
		modelo.addAttribute("mesa",mesa);
		return "administrador/mesa-form";
	}
	
	@PostMapping("/saveMesa")
	public String GuardarMesa(Mesa mesa) {
		mesImp.createMesa(mesa);
		return "redirect:/listMesas";
	}
	
	@GetMapping("/deleteMesa/{id}")
	public String deleteMesa(@PathVariable Long id) {
		mesImp.deleteMesa(id);
		return "redirect:/listMesas";
	}
	
	@GetMapping("/editMesa/{id}")
	public String editMesa(@PathVariable Long id, Model modelo) {
		modelo.addAttribute("mesa",mesImp.searchMesa(id).orElse(null));
		return "administrador/mesa-form";
	}

	
		
	

}
