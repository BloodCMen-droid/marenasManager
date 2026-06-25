package marenas.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import marenas.pe.invalid.AuthService;
import marenas.pe.model.Empleado;
import marenas.pe.service.EmpleadoImplement;
import marenas.pe.service.RolImplement;

@Controller
public class EmpleadoController {

	@Autowired //IMPORTANTE!!!!
	private EmpleadoImplement empImp;
	
	@Autowired //IMPORTANTE!!!!
	private RolImplement rolImp;
	
    
    

	
	@GetMapping("/listEmpleados")
	public String listarEmpleados(Model modelo) {
		modelo.addAttribute("empleados",empImp.getAllEmpleado());
		return "administrador/mantenimiento-empleado";
	}
	
	@GetMapping("/newEmpleado")
	public String formularioEmpleado(Model modelo) {
		Empleado empleado = new Empleado();
		modelo.addAttribute("empleado",empleado);
		modelo.addAttribute("roles", rolImp.getAllRol());
		return "administrador/empleado-form";
	}
	
	@PostMapping("/saveEmpleado")
	public String GuardarEmpleado(Empleado empleado) {
		empImp.createEmple(empleado);
		return "redirect:/listEmpleados";
	}
	
	@GetMapping("/deleteEmpleado/{id}")
	public String deleteEmpleado(@PathVariable Long id) {
		empImp.deleteEmpleado(id);
		return "redirect:/listEmpleados";
	}
	
	@GetMapping("/editEmpleado/{id}")
	public String editEmpleado(@PathVariable Long id, Model modelo) {
		modelo.addAttribute("empleado",empImp.searchEmpleado(id).orElse(null));
		modelo.addAttribute("roles", rolImp.getAllRol());
		return "administrador/empleado-form";
	}
}
