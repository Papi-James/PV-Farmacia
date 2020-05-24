package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
@Table(name="Venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenta;
    private int idUsuario;
    private Timestamp fecha;
    private BigDecimal total;
    
}
