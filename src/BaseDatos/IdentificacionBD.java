/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Modelo.Identificacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author eduar
 */
public class IdentificacionBD {

    public Identificacion BuscarUsuario(String username) {  //username de la vista
        Statement st;
        ResultSet rs;
        Identificacion ideBase = new Identificacion();
        try {
            st = ConexionBD.Conectar().createStatement();
            rs = st.executeQuery("select * from persona where usuario ='" + username + "'");

            while (rs.next()) {
                ideBase.setUsuario(rs.getString("usuario"));
                ideBase.setContrasena(rs.getString("contrasena"));
                ideBase.setRol(rs.getString("Rol"));        
            } 
        } catch (SQLException e) {
        }
        return ideBase;
    }
    
    public boolean ExisteAdminBD(Identificacion IdeFormu){
        Statement st;
        ResultSet rs;
        boolean estado=false;
        
        try {
            st = ConexionBD.Conectar().createStatement();
            rs = st.executeQuery("select * from persona,administrador "
                    + "where Rol ='admin' "
                    + "and persona.id_Persona = administrador.Persona_id_Persona ");
            Identificacion ideBase = new Identificacion();
            while (rs.next()) {
                ideBase.setRol(rs.getString("Rol"));        
            } 
            if(IdeFormu.getRol().equals(ideBase.getRol())){
                estado=true;
                JOptionPane.showMessageDialog(null, "ya existe un admin en la base de datos");
            }
            
            
        } catch (SQLException e) {
            System.err.println("error");
        }
        
       
        return estado;
    }
            
            

    public Boolean validarUsuario(Identificacion ideBase, Identificacion vista) {
        boolean estado = false;
 
            if(ideBase.getUsuario()!=null){
            
                if (ideBase.getUsuario().equals(vista.getUsuario()) && ideBase.getContrasena()
                        .equals(vista.getContrasena())==false)
                    
                    System.out.println("contraseña incorrecta");
                else if (ideBase.getUsuario().equals(vista.getUsuario()) 
                        && (ideBase.getContrasena().equals(vista.getContrasena()) == false 
                        || ideBase.getContrasena().equals(vista.getContrasena()) == true)
                        && ideBase.getRol().equals(vista.getRol()) == false) 
                    System.out.println("rol incorrecto");
                else estado=true;

            }else System.out.println("no existe en la base de datos");
            
        return estado;
    }
    
    
}
