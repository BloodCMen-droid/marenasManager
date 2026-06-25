package marenas.pe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class DetalleProductoInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="id_insumo")
    private Insumo insumo;

    @Column(name = "gasto")
    private Double gasto;
    
    


	public DetalleProductoInsumo(Producto producto, Insumo insumo, Double cantidad) {
		this.producto = producto;
		this.insumo = insumo;
		this.gasto = cantidad;
	}


	public DetalleProductoInsumo() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Insumo getInsumo() {
		return insumo;
	}


	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}


	public Double getGasto() {
		return gasto;
	}


	public void setGasto(Double gasto) {
		this.gasto = gasto;
	}


	



    
}