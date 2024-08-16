package sysventa.sistema_ventas_back.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nombre;
    private Double precio;
    private Integer stock;
    private boolean estado;
    private String imagen;

    @ManyToOne(targetEntity = Categoria.class)
    @JsonBackReference
    private Categoria categoria;

    @ManyToMany(targetEntity = Proveedor.class, fetch = FetchType.LAZY)
    @JoinTable(name = "producto_proveedor", joinColumns = @JoinColumn(name = "producto"), inverseJoinColumns = @JoinColumn(name = "proveedor"))
    private List<Proveedor> provedores;

    @ManyToOne(targetEntity = Usuario.class)
    @JsonManagedReference
    @JoinColumn(name = "usuario_alta_id")
    private Usuario usuarioAltaId;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne(targetEntity = Usuario.class)
    @JsonBackReference
    @JoinColumn(name = "usuario_modi_id")
    private Usuario usuarioModiId;

    @Column(name = "fecha_modi")
    private Date fechaMofi;

    @ManyToOne(targetEntity = Marca.class)
    private Marca marca;

}
