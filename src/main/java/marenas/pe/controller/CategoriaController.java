package marenas.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import marenas.pe.model.Categoria;
import marenas.pe.service.CategoriaImplement;

@Controller
public class CategoriaController {
	
	@Autowired //IMPORTANTE!!!!
	private CategoriaImplement cateImp;
	
	@GetMapping("/listCategorias")
	public String listarCategoria(Model modelo) {
		modelo.addAttribute("categorias",cateImp.getAllCategoria());
		return "administrador/mantenimiento-categoria";
	}
	
	@GetMapping("/newCategoria")
	public String formularioCategoria(Model modelo) {
		Categoria categoria = new Categoria();
		modelo.addAttribute("categoria",categoria);
		return "administrador/categoria-form";
	}
	
	@PostMapping("/saveCategoria")
	public String GuardarCategoria(Categoria categoria) {
		cateImp.createCate(categoria);
		return "redirect:/listCategorias";
	}
	
	@GetMapping("/deleteCategoria/{id}")
	public String deleteCategoria(@PathVariable Long id) {
		cateImp.deleteCategoria(id);
		return "redirect:/listCategoria";
	}
	
	@GetMapping("/editCategoria/{id}")
	public String editCategoria(@PathVariable Long id, Model modelo) {
		modelo.addAttribute("categoria",cateImp.searchCategoria(id).orElse(null));
		return "administrador/categoria-form";
	}

}
