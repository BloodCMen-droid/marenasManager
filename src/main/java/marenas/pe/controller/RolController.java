package marenas.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import marenas.pe.model.Categoria;
import marenas.pe.model.Rol;
import marenas.pe.service.CategoriaImplement;
import marenas.pe.service.RolImplement;

@Controller
public class RolController {
	
	@Autowired //IMPORTANTE!!!!
	private RolImplement rolImp;
	
	@GetMapping("/listRoles")
	public String listarRol(Model modelo) {
		modelo.addAttribute("roles",rolImp.getAllRol());
		return "administrador/mantenimiento-rol";
	}
	
	@GetMapping("/newRol")
	public String formularioRol(Model modelo) {
		Rol rol = new Rol();
		modelo.addAttribute("rol",rol);
		return "administrador/rol-form";
	}
	
	@PostMapping("/saveRol")
	public String GuardarRol(Rol rol) {
		rolImp.createRol(rol);
		return "redirect:/listRoles";
	}
	
	@GetMapping("/deleteRol/{id}")
	public String deleteRol(@PathVariable int id) {
		rolImp.deleteRol(id);
		return "redirect:/listRoles";
	}
	
	@GetMapping("/editRol/{id}")
	public String editRol(@PathVariable int id, Model modelo) {
		modelo.addAttribute("rol",rolImp.searchRol(id).orElse(null));
		return "administrador/rol-form";
	}


}
