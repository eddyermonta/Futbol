/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Modelo.Calificaciones;
import Modelo.Jugador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eduar
 */
public class CalificacionBD {
    public void UpdatePartidos(Jugador jugador){
        try{
            String sql="update jugador as j,persona as p\n" +
                       "set j.CantidadPartidosJugados=? \n" +
                       "where p.id_Persona=j.Persona_id_Persona\n" +
                       "and p.Nombre='"+jugador.getNombre()+"'";
            
            PreparedStatement statement = ConexionBD.Conectar().prepareStatement(sql);
            statement.setInt(1, jugador.getCantidad_partidos_jugados());
            
            int rowsUpdated = statement.executeUpdate();
             if (rowsUpdated > 0) {
                 System.out.println("partidos actualizados en bd");
             }
            
        }catch(SQLException ex){
            Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public boolean insertarCalificaciones(Calificaciones c,Jugador j){
        JugadorBD jugadorBD = new JugadorBD();
        PartidosBD pbd = new PartidosBD();
        PreparedStatement st; 
        boolean estado=false;
        try{
            st = ConexionBD.Conectar().prepareStatement(
                    "insert into calificaciones "
                  + "(Critica,PuntuacionJugador,Jugador_idJugador,Partido_idPartido)\n" +
                     "values (?,?,?,?)");
      
            st.setString(1, c.getCritica());
            st.setDouble(2, c.getPuntuacionJugador()); 
            st.setInt(3, jugadorBD.BuscarIndiceJug(j)); 
            st.setInt(4,pbd.BuscarIndicePartido());
           st.execute(); 
          estado=true;
            
        }catch(SQLException ex){
            Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
            estado= false;
        }
       return estado;
    }


    public double SumarCalificaciones(String jugador) {
         Statement query;
        ResultSet rs;
        double cuenta=0;
        try{
            query=ConexionBD.Conectar().createStatement();
            rs=query.executeQuery("select sum(PuntuacionJugador)"
                    + " from calificaciones as c, jugador as j, persona as p, partido as pt   "
                    + " where j.idJugador=c.Jugador_idJugador and p.id_Persona=j.Persona_id_Persona and pt.idPartido=c.Partido_idPartido"
                                + " and p.Nombre='"+jugador+"' and pt.idPartido=j.CantidadPartidosJugados");
            while (rs.next()) {                
                cuenta=rs.getDouble(1);
            }
            
        }catch(SQLException e){
            System.err.println("err"+e);
        }
         System.out.println(cuenta);
         return cuenta;

    }
    

    public int CalificacionJugador(String jugador) {
         Statement query;
        ResultSet rs;
        int cuenta=0;
        try{
            query=ConexionBD.Conectar().createStatement();
            rs=query.executeQuery("select count(PuntuacionJugador)  "
                    + "from calificaciones as c, jugador as j, persona as p, partido as pt \n" 
                    + "where j.idJugador=c.Jugador_idJugador and p.id_Persona=j.Persona_id_Persona and pt.idPartido=c.Partido_idPartido"
                    + " and p.Nombre='"+jugador+"' and pt.idPartido=j.CantidadPartidosJugados");
            while (rs.next()) {                
                cuenta=rs.getInt(1);
            }
            
        }catch(SQLException e){
            System.err.println("err"+e);
        }
         System.out.println(cuenta);
         return cuenta;

    }

    public void UpdatePromedios(double promedio,String nombre){
        try{
            String sql="update calificaciones as c, jugador as j , persona as p, partido as pt\n" +
                        "set c.PromedioPartido= ?  \n" +
                        " where j.idJugador=c.Jugador_idJugador and p.id_Persona=j.Persona_id_Persona and pt.idPartido=c.Partido_idPartido\n" +
                        " and p.Nombre='"+nombre+"' and pt.idPartido=j.CantidadPartidosJugados";
            
            PreparedStatement statement = ConexionBD.Conectar().prepareStatement(sql);
            statement.setDouble(1, promedio);
            
            int rowsUpdated = statement.executeUpdate();
             if (rowsUpdated > 0) {
                 System.out.println("promedios actualizados en bd");
             }
            
        }catch(SQLException ex){
            Logger.getLogger(JugadorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    public static void main(String[] args) {
      CalificacionBD cbd = new CalificacionBD();
      Jugador datosInicio = new Jugador();
      Calificaciones c = new Calificaciones();
      datosInicio.setNombre("yeimy");
      cbd.UpdatePromedios(5,"eduardo");
    }




}
