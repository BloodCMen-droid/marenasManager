package marenas.pe.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprobantes")
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // boleta | factura
    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(length = 11)
    private String ruc; // solo si es factura

    @Column(length = 150)
    private String razonSocial; // solo si es factura

    // OneToOne → Pedido (lado dueño de la relación)
    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    // Constructores
    public Comprobante() {
        this.fechaEmision = LocalDateTime.now();
    }

    public Comprobante(String tipo, Pedido pedido) {
        this.tipo = tipo;
        this.pedido = pedido;
        this.fechaEmision = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}