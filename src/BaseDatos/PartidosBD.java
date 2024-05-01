/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eduar
 * INSERT INTO partido (`Administrador_idAdministrador`) VALUES ('1');
 */
public class PartidosBD {
    
       public void insertarPartido() {
        AdministradorBD abd= new AdministradorBD();
        PreparedStatement st;
        try {
            st = ConexionBD.Conectar().prepareStatement(
                    "INSERT INTO partido"
                    + "(Administrador_idAdministrador)"
                    + "VALUES (?)");
           
            st.setInt(1,abd.buscarIndice());
           
            st.execute();
            
        } catch (SQLException e) {
            System.err.println("error " + e);
        }

    }
       
         public int BuscarIndicePartido(){
         int indice=-1;
         Statement query;
         ResultSet rs;
         try{
            query=ConexionBD.Conectar().createStatement();
            rs=query.executeQuery("SELECT idPartido FROM partido;"); 
             while (rs.next()) { 
                 indice=rs.getInt("idPartido");
             }
         }catch(SQLException ex){
             Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
         }
         return indice;
         
     }
         
         public static void main(String[] args) {
             PartidosBD pbd = new PartidosBD();
             System.out.println(pbd.BuscarIndicePartido());
    }
    
}
