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

    @Column(name = "dp_cantidad",nullable = false)
    private Integer cantidad;

    @Column(name = "dp_precioUnitario",nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // ManyToOne → Pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // ManyToOne → Producto
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Constructores
    public DetallePedido() {}

    public DetallePedido(Integer cantidad, BigDecimal precioUnitario, Pedido pedido, Producto producto) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.pedido = pedido;
        this.producto = producto;
    }

    // Subtotal calculado (no se persiste en BD)
    @Transient
    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}