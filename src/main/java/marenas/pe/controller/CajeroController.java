package marenas.pe.controller;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import marenas.pe.model.Cliente;
import marenas.pe.model.DetallePedido;
import marenas.pe.model.DetalleProductoInsumo;
import marenas.pe.model.Factura;
import marenas.pe.model.Insumo;
import marenas.pe.model.Pedido;
import marenas.pe.service.IClienteService;
import marenas.pe.service.IDetalleProductoInsumoService;
import marenas.pe.service.IFacturaService;
import marenas.pe.service.IInsumoService;
import marenas.pe.service.IPedidoService;


@Controller
public class CajeroController {
	
	

	    @Autowired
	    private IPedidoService pedidoService;

	    @Autowired
	    private IClienteService clienteService;

	    @Autowired
	    private IFacturaService facturaService;
	    
	    @Autowired
	    private IDetalleProductoInsumoService detalleInsumoService;

	    @Autowired
	    private IInsumoService  insumoService;
	    
	    
	    

	    // Lista pedidos en estado LISTO
	   
	    @GetMapping("/cajero")
	    public String listarPedidosListos(Model model,
	            @RequestParam(required = false, defaultValue = "LISTO") String estado){
	      
	        model.addAttribute("pedidos",
	            pedidoService.getAllPedido()
	                .stream()
	                .filter(p -> p.getEstado().equals(estado)) // ← cambia "LISTO" por estado
	                .collect(java.util.stream.Collectors.toList())
	        );
	        model.addAttribute("estadoActual", estado);
	        return "cajero/pedidos";
	    }

	    // Ver detalle del pedido
	    @GetMapping("/cajero/detalle/{id}")
	    public String detallePedido(@PathVariable Long id, Model model){
	        Pedido pedido = pedidoService.searchPedido(id).orElseThrow();
	        pedido.calcularTotal(); // asegura que el total esté actualizado
	        model.addAttribute("pedido", pedido);
	        return "cajero/detalle";
	    }

	    // Formulario de pago
	    @GetMapping("/cajero/pagar/{id}")
	    public String formPago(@PathVariable Long id, Model model){
	        Pedido pedido = pedidoService.searchPedido(id).orElseThrow();
	        pedido.calcularTotal();
	        model.addAttribute("pedido", pedido);
	        // Calcula preview de IGV y total para mostrar en el form
	        BigDecimal subtotal = pedido.getTotal();
	        BigDecimal igv = subtotal.multiply(new BigDecimal("0.18"))
	                                 .setScale(2, RoundingMode.HALF_UP);
	        BigDecimal total = subtotal.add(igv);
	        model.addAttribute("subtotal", subtotal);
	        model.addAttribute("igv", igv);
	        model.addAttribute("totalFinal", total);
	        return "cajero/pagar";
	    }

	    // Registrar pago → crea cliente si no existe → crea factura
	    @PostMapping("/cajero/registrarPago")
	    public String registrarPago(
	            @RequestParam Long pedidoId,
	            @RequestParam String dni,
	            @RequestParam String nombre,
	            @RequestParam(required = false) String telefono,
	            @RequestParam String metodoPago){

	        // Si ya tiene factura, ir directo a verla
	        if(facturaService.buscarPorPedido(pedidoId).isPresent()){
	            Factura f = facturaService.buscarPorPedido(pedidoId).orElseThrow();
	            return "redirect:/cajero/factura/" + f.getId();
	        }

	        // 1. Buscar o crear cliente
	        Cliente cliente = clienteService.buscarPorDni(dni)
	            .orElseGet(() -> {
	                Cliente nuevo = new Cliente();
	                nuevo.setDni(dni);
	                nuevo.setNombre(nombre);
	                nuevo.setTelefono(telefono);
	                return clienteService.crearCliente(nuevo);
	            });

	        // 2. Obtener pedido y calcular totales
	        Pedido pedido = pedidoService.searchPedido(pedidoId).orElseThrow();
	        pedido.calcularTotal();

	        BigDecimal subtotal = pedido.getTotal();
	        BigDecimal igv = subtotal.multiply(new BigDecimal("0.18"))
	                                 .setScale(2, RoundingMode.HALF_UP);
	        BigDecimal total = subtotal.add(igv);

	        // 3. Crear factura
	        Factura factura = new Factura();
	        factura.setPedido(pedido);
	        factura.setCliente(cliente);
	        factura.setFechaEmision(LocalDateTime.now());
	        factura.setSubtotal(subtotal);
	        factura.setIgv(igv);
	        factura.setTotal(total);
	        factura.setMetodoPago(metodoPago);

	        Factura facturaGuardada = facturaService.crearFactura(factura);

	        // 4. Cerrar pedido
	        pedido.setEstado("CERRADO");
	        pedidoService.createPedido(pedido);

	        // 5. Descontar stock con protección
	        try {
	            for(DetallePedido dp : pedido.getDetalles()){
	                List<DetalleProductoInsumo> receta =
	                    detalleInsumoService.getByProducto(dp.getProducto().getId());

	                if(receta == null || receta.isEmpty()) continue;

	                for(DetalleProductoInsumo dpi : receta){
	                    if(dpi.getGasto() == null) continue;

	                    int cantidadUsada = (int)(dpi.getGasto() * dp.getCantidad());
	                    Insumo insumo = dpi.getInsumo();
	                    insumo.setStock(Math.max(insumo.getStock() - cantidadUsada, 0));
	                    insumoService.createInsumo(insumo);
	                }
	            }
	        } catch(Exception e){
	            System.out.println("Error descontando stock: " + e.getMessage());
	        }

	        return "redirect:/cajero/factura/" + facturaGuardada.getId();
	    }
	    
	 // Vista factura generada
	    @GetMapping("/cajero/factura/{id}")
	    public String verFactura(@PathVariable Long id, Model model){
	        Factura factura = facturaService.buscarPorId(id).orElseThrow();
	        
	        // Debug temporal
	        System.out.println("Factura id: " + factura.getId());
	        System.out.println("Cliente: " + factura.getCliente());
	        System.out.println("Pedido: " + factura.getPedido());
	        System.out.println("FechaEmision: " + factura.getFechaEmision());
	        System.out.println("Total: " + factura.getTotal());
	        
	        model.addAttribute("factura", factura);
	        return "cajero/factura";
	    }
	    
	    @GetMapping("/cajero/factura/{id}/pdf")
	    public ResponseEntity<byte[]> descargarFacturaPDF(@PathVariable Long id) throws Exception {
	        byte[] pdf = facturaService.generarPDFFactura(id);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDispositionFormData("filename", "Factura_" + id + ".pdf");
	        return ResponseEntity.ok().headers(headers).body(pdf);
	    }
}

