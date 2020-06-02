package entidades;

import java.io.Serializable;
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
@Table(name="Usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    private String nombreUsuario;
    private String nombre;
    private String paterno;
    private String materno;
    private String email;
    private String contrasenia;
    private int tipoUsuario;
    
}
