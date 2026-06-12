package marenas.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import marenas.pe.model.Producto;
import marenas.pe.service.CategoriaImplement;
import marenas.pe.service.ProductoImplement;



@Controller
public class ProductosController {


	@Autowired
	private ProductoImplement produImp;
	
	@Autowired
	private CategoriaImplement cateImp;
	

	//productos -- array
	//producto --- objet

	@GetMapping("/listProductos")
	public String listarAlumnos(Model modelo) {
		modelo.addAttribute("productos",produImp.getAllProducto());
		return "mantenimiento-productos";
	}
	
	@GetMapping("/newProducto")
	public String formularioAlumno(Model modelo) {
		Producto producto = new Producto();
		modelo.addAttribute("producto",producto);
		modelo.addAttribute("categorias", cateImp.getAllCategoria());
		return "producto-form";
	}
	
	@PostMapping("/saveProducto")
	public String GuardarAlumno(Producto producto) {
		produImp.createProdu(producto);
		return "redirect:/listProductos";
	}
	
	@GetMapping("/deleteProducto/{id}")
	public String deleteAlumno(@PathVariable Long id) {
		produImp.deleteProducto(id);
		return "redirect:/listProductos";
	}
	
	@GetMapping("/editProducto/{id}")
	public String editAlumno(@PathVariable Long id, Model modelo) {
		modelo.addAttribute("producto",produImp.searchProducto(id).orElse(null));
		modelo.addAttribute("categorias", cateImp.getAllCategoria());
		return "producto-form";
	}
}
