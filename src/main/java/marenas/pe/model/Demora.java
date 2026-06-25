package marenas.pe.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="tbl_demoras")
public class Demora {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer tiempoMinutos;


    private String motivo;


    private LocalDateTime fecha;
    
    @Column(name = "estado")
    private String estado = "PENDIENTE"; // PENDIENTE → NOTIFICADO



    @ManyToOne
    @JoinColumn(name="pedido_id")
    private Pedido pedido;



    public Demora(){}



    public Long getId(){
        return id;
    }


    public void setId(Long id){
        this.id=id;
    }


   

    public Integer getTiempoMinutos() {
		return tiempoMinutos;
	}



	public void setTiempoMinutos(Integer tiempoMinutos) {
		this.tiempoMinutos = tiempoMinutos;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getMotivo(){
        return motivo;
    }


    public void setMotivo(String motivo){
        this.motivo=motivo;
    }


    public LocalDateTime getFecha(){
        return fecha;
    }


    public void setFecha(LocalDateTime fecha){
        this.fecha=fecha;
    }


    public Pedido getPedido(){
        return pedido;
    }


    public void setPedido(Pedido pedido){
        this.pedido=pedido;
    }

}