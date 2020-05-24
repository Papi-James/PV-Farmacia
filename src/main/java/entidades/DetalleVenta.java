package entidades;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author papitojaime
 */
@Entity
@Table(name="DetalleVenta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDVenta;
    private int idProducto;
    private int idMedico;
    private int idVenta;
    private int cantidad;
    private BigDecimal precio;
    private boolean tipoVenta;
    
}
