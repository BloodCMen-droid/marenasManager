package marenas.pe.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;
    
    @Column(name = "nombre_rol")
    private String nombre; // ADMINISTRADOR, MESERO
    
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    private List<Empleado> empleado;
    



	public Rol(String nombre, List<Empleado> empleado) {

		this.nombre = nombre;
		this.empleado = empleado;
	}

	public Rol() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    
    
}