/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Vistas.*;
import Modelo.*;
import BaseDatos.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author eduar
 */
public class InicioSesionFormulario implements ActionListener {

    Inicio_sesion inicio_sesionVista;
    Identificacion identificacionModelo; 
    
    public InicioSesionFormulario(Inicio_sesion inicio_sesionVista, Identificacion identificacionModelo) {
        this.inicio_sesionVista = inicio_sesionVista;
        this.identificacionModelo = identificacionModelo;
        this.inicio_sesionVista.jButtonInicio.addActionListener(this);
        this.inicio_sesionVista.jButtonNuevoUsuario.addActionListener(this);
       
    }   

    public void lectura() {
        identificacionModelo.setUsuario(inicio_sesionVista.jTextFieldUsuario.getText());
        identificacionModelo.setContrasena(String.valueOf(inicio_sesionVista.jPasswordField1.getPassword()));
        identificacionModelo.setRol(String.valueOf(inicio_sesionVista.jComboTipoUsuario.getSelectedItem()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        lectura();
        IdentificacionBD identificacionDatos = new IdentificacionBD();
      
        if (e.getSource() == inicio_sesionVista.jButtonInicio) {

            if (identificacionDatos.validarUsuario(identificacionDatos.BuscarUsuario(identificacionModelo.getUsuario()), identificacionModelo)) { 
            //valida y compara datos ingresados con la base de datos
                Vistas.ListaJugadores listaJugadoresVista = new Vistas.ListaJugadores();
                JugadorBD jugadorbd= new JugadorBD();
                ListaJugadoresFormulario form = new ListaJugadoresFormulario(listaJugadoresVista,identificacionModelo,
                        jugadorbd.LlenarJugador(identificacionModelo));
                listaJugadoresVista.setVisible(true);
                
            }
        }
        if (e.getSource() == inicio_sesionVista.jButtonNuevoUsuario) {
            if ("jugador".equals(identificacionModelo.getRol())) {
               
                Registrar_Jugador RegistrarJugadorVista = new Registrar_Jugador();
                RegistrarJugadorFormulario formu = new RegistrarJugadorFormulario(RegistrarJugadorVista);
                RegistrarJugadorVista.setVisible(true);
               
            } else if ("admin".equals(identificacionModelo.getRol())) {
                if (!identificacionDatos.ExisteAdminBD(identificacionModelo)) {

                    Vistas.Administrador personaVista = new Vistas.Administrador();
                    AdministradorFormulario formu = new AdministradorFormulario(personaVista);
                    personaVista.setVisible(true);
                    
                    
                }

            }

        }

    }

}
