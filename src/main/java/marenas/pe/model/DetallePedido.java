package marenas.pe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_detallePedidos")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dp_id")
    private Long id;


    @Column(name = "dp_cantidad", nullable = false)
    private Integer cantidad;





    @Column(name = "dp_estado_producto")
    private String estadoDProducto = "PENDIENTE";


    @Column(name = "dp_adicional")
    private String adicional;


    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;


    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;



    public DetallePedido() {
    }



    public DetallePedido(Integer cantidad, String estadoDProducto, String adicional, Pedido pedido, Producto producto) {
		this.cantidad = cantidad;
		this.estadoDProducto = estadoDProducto;
		this.adicional = adicional;
		this.pedido = pedido;
		this.producto = producto;
	}

	public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Integer getCantidad() {
        return cantidad;
    }


    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public String getEstadoDProducto() {
        return estadoDProducto;
    }


    public void setEstadoDProducto(String estadoDProducto) {
        this.estadoDProducto = estadoDProducto;
    }


    public String getAdicional() {
        return adicional;
    }


    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }


    public Pedido getPedido() {
        return pedido;
    }


    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }


    public Producto getProducto() {
        return producto;
    }


    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
