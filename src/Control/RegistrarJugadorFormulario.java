/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;


import BaseDatos.JugadorBD;
import Modelo.Identificacion;
import Modelo.Jugador;
import Vistas.Inicio_sesion;
import Vistas.Registrar_Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author eduar
 */
public class RegistrarJugadorFormulario implements ActionListener{
    
    Registrar_Jugador RegistrarJugadorVista;
    

    public RegistrarJugadorFormulario(Registrar_Jugador RegistrarJugadorVista) {
        this.RegistrarJugadorVista = RegistrarJugadorVista;
        
        this.RegistrarJugadorVista.jButtonGuardar.addActionListener(this);
        this.RegistrarJugadorVista.jComboBoxModo.addActionListener(this);
        this.RegistrarJugadorVista.jComboBoxForma.setEnabled(false);      
    }
    
    public Jugador LlenarFormularioJugador(Jugador jugadorModelo){
            jugadorModelo.setNombre(RegistrarJugadorVista.jTextFieldNombre.getText());
            jugadorModelo.setCorreo(RegistrarJugadorVista.jTextFieldCorreo.getText());
            jugadorModelo.setCedula(RegistrarJugadorVista.jTextFieldCedula.getText());
            jugadorModelo.setModo_suscripcion(String.valueOf(RegistrarJugadorVista.jComboBoxModo.getSelectedItem()));
            jugadorModelo.setPromedio_general(5.0);
            jugadorModelo.setCantidad_infracciones(0);
            jugadorModelo.setCantidad_partidos_jugados(0);
                
            if(jugadorModelo.getModo_suscripcion().equals("frecuente"))
                jugadorModelo.setForma_juego("estandar"); 
             else jugadorModelo.setForma_juego(String.valueOf(RegistrarJugadorVista.jComboBoxForma.getSelectedItem())); 
            
            jugadorModelo.setPenalizado(false);
            jugadorModelo.setAsistio(false);
            jugadorModelo.setRol("jugador");
            jugadorModelo.setUsuario(RegistrarJugadorVista.jTextFieldUsuario.getText());
            jugadorModelo.setContrasena(String.valueOf(RegistrarJugadorVista.jPasswordField.getPassword()));
            
            return jugadorModelo;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(RegistrarJugadorVista.jButtonGuardar == e.getSource()){
            
            Inicio_sesion inicio_sesionVista = new Inicio_sesion();
            JugadorBD jugadorBD = new JugadorBD();
            Jugador JugadorModelo = LlenarFormularioJugador(new Jugador());
            
            Identificacion identificacionModelo = new Identificacion();
            
            if(jugadorBD.contarJug()<=20){ //limitar a 20 jugadores
                
                if(((JugadorModelo.getModo_suscripcion().equals("frecuente") && jugadorBD.contarJugFrecuente()<=10)
                        || JugadorModelo.getModo_suscripcion().equals("ocasional")) && !jugadorBD.BuscarRepetidoBD(JugadorModelo.getNombre())  ){
                    
                    InicioSesionFormulario form = new InicioSesionFormulario(inicio_sesionVista,identificacionModelo);            
                    RegistrarJugadorVista.dispose();
                    jugadorBD.registrarJugador(JugadorModelo);
                
                }else if (!((JugadorModelo.getModo_suscripcion().equals("frecuente") && jugadorBD.contarJugFrecuente()<=10)))
                        JOptionPane.showMessageDialog(null, "Supera el limite de jugadores frecuentes que se pueden inscribir");  
                else if (jugadorBD.BuscarRepetidoBD(JugadorModelo.getNombre()))
                         JOptionPane.showMessageDialog(null, "el jugador ya se encuentra en base de datos");  
            }
        }
      
        if(RegistrarJugadorVista.jComboBoxModo==e.getSource()){
            if( RegistrarJugadorVista.jComboBoxModo.getSelectedItem().equals("frecuente"))
              RegistrarJugadorVista.jComboBoxForma.setEnabled(false);
            else RegistrarJugadorVista.jComboBoxForma.setEnabled(true);
        }
          
    }
    
}
