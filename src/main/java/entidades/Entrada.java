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
@Table(name="Entrada")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrada implements Serializable{
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEntrada;
    private int idUsuario;
    private String proveedor;
    private String numFactura;
    private BigDecimal total;
    private Timestamp fecha;
}
