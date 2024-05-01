/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Modelo.Jugador;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author eduar
 */
public class InfraccionesBD {
    
    public void Updateinfraccion(Jugador jugador){
        try{
            String sql="update jugador as j,persona as p\n" +
                       "set j.CantidadInfracciones = ? \n" +
                       "where p.id_Persona=j.Persona_id_Persona\n" +
                       "and p.Nombre='"+jugador.getNombre()+"'";
            
            PreparedStatement statement = ConexionBD.Conectar().prepareStatement(sql);
            statement.setInt(1, jugador.getCantidad_infracciones());
            
            int rowsUpdated = statement.executeUpdate();
             if (rowsUpdated > 0) {
                 System.out.println("infraccion Actualizada en bd");
             }
            
        }catch(SQLException ex){
            Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
     public void Updateinfraccion(Jugador jugador, int dato){
        try{
            String sql="update jugador as j,persona as p\n" +
                       "set j.CantidadInfracciones = ? , j.Asistencia=false\n" +
                       "where p.id_Persona=j.Persona_id_Persona\n" +
                       "and p.Nombre='"+jugador.getNombre()+"'";
            
            PreparedStatement statement = ConexionBD.Conectar().prepareStatement(sql);
            statement.setInt(1, jugador.getCantidad_infracciones());
            
            int rowsUpdated = statement.executeUpdate();
             if (rowsUpdated > 0) {
                 System.out.println("infraccion Actualizada en bd");
             }
            
        }catch(SQLException ex){
            Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void updatePenalizacion(Jugador jugador){
         try{
            String sql="update jugador as j,persona as p\n" +
                        "set j.penalizado= ? , j.FormaJuego = 'solidaria'\n" +
                        "where p.id_Persona=j.Persona_id_Persona\n" +
                        "and p.Nombre='"+jugador.getNombre()+"'";
            
            
            PreparedStatement statement = ConexionBD.Conectar().prepareStatement(sql);
            statement.setBoolean(1, jugador.isPenalizado());
            
            int rowsUpdated = statement.executeUpdate();
             if (rowsUpdated > 0) {
                 System.out.println("penalizacion Actualizada en bd");
             }
            
        }catch(SQLException ex){
            Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
