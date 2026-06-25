package marenas.pe.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import marenas.pe.config.CustomUserDetails;
import marenas.pe.dto.DetallePedidoDTO;
import marenas.pe.model.Demora;
import marenas.pe.model.DetallePedido;
import marenas.pe.model.Mesa;
import marenas.pe.model.Pedido;
import marenas.pe.model.Producto;
import marenas.pe.model.UsuarioCredential;
import marenas.pe.repository.IMesaRepository;
import marenas.pe.repository.IProductoRepository;
import marenas.pe.repository.IUsuarioCredentialRepository;
import marenas.pe.service.ICategoriaService;
import marenas.pe.service.IDemoraService;
import marenas.pe.service.IDetallePedidoService;
import marenas.pe.service.IPedidoService;

@Controller
@SessionAttributes({"detalleTemporal", "mesaSeleccionada"})

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
	
	@Autowired
	private IDemoraService demoraService;
	



    // Carga formulario nuevo pedido
	@GetMapping("/newPedido")
	public String nuevoPedido(Model model, Authentication auth){

	    // Usuario logueado
	    CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
	    model.addAttribute("usuarioNombre", 
	        user.getUsuarioCredential().getEmpleado().getNombre());

	    model.addAttribute("productos", productoRepository.findAll());
	    model.addAttribute("mesas", mesaRepository.findAll());
	    model.addAttribute("categorias", categoriaService.getAllCategoria());

	    // ← ESTO ES EL FIX, inicializa ambos si no existen
	    if(!model.containsAttribute("detalleTemporal")){
	        model.addAttribute("detalleTemporal", new ArrayList<DetallePedidoDTO>());
	    }
	    if(!model.containsAttribute("mesaSeleccionada")){
	        model.addAttribute("mesaSeleccionada", null); // ← inicializa en null
	    }

	    return "mesero/registrar-pedido";
	}



    // Agregar producto temporalmente
	@PostMapping("/saveDetalle")
	public String agregarDetalle(
	        @RequestParam Long productoId,
	        @RequestParam Integer cantidad,
	        @RequestParam(required=false) String adicional,
	        @RequestParam(required=false) Long mesaId, // ← viene del form
	        @ModelAttribute("detalleTemporal") List<DetallePedidoDTO> detalles,
	        Model model){ // ← sin @ModelAttribute mesaSeleccionada

	    // Guardar mesa en sesión si viene
	    if(mesaId != null){
	        model.addAttribute("mesaSeleccionada", mesaId);
	    }

	    Producto producto = productoRepository.findById(productoId).orElseThrow();

	    DetallePedidoDTO dto = new DetallePedidoDTO();
	    dto.setProductoId(producto.getId());
	    dto.setProductoNombre(producto.getNombre());
	    dto.setPrecio(producto.getPrecio());
	    dto.setCantidad(cantidad);
	    dto.setAdicional(adicional);
	    dto.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)));

	    detalles.add(dto);

	    return "redirect:/newPedido";
	}

    @PostMapping("/savePedido")
    public String registrarPedido(
            @RequestParam Long mesaId,
            @ModelAttribute("detalleTemporal") List<DetallePedidoDTO> detalles,
            SessionStatus status,
            Authentication auth  // ← AGREGAR ESTO
            ){

        if(detalles.isEmpty()){ return "redirect:/newPedido"; }

        // Obtener usuario logueado
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        UsuarioCredential usuario = userDetails.getUsuarioCredential(); // ← necesitas este método

        Pedido pedido = new Pedido();
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");

        Mesa mesa = mesaRepository.findById(mesaId).orElseThrow();
        pedido.setMesa(mesa);
        pedido.setUsuarioCredential(usuario); // ← ya no hardcodeado

        Pedido pedidoGuardado = pedidoService.createPedido(pedido);

        for(DetallePedidoDTO dto : detalles){
            DetallePedido detalle = new DetallePedido();
            detalle.setCantidad(dto.getCantidad());
            detalle.setAdicional(dto.getAdicional());
            detalle.setEstadoDProducto("PENDIENTE");
            detalle.setPedido(pedidoGuardado);
            Producto producto = productoRepository.findById(dto.getProductoId()).orElseThrow();
            detalle.setProducto(producto);
            detallePedidoService.creatrDetalleP(detalle);
        }

        status.setComplete();
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
    
    
    @GetMapping("/detallePedido/{id}")
    public String detallePedido(
            @PathVariable Long id,
            Model model){

        Pedido pedido = pedidoService
                .searchPedido(id)
                .orElseThrow();

        model.addAttribute("pedido", pedido);

        model.addAttribute("productos",
                productoRepository.findAll());

        model.addAttribute("categorias",
                categoriaService.getAllCategoria());

        return "mesero/detalle-pedido";
    }
    

    
    @GetMapping("/eliminarDetallePedido/{id}")
    public String eliminarDetallePedido(
            @PathVariable Long id){
        DetallePedido detalle =
                detallePedidoService.searchDetalle(id)
                .orElseThrow();
        Long pedidoId = detalle.getPedido().getId();
        detallePedidoService.deleteDetalle(id);
        return "redirect:/detallePedido/" + pedidoId;
    }
    
    
    @GetMapping("/deleteDetalleBD/{id}")
    public String eliminarDetalleBD(
            @PathVariable Long id){

        detallePedidoService.deleteDetalle(id);

        return "redirect:/pedidos";
    }
    
    @PostMapping("/saveDetallePedido")
    public String agregarDetallePedidoExistente(
            @RequestParam Long pedidoId,
            @RequestParam Long productoId,
            @RequestParam Integer cantidad,
            @RequestParam(required = false) String adicional){

        Pedido pedido = pedidoService
                .searchPedido(pedidoId)
                .orElseThrow();

        Producto producto = productoRepository
                .findById(productoId)
                .orElseThrow();

        DetallePedido detalle = new DetallePedido();

        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setAdicional(adicional);
        detalle.setEstadoDProducto("PENDIENTE");

        detallePedidoService.creatrDetalleP(detalle);

        return "redirect:/detallePedido/" + pedidoId;
    }
    
    //ELIMINAR UN PEDIDO
    @GetMapping("/deletePedido/{id}")
    public String eliminarPedido(@PathVariable Long id){
        pedidoService.deleteDelete(id);
        return "redirect:/pedidos";
    }
    
    
 // Ver demoras de un pedido (mesero)
    @GetMapping("/demoras/{pedidoId}")
    public String verDemoras(@PathVariable Long pedidoId, Model model){
        model.addAttribute("pedido", 
            pedidoService.searchPedido(pedidoId).orElseThrow());
        model.addAttribute("demoras", 
            demoraService.getDemorasPorPedido(pedidoId));
        return "mesero/demoras";
    }

    // Mesero notifica que ya informó al cliente
    @GetMapping("/notificarDemora/{id}")
    public String notificarDemora(@PathVariable Long id){
        Demora demora = demoraService.notificarDemora(id);
        return "redirect:/demoras/" + demora.getPedido().getId();
    }
}
