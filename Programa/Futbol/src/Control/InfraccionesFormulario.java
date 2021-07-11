/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Identificacion;
import Modelo.Jugador;
import Vistas.Infracciones;
import Vistas.ListaJugadores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author eduar
 */
public class InfraccionesFormulario implements ActionListener{
    Infracciones infraccionesVista;
    ListaJugadores  ListaJugadoresVista;
    Identificacion identificacionModelo;
    Jugador jugadorModelo;
    Object [] dato ;
    
    public InfraccionesFormulario(Infracciones infraccionesVista, ListaJugadores ListaJugadoresVista, Identificacion identificacionModelo, Jugador jugadorModelo) {
        this.infraccionesVista = infraccionesVista;
        this.ListaJugadoresVista = ListaJugadoresVista;
        this.identificacionModelo = identificacionModelo;
        this.jugadorModelo = jugadorModelo;
        dato = new Object[4];
        this.infraccionesVista.jButtonAplicarInfraccion.addActionListener(this);
        this.infraccionesVista.jButtonEliminarInfraccion.addActionListener(this);
    }
 
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==infraccionesVista.jButtonAplicarInfraccion){
           
            ListaJugadoresFormulario ListaJugadoresFormulario = new ListaJugadoresFormulario(dato,ListaJugadoresVista, identificacionModelo, jugadorModelo);
            infraccionesVista.dispose();
            ListaJugadoresVista.setVisible(true);
            
        }else if(e.getSource()==infraccionesVista.jButtonEliminarInfraccion){
            
        }
    }
}
