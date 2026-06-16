package marenas.pe.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_mesa")
public class Mesa {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mesa")
    private Long id;
 
    @Column(name = "numero_mesa",nullable = false, unique = true)
    private int numero;
 
    @Column(name = "capacidad_mesa",nullable = false)
    private int capacidad;
 
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;
 
    // Constructores
    public Mesa() {}
 
    public Mesa(Integer numero, Integer capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
    }
 
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
 
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
 
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}
 



