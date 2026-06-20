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
    
    @Column(name="dni_empleado",nullable = false, length = 150)
    private String dni;
    
    





	@Column(name="telefono_empleado",length = 20)
    private String telefono;

 
    

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) //Elimina el usuario 
    @JoinColumn(name = "id_usuario")
    private UsuarioCredential usuarioCredential;
    

    
    // Constructores
    public Empleado() {}

	public String getDni() {
		return dni;
	}



	public void setDni(String dni) {
		this.dni = dni;
	}

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



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	


	public UsuarioCredential getUsuarioCredential() {
		return usuarioCredential;
	}



	public void setUsuarioCredential(UsuarioCredential usuarioCredential) {
		this.usuarioCredential = usuarioCredential;
	}



	// Getters y Setters
    
}