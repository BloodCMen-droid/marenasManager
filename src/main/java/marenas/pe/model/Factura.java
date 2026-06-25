package marenas.pe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name="tbl_facturas")
public class Factura {


@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name = "id_factura")
private Long id;

@Column(name = "fech_emision_factura")
private LocalDateTime fechaEmision;

@Column(name = "total_factura")
private BigDecimal total;

@Column(name = "igv")
private BigDecimal igv;

@Column(name = "subtotal")
private BigDecimal subtotal; // total sin IGV

@Column(name = "metodo_pago")
private String metodoPago;

@OneToOne
@JoinColumn(name="pedido_id")
private Pedido pedido;

@ManyToOne
@JoinColumn(name="cliente_id")
private Cliente cliente;



public Factura(){}



public Cliente getCliente() {
	return cliente;
}



public void setCliente(Cliente cliente) {
	this.cliente = cliente;
}



public Long getId(){
return id;
}


public void setId(Long id){
this.id=id;
}


public LocalDateTime getFechaEmision(){
return fechaEmision;
}


public void setFechaEmision(LocalDateTime fechaEmision){
this.fechaEmision=fechaEmision;
}


public BigDecimal getTotal(){
return total;
}


public void setTotal(BigDecimal total){
this.total=total;
}


public Pedido getPedido(){
return pedido;
}


public void setPedido(Pedido pedido){
this.pedido=pedido;
}



public BigDecimal getIgv() {
	return igv;
}



public void setIgv(BigDecimal igv) {
	this.igv = igv;
}



public BigDecimal getSubtotal() {
	return subtotal;
}



public void setSubtotal(BigDecimal subtotal) {
	this.subtotal = subtotal;
}



public String getMetodoPago() {
	return metodoPago;
}



public void setMetodoPago(String metodoPago) {
	this.metodoPago = metodoPago;
}



}