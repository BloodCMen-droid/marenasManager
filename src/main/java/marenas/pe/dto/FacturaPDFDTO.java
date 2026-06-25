package marenas.pe.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FacturaPDFDTO {
	
	  // === EMPRESA (estático) ===
    private String empresaNombre = "Marenas Grill";
    private String empresaRuc = "20601234567";
    
    // === FACTURA ===
    private Long facturaId;
    private LocalDateTime fechaEmision;
    private String metodoPago;
    
    // === CLIENTE ===
    private String clienteNombre;
    private String clienteDni;
    private String clienteTelefono;
    
    // === PEDIDO / AUDITORÍA ===
    private Long pedidoId;
    private Integer mesaNumero;
    private String meseroNombre;
    private LocalDateTime fechaPedido;
    
    // === DETALLE (una fila por producto) ===
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precioUnit;
    private BigDecimal subtotalLinea;
    
    // === TOTALES ===
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    
    // Constructor vacío
    public FacturaPDFDTO() {}
    
    // Getters y Setters de todos los campos
    public String getEmpresaNombre() { return empresaNombre; }
    public void setEmpresaNombre(String empresaNombre) { this.empresaNombre = empresaNombre; }
    
    public String getEmpresaRuc() { return empresaRuc; }
    public void setEmpresaRuc(String empresaRuc) { this.empresaRuc = empresaRuc; }
    
    public Long getFacturaId() { return facturaId; }
    public void setFacturaId(Long facturaId) { this.facturaId = facturaId; }
    
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    
    public String getClienteDni() { return clienteDni; }
    public void setClienteDni(String clienteDni) { this.clienteDni = clienteDni; }
    
    public String getClienteTelefono() { return clienteTelefono; }
    public void setClienteTelefono(String clienteTelefono) { this.clienteTelefono = clienteTelefono; }
    
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    
    public Integer getMesaNumero() { return mesaNumero; }
    public void setMesaNumero(Integer mesaNumero) { this.mesaNumero = mesaNumero; }
    
    public String getMeseroNombre() { return meseroNombre; }
    public void setMeseroNombre(String meseroNombre) { this.meseroNombre = meseroNombre; }
    
    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }
    
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    
    public BigDecimal getPrecioUnit() { return precioUnit; }
    public void setPrecioUnit(BigDecimal precioUnit) { this.precioUnit = precioUnit; }
    
    public BigDecimal getSubtotalLinea() { return subtotalLinea; }
    public void setSubtotalLinea(BigDecimal subtotalLinea) { this.subtotalLinea = subtotalLinea; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public BigDecimal getIgv() { return igv; }
    public void setIgv(BigDecimal igv) { this.igv = igv; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}


