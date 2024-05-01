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
public class Calificaciones extends Jugador{
   private String critica;
   private double puntuacionJugador;
   private double promedioPartido;

    public Calificaciones(String critica, double puntuacionJugador, double promedioPartido, double promedio_general, int cantidad_infracciones, int cantidad_partidos_jugados, String modo_suscripcion, String forma_juego, boolean asistio, boolean penalizado, String nombre, String cedula, String rol, String correo, String Usuario, String contrasena) {
        super(promedio_general, cantidad_infracciones, cantidad_partidos_jugados, modo_suscripcion, forma_juego, asistio, penalizado, nombre, cedula, rol, correo, Usuario, contrasena);
        this.critica = critica;
        this.puntuacionJugador = puntuacionJugador;
        this.promedioPartido = promedioPartido;
    }

    public Calificaciones() {
    }
    

    public Calificaciones(String critica, double puntuacionJugador, double promedioPartido) {
        this.critica = critica;
        this.puntuacionJugador = puntuacionJugador;
        this.promedioPartido = promedioPartido;
    }

    public String getCritica() {
        return critica;
    }

    public void setCritica(String critica) {
        this.critica = critica;
    }

    public double getPuntuacionJugador() {
        return puntuacionJugador;
    }

    public void setPuntuacionJugador(double puntuacionJugador) {
        this.puntuacionJugador = puntuacionJugador;
    }

    public double getPromedioPartido() {
        return promedioPartido;
    }

    public void setPromedioPartido(double promedioPartido) {
        this.promedioPartido = promedioPartido;
    }
    
    
}
