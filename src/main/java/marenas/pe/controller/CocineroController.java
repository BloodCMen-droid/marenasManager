package marenas.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import marenas.pe.model.Demora;
import marenas.pe.model.DetallePedido;
import marenas.pe.model.Pedido;
import marenas.pe.service.IDemoraService;
import marenas.pe.service.IDetallePedidoService;
import marenas.pe.service.IPedidoService;

@Controller
public class CocineroController {

    @Autowired
    private IPedidoService pedidoService;

    @Autowired
    private IDetallePedidoService detallePedidoService;
    
    @Autowired
    private IDemoraService demoraService;


    // Lista pedidos PENDIENTES para que el cocinero los vea
    @GetMapping("/cocina")
    public String listarPedidosCocina(Model model){
        model.addAttribute("pedidos", 
            pedidoService.getAllPedido()
                .stream()
                .filter(p -> p.getEstado().equals("PENDIENTE"))
                .collect(java.util.stream.Collectors.toList())
        );
        return "cocinero/cocina";
    }

    // Cambia estado de un detalle a PREPARADO
    // y verifica si el pedido completo está listo
    @GetMapping("/prepararDetalle/{id}")
    public String prepararDetalle(@PathVariable Long id){
        DetallePedido detalle = detallePedidoService
            .searchDetalle(id)
            .orElseThrow();

        detalle.setEstadoDProducto("PREPARADO");
        detallePedidoService.creatrDetalleP(detalle); // guarda el cambio

        // Verifica si todos los detalles están PREPARADO
        // y si es así cambia el pedido a LISTO
        detallePedidoService.verificarEstadoPedido(
            detalle.getPedido().getId()
        );

        return "redirect:/cocina";
    }
    
 // Vista detalle del pedido para el cocinero
    @GetMapping("/cocinaPedido/{id}")
    public String detalleCocina(@PathVariable Long id, Model model){
        model.addAttribute("pedido", pedidoService.searchPedido(id).orElseThrow());
        model.addAttribute("demora", new Demora()); // form vacío
        return "cocinero/detalle-cocina";
    }

    // Registrar demora
    @PostMapping("/saveDemora")
    public String guardarDemora(
            @RequestParam Long pedidoId,
            @RequestParam Integer tiempoMinutos,
            @RequestParam String motivo){

        Pedido pedido = pedidoService.searchPedido(pedidoId).orElseThrow();
        
        Demora demora = new Demora();
        demora.setPedido(pedido);
        demora.setTiempoMinutos(tiempoMinutos);
        demora.setMotivo(motivo);
        
        demoraService.crearDemora(demora);
        
        return "redirect:/cocinaPedido/" + pedidoId;
    }
}