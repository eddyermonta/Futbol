/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Identificacion;
import Modelo.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vistas.Calificacion;
import Vistas.ListaJugadores;
/**
 *
 * @author eduar
 */
public class CalificacionFormulario implements ActionListener{
Calificacion calificacionVista;
ListaJugadores  ListaJugadoresVista;
Identificacion identificacionModelo;
Jugador jugadorModelo;
Object [] dato ;

    public CalificacionFormulario(Calificacion calificacionVista, ListaJugadores ListaJugadoresVista, Identificacion identificacionModelo, Jugador jugadorModelo) {
        this.calificacionVista = calificacionVista;
        this.ListaJugadoresVista = ListaJugadoresVista;
        this.identificacionModelo = identificacionModelo;
        this.jugadorModelo = jugadorModelo;
        dato = new Object[4];
        this.calificacionVista.jButtonTerminar.addActionListener(this);
    }
  
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.calificacionVista.jButtonTerminar==e.getSource()){
            ListaJugadoresFormulario ListaJugadoresFormulario = new ListaJugadoresFormulario(dato,ListaJugadoresVista, identificacionModelo, jugadorModelo);
            calificacionVista.dispose();
            ListaJugadoresVista.setVisible(true);
        }
    }
    
}
