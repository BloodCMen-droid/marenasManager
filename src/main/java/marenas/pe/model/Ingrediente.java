package marenas.pe.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ingredientes")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 50)
    private String unidad; // kg, litros, unidades, etc.

    @ManyToMany(mappedBy = "ingredientes")
    private List<Producto> productos;

    // Constructores
    public Ingrediente() {}

    public Ingrediente(String nombre, String unidad) {
        this.nombre = nombre;
        this.unidad = unidad;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
}