
package utilerias;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author ramms
 */
public class UtileriaReportes {
    
        private Connection con;
    
        BasicDataSource basicDataSource = new BasicDataSource();
    
    public Connection conecta() throws SQLException {
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("197098");
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/Farmacia");
        basicDataSource.setValidationQuery("select 1");
        con = null;
        try {
            DataSource dataSource = basicDataSource;
            con = dataSource.getConnection();
            System.out.println("Conexion establecida");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;

    }
    
}
