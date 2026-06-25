package marenas.pe.controller;

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

import marenas.pe.model.Insumo;
import marenas.pe.service.IDetalleProductoInsumoService;
import marenas.pe.service.IInsumoService;

@Controller
public class InventarioController {
	
	@Autowired
    private IInsumoService insumoService;

    @Autowired
    private IDetalleProductoInsumoService detalleInsumoService;

    // Lista insumos con estado
    @GetMapping("/inventario")
    public String listarInsumos(Model model){
        List<Insumo> insumos = insumoService.getAllInsumo();
        model.addAttribute("insumos", insumos);

        // Lista de compras = insumos en estado CRÍTICO o AGOTADO
        List<Insumo> listaCompras = insumos.stream()
            .filter(i -> i.getEstado().equals("CRÍTICO") 
                      || i.getEstado().equals("AGOTADO"))
            .collect(java.util.stream.Collectors.toList());
        model.addAttribute("listaCompras", listaCompras);

        return "inventario/inventario";
    }

    // Agregar stock
    @GetMapping("/inventario/addStock/{id}")
    public String formStock(@PathVariable Long id, Model model){
        model.addAttribute("insumo", 
            insumoService.searchInsumo(id).orElseThrow());
        return "inventario/anadir-stock";
    }

    @PostMapping("/inventario/saveStock")
    public String guardarStock(
            @RequestParam Long id,
            @RequestParam int cantidad){
        insumoService.agregarStock(id, cantidad);
        return "redirect:/inventario";
    }
    
    
    @GetMapping("/inventario/exportarCompras")
    public ResponseEntity<byte[]> exportarCompras() throws Exception {
        byte[] pdf = insumoService.exportarListaCompras();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "Lista_Compras.pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
