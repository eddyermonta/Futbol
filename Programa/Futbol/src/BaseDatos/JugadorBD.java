/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;


import Modelo.Identificacion;
import Modelo.Jugador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eduar
 */
public class JugadorBD {
    
     public void insertaPersonaJugador(Jugador jugador) {
        PreparedStatement st;
        try {
            st = ConexionBD.Conectar().prepareStatement(
                    "INSERT INTO persona"
                    + "(id_Persona,Cedula,Usuario,Contrasena,Nombre,Correo,Rol)"
                    + "VALUES (?,?,?,?,?,?,?)");
            st.setInt(1, 0);
            st.setString(2, jugador.getCedula());
            st.setString(3, jugador.getUsuario());
            st.setString(4, jugador.getContrasena());
            st.setString(5, jugador.getNombre());
            st.setString(6, jugador.getCorreo());
            st.setString(7, jugador.getRol());
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
                    "SELECT id_Persona FROM persona where Rol='jugador'");
            while (rs.next()) {
                indice= rs.getInt("id_Persona");
            }
        } catch (Exception e) {
            System.err.println("error " + e);
        }
        return indice;
    }
    
    public int contarJug(){
        Statement query;
        ResultSet rs;
       int cuenta=0;
        try{
            query=ConexionBD.Conectar().createStatement();
            rs=query.executeQuery("SELECT COUNT(*)FROM jugador");
            while (rs.next()) {                
                cuenta=rs.getInt(1);
            }
            
        }catch(SQLException e){
            System.err.println("err"+e);
        }
         System.out.println(cuenta);
         return cuenta;
        
    }
    
    public int contarJugFrecuente(){
        Statement query;
        ResultSet rs;
       int cuenta=0;
        try{
            query=ConexionBD.Conectar().createStatement();
            rs=query.executeQuery("SELECT COUNT(*)FROM jugador where ModoSuscripcion='frecuente'");
            while (rs.next()) {                
                cuenta=rs.getInt(1);
            }
            
        }catch(SQLException e){
            System.err.println("err"+e);
        }
         System.out.println(cuenta);
         return cuenta;
        
    }
    
    public void InserteJug(Jugador jug) {
        PreparedStatement st;
        try {
            st = ConexionBD.Conectar().prepareStatement(
                    "INSERT INTO jugador"+
                    "(idJugador,"
                    + "PromedioGeneral,"
                    + "Asistencia,"
                    + "CantidadInfracciones,"
                    + "CantidadPartidosJugados,"
                    + "FormaJuego,"
                    + "ModoSuscripcion,"
                    + "Penalizado,"
                    + "Persona_id_Persona)"+ 
                    "VALUES (?, ?, ?, ?, ?, ?, ?,?,?)");
                    
            st.setInt(1, 0);
            st.setDouble(2, jug.getPromedio_general());
            st.setBoolean(3, jug.isAsistio());
            st.setInt(4, jug.getCantidad_infracciones());
            st.setInt(5, jug.getCantidad_partidos_jugados());
            st.setString(6, jug.getForma_juego());
            st.setString(7, jug.getModo_suscripcion());
            st.setBoolean(8, jug.isPenalizado());
            st.setInt(9, buscarIndice());
            
            st.execute();

        } catch (SQLException e) {

        }
    }

    public void registrarJugador(Jugador jug) {
        insertaPersonaJugador(jug);
        InserteJug(jug);

    }
    
    public ArrayList<Jugador> llenarjJugador(){
        Statement query;
        ResultSet rs;
        ArrayList<Jugador> listajugadores = new ArrayList<>();
        Jugador jugador = new Jugador();
        int i=0;
        try{
            query=ConexionBD.Conectar().createStatement();
            rs=query.executeQuery("SELECT * from jugador,persona where id_Persona=Persona_id_Persona ");
            while (rs.next()) {       
               
                
                jugador.setCantidad_infracciones(rs.getInt("CantidadInfracciones"));
                jugador.setCantidad_partidos_jugados(rs.getInt("CantidadPartidosJugados"));
                jugador.setCedula(rs.getString("Cedula"));
                jugador.setCorreo(rs.getString("Correo"));
                jugador.setForma_juego(rs.getString("FormaJuego"));
                jugador.setModo_suscripcion( rs.getString("ModoSuscripcion"));
                jugador.setNombre(rs.getString("Nombre"));
                jugador.setPenalizado(rs.getBoolean("Penalizado"));
                jugador.setPromedio_general(rs.getDouble("PromedioGeneral"));
                jugador.setRol(rs.getString("Rol"));
                jugador.setUsuario(rs.getString("Usuario"));
                jugador.setContrasena(rs.getString("Contrasena"));
                jugador.setAsistio(rs.getBoolean("Asistencia"));
               
                listajugadores.add(i,jugador);
                 jugador = new Jugador();
                i++;
            }
            
        }catch(SQLException e){
            System.err.println("err"+e);
        }
        return listajugadores;
    }
    
    public Jugador LlenarJugador(Identificacion datosInicio){
        Statement query;
        ResultSet rs;
        Jugador jugador = new Jugador();
        try{
            query=ConexionBD.Conectar().createStatement();
            rs=query.executeQuery("SELECT * from persona,jugador where Usuario="+"'"+datosInicio.getUsuario()+"'"+
                    "and id_Persona=Persona_id_Persona");
            while (rs.next()) { 
                jugador.setCedula(rs.getString("Cedula"));
                jugador.setCorreo(rs.getString("Correo"));
                jugador.setNombre(rs.getString("Nombre"));
                jugador.setAsistio(rs.getBoolean("Asistencia"));
                jugador.setCantidad_infracciones(rs.getInt("CantidadInfracciones"));
                jugador.setCantidad_partidos_jugados(rs.getInt("CantidadPartidosJugados"));
                jugador.setForma_juego(rs.getString("FormaJuego"));
                jugador.setModo_suscripcion(rs.getString("ModoSuscripcion"));
                jugador.setPenalizado(rs.getBoolean("Penalizado"));
                jugador.setPromedio_general(rs.getDouble("PromedioGeneral"));
                
            }}
        catch(SQLException e){
            System.err.println("err"+e);
        }
        return jugador;
    }
    
    public void updateJugador(Jugador jugador){
         try {
             String sql = "update jugador,persona set asistencia = ? \n" +
            "where Persona_id_Persona = id_Persona\n" +
            "and Nombre='"+jugador.getNombre()+"'";
             
             PreparedStatement statement = ConexionBD.Conectar().prepareStatement(sql);
             statement.setBoolean(1, jugador.isAsistio());
             
             
             int rowsUpdated = statement.executeUpdate();
             if (rowsUpdated > 0) {
                 
             }} catch (SQLException ex) {
             Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
     public boolean updateJugadorModo(Jugador jugador){
         try {
             String sql = "update jugador,persona set ModoSuscripcion  = ? \n" +
            "where Persona_id_Persona = id_Persona\n" +
            "and Nombre='"+jugador.getNombre()+"'";

             PreparedStatement statement = ConexionBD.Conectar().prepareStatement(sql);
             statement.setString(1, jugador.getModo_suscripcion());
             
             int rowsUpdated = statement.executeUpdate();
             if (rowsUpdated > 0) {
                 
             }
              return true;
         } catch (SQLException ex) {
             Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
             return false;
         }
    }
}
