package marenas.pe.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_empleado")
    private Long id;

    @Column(name="nombre_empleado",nullable = false, length = 150)
    private String nombre;

    @Column(name="cargo_empleado",length = 100)
    private String cargo; // mozo, cocinero, cajero, etc.

    @Column(name="telefono_empleado",length = 20)
    private String telefono;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    // Constructores
    public Empleado() {}

    public Empleado(String nombre, String cargo, String telefono) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}