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

    @Column(name="telefono_empleado",length = 20)
    private String telefono;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;
    
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    // Constructores
    public Empleado() {}



    public Empleado(String nombre, String telefono, List<Pedido> pedidos, Rol rol) {

		this.nombre = nombre;
		this.telefono = telefono;
		this.pedidos = pedidos;
		this.rol = rol;
	}



	// Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }


    public Rol getRol() {
		return rol;
	}



	public void setRol(Rol rol) {
		this.rol = rol;
	}



	public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}