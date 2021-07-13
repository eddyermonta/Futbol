/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;


import BaseDatos.IdentificacionBD;
import BaseDatos.JugadorBD;
import Modelo.Identificacion;
import Modelo.Jugador;
import Vistas.Inicio_sesion;
import Vistas.Registrar_Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author eduar
 */
public class RegistrarJugadorFormulario implements ActionListener{
    
    Registrar_Jugador RegistrarJugadorVista;
    Jugador JugadorModelo;

    public RegistrarJugadorFormulario(Registrar_Jugador RegistrarJugadorVista, Jugador JugadorModelo) {
        this.RegistrarJugadorVista = RegistrarJugadorVista;
        this.JugadorModelo=JugadorModelo;
        this.RegistrarJugadorVista.jButtonAtras.addActionListener(this);
        this.RegistrarJugadorVista.jButtonGuardar.addActionListener(this);
        this.RegistrarJugadorVista.jComboBoxModo.addActionListener(this);
        this.RegistrarJugadorVista.jComboBoxForma.setEnabled(false);      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(RegistrarJugadorVista.jButtonAtras == e.getSource()){
            
            Inicio_sesion inicio_sesionVista = new Inicio_sesion();
            Identificacion identificacionModelo = new Identificacion();
            IdentificacionBD identificacionDatos = new IdentificacionBD();
            
            InicioSesionFormulario form = new InicioSesionFormulario(inicio_sesionVista, identificacionModelo, identificacionDatos);
            inicio_sesionVista.setVisible(true);
            RegistrarJugadorVista.dispose();
            
        }
        if(RegistrarJugadorVista.jButtonGuardar == e.getSource()){
            Inicio_sesion inicio_sesionVista = new Inicio_sesion();
            Identificacion identificacionModelo = new Identificacion();
            IdentificacionBD identificacionDatos = new IdentificacionBD();
            JugadorBD jugadorBD = new JugadorBD();
            
            if(jugadorBD.contarJug()<=20){ //limitar a 20 jugadores
            
            JugadorModelo.setNombre(RegistrarJugadorVista.jTextFieldNombre.getText());
            JugadorModelo.setCorreo(RegistrarJugadorVista.jTextFieldCorreo.getText());
            JugadorModelo.setCedula(RegistrarJugadorVista.jTextFieldCedula.getText());
            JugadorModelo.setModo_suscripcion(String.valueOf(RegistrarJugadorVista.jComboBoxModo.getSelectedItem()));
            JugadorModelo.setPromedio_general(5.0);
            JugadorModelo.setCantidad_infracciones(0);
            JugadorModelo.setCantidad_partidos_jugados(0);
                
            if(JugadorModelo.getModo_suscripcion().equals("frecuente"))
                JugadorModelo.setForma_juego("estandar"); // configurado una de las formas
             else JugadorModelo.setForma_juego(String.valueOf(RegistrarJugadorVista.jComboBoxForma.getSelectedItem())); //si no elige la forma
            
            JugadorModelo.setPenalizado(false);
            JugadorModelo.setAsistio(false);
            JugadorModelo.setRol("jugador");
            JugadorModelo.setUsuario(RegistrarJugadorVista.jTextFieldUsuario.getText());
            JugadorModelo.setContrasena(String.valueOf(RegistrarJugadorVista.jPasswordField.getPassword()));
            
            if(JugadorModelo.getModo_suscripcion().equals("frecuente") && jugadorBD.contarJugFrecuente()<=10)
            jugadorBD.registrarJugador(JugadorModelo);
            else if(JugadorModelo.getModo_suscripcion().equals("ocasional"))
            jugadorBD.registrarJugador(JugadorModelo);    
                System.out.println("limite de jugadores");
            }
           
           
            InicioSesionFormulario form = new InicioSesionFormulario(inicio_sesionVista, identificacionModelo, identificacionDatos);
            inicio_sesionVista.setVisible(true);
            RegistrarJugadorVista.dispose();
        }
      if(RegistrarJugadorVista.jComboBoxModo==e.getSource()){
          if( RegistrarJugadorVista.jComboBoxModo.getSelectedItem().equals("frecuente"))
              RegistrarJugadorVista.jComboBoxForma.setEnabled(false);
            else
             RegistrarJugadorVista.jComboBoxForma.setEnabled(true);
      }
          
    }
    
}
