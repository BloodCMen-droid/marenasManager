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
@Table(name = "tbl_insumos")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumo")
    private Long id;

    @Column(name = "nombre_insumo", nullable = false, length = 100)
    private String nombre;

    @Column(name = "stock_insumo", nullable = false)
    private int stock;

    @Column(name = "stock_minimo_insumo", nullable = false)
    private int stockMinimo;

    @Column(name = "unidad_medida_insumo", nullable = false, length = 20)
    private String unidadMedida;

    @Column(name = "estado_insumo", nullable = false, length = 20)
    private String estado = "Normal";

    @OneToMany(mappedBy = "insumo")
    private List<DetalleProductoInsumo> detallesProducto;

    public Insumo() {
    }




    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }










	   public String getNombre() {
		return nombre;
	}

	   public void setNombre(String nombre) {
		   this.nombre = nombre;
	   }







	   public Long getId() {
		return id;
	}

	   public void setId(Long id) {
		   this.id = id;
	   }

	  

	   public int getStock() {
		   return stock;
	   }

	   public void setStock(int stock) {
		   this.stock = stock;
	   }

	 

	

	   public int getStockMinimo() {
		return stockMinimo;
	}



	   public void setStockMinimo(int stockMinimo) {
		   this.stockMinimo = stockMinimo;
	   }



	   public String getUnidadMedida() {
		   return unidadMedida;
	   }



	   public void setUnidadMedida(String unidadMedida) {
		   this.unidadMedida = unidadMedida;
	   }



	   public List<DetalleProductoInsumo> getDetallesProducto() {
		   return detallesProducto;
	   }

	   public void setDetallesProducto(List<DetalleProductoInsumo> detallesProducto) {
		   this.detallesProducto = detallesProducto;
	   }
	   
	   

}
