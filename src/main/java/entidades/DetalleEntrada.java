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
@Table(name="DetalleEntrada")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleEntrada {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDEntrada;
    private int idProducto;
    private int idEntrada;
    private int cantidad;
    private BigDecimal costo;
}
