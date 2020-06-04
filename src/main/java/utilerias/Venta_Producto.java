package utilerias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author papitojaime
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta_Producto implements Serializable{
    private int id;
    private int idProducto;
    private String nombre;
    private String presentacion;
    private String marca;
    private String sustancia;
    private boolean receta;
    private BigDecimal precio;
    private BigDecimal subtotal;
    private float iva;
    private int idMedico;
    private int cantidad;
    private String barras;
    private boolean tipoVenta;
}
