package marenas.pe.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "tbl_cliente")
public class Cliente {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long id;

	@Column(name = "nombre_cliente", nullable = false  )
	private String nombre;

	@Column(name = "dni_cliente", nullable = false, unique= true  )
	private String dni;

	@Column(name = "telefono_cliente" )
	private String telefono;



	@OneToMany(mappedBy="cliente")
	private List<Factura> facturas;



	public Cliente() {
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



	public String getDni() {
		return dni;
	}



	public void setDni(String dni) {
		this.dni = dni;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public List<Factura> getFacturas() {
		return facturas;
	}



	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}


	
	
	}


