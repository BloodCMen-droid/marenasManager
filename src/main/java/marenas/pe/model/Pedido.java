package marenas.pe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Column(name = "fechaHora_pedido", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado_pedido", nullable = false, length = 20)
    private String estado = "abierto";

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    // ==================== RELACIONES ====================

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioCredential usuarioCredential;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Demora> demoras = new ArrayList<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Factura factura;

    // ==================== CONSTRUCTOR ====================

    public Pedido() {
        this.fechaHora = LocalDateTime.now();
        this.detalles = new ArrayList<>();
        this.demoras = new ArrayList<>();
    }

    // ==================== MÉTODO PARA CALCULAR TOTAL ====================

    public void calcularTotal() {
        if (detalles == null || detalles.isEmpty()) {
            this.total = BigDecimal.ZERO;
            return;
        }

        this.total = detalles.stream()
                .map(detalle -> detalle.getProducto().getPrecio()
                        .multiply(BigDecimal.valueOf(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }

    public UsuarioCredential getUsuarioCredential() { return usuarioCredential; }
    public void setUsuarioCredential(UsuarioCredential usuarioCredential) { 
        this.usuarioCredential = usuarioCredential; 
    }

    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { 
        this.detalles = detalles != null ? detalles : new ArrayList<>(); 
    }

    public List<Demora> getDemoras() { return demoras; }
    public void setDemoras(List<Demora> demoras) { 
        this.demoras = demoras != null ? demoras : new ArrayList<>(); 
    }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
}