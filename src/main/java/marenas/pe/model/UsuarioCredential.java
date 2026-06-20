package marenas.pe.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tbl_Usuario_Credential")
public class UsuarioCredential {


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @JoinColumn(name = "id_usuario")
	    private Long id;

	    
	    @Column(name = "email_usuario")
	    private String email;

	    @Column(name = "passoword_usuario")
	    private String password;


	    @ManyToOne
	    @JoinColumn(name = "id_rol")
	    private Rol rol;


	    @OneToOne(mappedBy = "usuarioCredential")
	    private Empleado empleado;


		public UsuarioCredential() {

		}
		
		@OneToMany(mappedBy = "usuarioCredential")
	    private List<Pedido> pedidos;



	


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public Rol getRol() {
			return rol;
		}


		public void setRol(Rol rol) {
			this.rol = rol;
		}


		public Empleado getEmpleado() {
			return empleado;
		}


		public void setEmpleado(Empleado empleado) {
			this.empleado = empleado;
		}

	    
			  

		  
		
	}


