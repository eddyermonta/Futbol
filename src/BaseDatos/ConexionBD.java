/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**asass
 * @author eduar
 dsds*/
public class ConexionBD {

    private static Connection con;
    public static final String Ruta = "jdbc:mysql://localhost:3306/futbol";
    public static final String Usuario = "root"; 
    public static final String Contrasena = "";
    public static final String Driver = "com.mysql.cj.jdbc.Driver";

    public ConexionBD() {
        Conectar();
    }

    public static Connection Conectar() {
        if (con == null) {
            try {
                Class.forName(Driver);
                con = (Connection) DriverManager.getConnection(Ruta, Usuario, Contrasena);
                if (con != null) {
                    System.out.println("se conecto correctamente");
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Error:" + e);
            }
        }
       
        return con;
    }

}
