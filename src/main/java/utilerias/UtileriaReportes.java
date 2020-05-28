/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dao.CategoriaDAO;

/**
 *
 * @author ramms
 */
public class UtileriaReportes {
    
        private Connection con;
    
    public Connection conecta() {

        String user = "postgres";
        String password = "ContraChida";
        Properties datos = new Properties();
        datos.put("user", user);
        datos.put("password", password);
        String url = "jdbc:postgresql://localhost:5432/Farmacia";
        String postgreSQLDriver = "org.postgresql.Driver";

        try {
            Class.forName(postgreSQLDriver);
            con = DriverManager.getConnection(url, datos);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UtileriaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
}
