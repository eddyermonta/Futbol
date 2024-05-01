/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author eduar
 */
public class Jugador extends Persona{
    private double promedio_general;
    private int cantidad_infracciones,cantidad_partidos_jugados;
    private String modo_suscripcion, forma_juego;
    private boolean asistio, penalizado;

    public Jugador() {
    }

    public Jugador(double promedio_general, int cantidad_infracciones,
            int cantidad_partidos_jugados, String modo_suscripcion,
            String forma_juego, boolean asistio, boolean penalizado,
            String nombre, String cedula, String rol, String correo, 
            String Usuario, String contrasena) {
        
        super(nombre, cedula, rol, correo, Usuario, contrasena);
        this.promedio_general = promedio_general;
        this.cantidad_infracciones = cantidad_infracciones;
        this.cantidad_partidos_jugados = cantidad_partidos_jugados;
        this.modo_suscripcion = modo_suscripcion;
        this.forma_juego = forma_juego;
        this.asistio = asistio;
        this.penalizado = penalizado;
    }

    public double getPromedio_general() {
        return promedio_general;
    }

    public void setPromedio_general(double promedio_general) {
        this.promedio_general = promedio_general;
    }

    public int getCantidad_infracciones() {
        return cantidad_infracciones;
    }

    public void setCantidad_infracciones(int cantidad_infracciones) {
        this.cantidad_infracciones = cantidad_infracciones;
    }

    public int getCantidad_partidos_jugados() {
        return cantidad_partidos_jugados;
    }

    public void setCantidad_partidos_jugados(int cantidad_partidos_jugados) {
        this.cantidad_partidos_jugados = cantidad_partidos_jugados;
    }

    public String getModo_suscripcion() {
        return modo_suscripcion;
    }

    public void setModo_suscripcion(String modo_suscripcion) {
        this.modo_suscripcion = modo_suscripcion;
    }

    public String getForma_juego() {
        return forma_juego;
    }

    public void setForma_juego(String forma_juego) {
        this.forma_juego = forma_juego;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public boolean isPenalizado() {
        return penalizado;
    }

    public void setPenalizado(boolean penalizado) {
        this.penalizado = penalizado;
    }

    @Override
    public String toString() {
        return "Jugador{" + "promedio_general=" + promedio_general + ", cantidad_infracciones=" + cantidad_infracciones + ", cantidad_partidos_jugados=" + cantidad_partidos_jugados + ", modo_suscripcion=" + modo_suscripcion + ", forma_juego=" + forma_juego + ", asistio=" + asistio + ", penalizado=" + penalizado + '}'
                +super.toString();
    }
    
    


    
    
    
    
}
