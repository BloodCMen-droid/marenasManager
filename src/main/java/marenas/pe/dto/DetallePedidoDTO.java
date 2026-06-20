package marenas.pe.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class DetallePedidoDTO {

	private UUID idTemporal = UUID.randomUUID();
	
    public UUID getIdTemporal() {
		return idTemporal;
	}


	public void setIdTemporal(UUID idTemporal) {
		this.idTemporal = idTemporal;
	}


	private Long productoId;

    private String productoNombre;

    private Integer cantidad;

    private String adicional;

    private BigDecimal precio;

    private BigDecimal subtotal;


    public Long getProductoId() {
        return productoId;
    }


    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }


    public String getProductoNombre() {
        return productoNombre;
    }


    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }


    public Integer getCantidad() {
        return cantidad;
    }


    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public String getAdicional() {
        return adicional;
    }


    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }


    public BigDecimal getPrecio() {
        return precio;
    }


    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }


    public BigDecimal getSubtotal() {
        return subtotal;
    }


    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}