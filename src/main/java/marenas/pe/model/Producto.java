package marenas.pe.model;
 
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
 
@Entity
@Table(name = "tbl_productos")
public class Producto {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Long id;
 
    @Column(name="nombre_producto",nullable = false, length = 150)
    private String nombre;
 
    @Column(name="descripcion_producto",length = 255)
    private String descripcion;
 
    @Column(name="precio_producto",nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
 
    @Column(name="activo_producto",nullable = false)
    private Boolean activo = true;
 
    // ManyToOne → Categoria (muchos productos pertenecen a una categoría)
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
 

    // Constructores
    public Producto() {}
 
    public Producto(String nombre, String descripcion, BigDecimal precio, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }
 
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
 
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
 
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
 
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
 
   
}