/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Identificacion;
import Modelo.Jugador;
import Vistas.ListaAutomatica;
import Vistas.ListaJugadores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author eduar
 */
public class ListaAutomaticaFormulario implements ActionListener{
    ListaAutomatica ListaAutoVista;
    ListaJugadores  ListaJugadoresVista;
    Identificacion identificacionModelo;
    Jugador jugadorModelo;
    Object [] dato ;
    public ListaAutomaticaFormulario(ListaAutomatica ListaAutoVista, ListaJugadores ListaJugadoresVista, Identificacion identificacionModelo, Jugador jugadorModelo) {
        this.ListaAutoVista = ListaAutoVista;
        this.ListaJugadoresVista = ListaJugadoresVista;
        this.identificacionModelo = identificacionModelo;
        this.jugadorModelo = jugadorModelo;
        this.ListaAutoVista.jButtonFormacion.addActionListener(this);
         dato = new Object[4];
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.ListaAutoVista.jButtonFormacion){
            
            ListaJugadoresFormulario ListaJugadoresFormulario = new ListaJugadoresFormulario(dato,ListaJugadoresVista, identificacionModelo, jugadorModelo);
            ListaAutoVista.dispose();
            ListaJugadoresVista.setVisible(true);
        }
    }
}
