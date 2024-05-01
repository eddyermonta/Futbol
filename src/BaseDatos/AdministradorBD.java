/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Modelo.Administrador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AdministradorBD {

    public void insertaPersonaAdmin(Administrador admin) {
        PreparedStatement st;
        try {
            st = ConexionBD.Conectar().prepareStatement(
                    "INSERT INTO futbol.persona"
                    + "(id_Persona,Cedula,Usuario,Contrasena,Nombre,Correo,Rol)"
                    + "VALUES (?,?,?,?,?,?,?)");
            st.setInt(1, 0);
            st.setString(2, admin.getCedula());
            st.setString(3, admin.getUsuario());
            st.setString(4, admin.getContrasena());
            st.setString(5, admin.getNombre());
            st.setString(6, admin.getCorreo());
            st.setString(7, admin.getRol());
           // st.execute();
            st.execute();
            
        } catch (SQLException e) {
            System.err.println("error " + e);
        }

    }

    public int buscarIndice() {
        int indice = 0;
        Statement query;
        ResultSet rs;
        try {
            query = ConexionBD.Conectar().createStatement();
            rs = query.executeQuery(
                    "SELECT id_Persona FROM persona where Rol='admin'");
            while (rs.next()) {
                indice= rs.getInt("id_Persona");
            }
        } catch (Exception e) {
            System.err.println("error " + e);
        }
        return indice;
    }

    public void InserteAdmin() {
        PreparedStatement st;
        try {
            st = ConexionBD.Conectar().prepareStatement(
                    "INSERT INTO futbol.administrador"
                    + "(idAdministrador, Persona_id_Persona)"
                    + "VALUES(?,?)");
            st.setInt(1, 0);
            
            st.setInt(2, buscarIndice());
            
            st.execute();

        } catch (SQLException e) {

        }
    }

    public void registrarAdministrador(Administrador admin) {
        insertaPersonaAdmin(admin);
        InserteAdmin();

    }
}
