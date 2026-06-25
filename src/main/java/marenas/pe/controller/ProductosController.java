package marenas.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import marenas.pe.model.DetalleProductoInsumo;
import marenas.pe.model.Insumo;
import marenas.pe.model.Producto;
import marenas.pe.service.CategoriaImplement;
import marenas.pe.service.IDetalleProductoInsumoService;
import marenas.pe.service.IInsumoService;
import marenas.pe.service.ProductoImplement;



@Controller
public class ProductosController {


	@Autowired
	private ProductoImplement produImp;
	
	@Autowired
	private CategoriaImplement cateImp;
	
	@Autowired
	private IDetalleProductoInsumoService detalleInsumoService;

	@Autowired
	private IInsumoService insumoService;
	

	//productos -- array
	//producto --- objet

	@GetMapping("/listProductos")
	public String listarAlumnos(Model modelo) {
		modelo.addAttribute("productos",produImp.getAllProducto());
		return "administrador/mantenimiento-productos";
	}
	
	@GetMapping("/newProducto")
	public String formularioAlumno(Model modelo) {
		Producto producto = new Producto();
		modelo.addAttribute("producto",producto);
		modelo.addAttribute("categorias", cateImp.getAllCategoria());
		return "administrador/producto-form";
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
		return "administrador/producto-form";
	}
	
	
	//DETALLE PRODCUTO INSUMO 
	
	// Ver receta de un producto
	@GetMapping("/recetaProducto/{id}")
	public String verReceta(@PathVariable Long id, Model model){
	    Producto producto = produImp.searchProducto(id).orElseThrow();
	    model.addAttribute("producto", producto);
	    model.addAttribute("receta", 
	        detalleInsumoService.getByProducto(id));
	    model.addAttribute("insumos", insumoService.getAllInsumo());
	    return "administrador/receta-producto";
	}

	// Agregar insumo a receta
	@PostMapping("/saveReceta")
	public String guardarReceta(
	        @RequestParam Long productoId,
	        @RequestParam Long insumoId,
	        @RequestParam Double gasto){

	    Producto producto = produImp.searchProducto(productoId).orElseThrow();
	    Insumo insumo = insumoService.searchInsumo(insumoId).orElseThrow();

	    DetalleProductoInsumo detalle = new DetalleProductoInsumo();
	    detalle.setProducto(producto);
	    detalle.setInsumo(insumo);
	    detalle.setGasto(gasto);

	    detalleInsumoService.crear(detalle);

	    return "redirect:/recetaProducto/" + productoId;
	}

	// Eliminar insumo de receta
	@GetMapping("/deleteReceta/{id}/{productoId}")
	public String eliminarReceta(
	        @PathVariable Long id,
	        @PathVariable Long productoId){
	    detalleInsumoService.eliminar(id);
	    return "redirect:/recetaProducto/" + productoId;
	}
}
