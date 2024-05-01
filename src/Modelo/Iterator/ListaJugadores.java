/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Iterator;

import BaseDatos.JugadorBD;
import Modelo.Jugador;
import java.util.ArrayList;

/**
 *
 * @author eduar
 */
public class ListaJugadores {
    
   protected ArrayList<Jugador>ListaJugadores = new ArrayList<>();

    public ListaJugadores() {
        addJugadores();
    }
    public Itf_iterator getIterator(){
        return new concrecateIterator(this);
    }
    
    public void addJugadores(){
        JugadorBD jugadorBD= new JugadorBD();
        ListaJugadores=jugadorBD.llenarjJugador();
        
    }
    
}
