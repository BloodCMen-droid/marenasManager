package marenas.pe.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import marenas.pe.dto.DetallePedidoDTO;
import marenas.pe.model.DetallePedido;
import marenas.pe.model.Mesa;
import marenas.pe.model.Pedido;
import marenas.pe.model.Producto;
import marenas.pe.model.UsuarioCredential;
import marenas.pe.repository.IMesaRepository;
import marenas.pe.repository.IProductoRepository;
import marenas.pe.repository.IUsuarioCredentialRepository;
import marenas.pe.service.ICategoriaService;
import marenas.pe.service.IDetallePedidoService;
import marenas.pe.service.IPedidoService;

@Controller
@SessionAttributes("detalleTemporal")
public class PedidoController {

	
	@Autowired
	private IProductoRepository productoRepository;

	@Autowired
	private IPedidoService pedidoService;

	@Autowired
	private IDetallePedidoService detallePedidoService;

	@Autowired
	private IMesaRepository mesaRepository;

	@Autowired
	private IUsuarioCredentialRepository usuarioRepository;
	
	@Autowired
	private ICategoriaService categoriaService;


    // Carga formulario nuevo pedido
    @GetMapping("/newPedido")
    public String nuevoPedido(Model model){
    	model.addAttribute("productos",productoRepository.findAll());
        model.addAttribute("mesas",mesaRepository.findAll());
    	 model.addAttribute("categorias",categoriaService.getAllCategoria());
        if(!model.containsAttribute("detalleTemporal")){
            model.addAttribute( "detalleTemporal",
                new ArrayList<DetallePedidoDTO>()
            );
        }
        return "mesero/registrar-pedido";
    }



    // Agregar producto temporalmente
    @PostMapping("/saveDetalle")
    public String agregarDetalle(
            @RequestParam Long productoId,
            @RequestParam Integer cantidad,
            @RequestParam(required=false) String adicional,
            @ModelAttribute("detalleTemporal") 
            List<DetallePedidoDTO> detalles){
    	Producto producto = productoRepository.findById(productoId).get();

        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setProductoId(producto.getId());
        dto.setProductoNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        
        dto.setCantidad(cantidad);
        dto.setAdicional(adicional);
        dto.setPrecio(producto.getPrecio());
        dto.setSubtotal(
                producto.getPrecio()
                .multiply(BigDecimal.valueOf(cantidad))
            );

        detalles.add(dto);
       
        return "redirect:/newPedido";
    }


    @PostMapping("/savePedido")
    public String registrarPedido(
            @RequestParam Long mesaId,
            @RequestParam Long usuarioId,
            @ModelAttribute("detalleTemporal")
            List<DetallePedidoDTO> detalles,
            SessionStatus status 
            ){ 
    	
    	
    	if(detalles.isEmpty()){ return "redirect:/nuevoPedido"; }
        // 1. Crear Pedido
        Pedido pedido = new Pedido();
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        
        // 2. Buscar mesa
        Mesa mesa = mesaRepository
                .findById(mesaId)
                .get();
        pedido.setMesa(mesa);

        // 3. Buscar usuario
        UsuarioCredential usuario = usuarioRepository.findById(usuarioId).get();
        pedido.setUsuarioCredential(usuario);

        // 4. Guardar pedido primero
        Pedido pedidoGuardado = pedidoService.createPedido(pedido);
        
        // 5. Crear detalles reales
        for(DetallePedidoDTO dto : detalles){
        	
        	DetallePedido detalle = new DetallePedido();
            detalle.setCantidad(dto.getCantidad());
            detalle.setAdicional(dto.getAdicional());
            detalle.setEstadoDProducto("PENDIENTE");
            detalle.setPedido(pedidoGuardado);
            
            Producto producto = productoRepository.findById(dto.getProductoId()).get();
            detalle.setProducto(producto);
            detallePedidoService.creatrDetalleP(detalle);
        }
        // 6. Limpiar sesión
        status.setComplete();
        return "redirect:/pedidos";
    }
    
    @PostMapping("/guardarPedido")
    public String guardarPedido(@ModelAttribute Pedido pedido){

        pedidoService.createPedido(pedido);

        return "redirect:/pedidos";

    }
    
    @GetMapping("/pedidos")
    public String listarPedidos(Model model){

        model.addAttribute("pedidos",pedidoService.getAllPedido());

        return "mesero/pedidos";
    }
    
    @GetMapping("/deleteDetalle/{id}")
    public String eliminarDetalle(
            @PathVariable("id") UUID id,
            @ModelAttribute("detalleTemporal") List<DetallePedidoDTO> detalles
    ){

        detalles.removeIf(d -> d.getIdTemporal().equals(id));

        return "redirect:/newPedido";
    }
}
