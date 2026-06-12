package marenas.pe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Column(name = "fechaHora_pedido",nullable = false)
    private LocalDateTime fechaHora;

    // abierto | en_preparacion | cerrado
    @Column(name = "estado_pedido" ,nullable = false, length = 20)
    private String estado = "abierto";

    @Column(precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

   

    // ManyToOne → Empleado
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    // OneToMany → DetallePedido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    // OneToOne → Comprobante


    // Constructores
    public Pedido() {
        this.fechaHora = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

   

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }

    
}