package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author papitojaime
 */
@Entity
@Table(name="Producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;
    private int idCategoria;
    private String nombre;
    private byte[] imagen;
    private String presentacion;
    private String marca;
    private String nombreComercial;
    private String sustancia;
    private String fraccion;
    private String claveSat;
    private boolean receta;
    private String codBarras;
    private BigDecimal precio;
    private BigDecimal costo;
    private float iva;
    private String proveedor;
    private int existencia;
    
    
}
