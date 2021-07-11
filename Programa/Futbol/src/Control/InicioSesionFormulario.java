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

    Inicio_sesion inicio_sesionVista; //vista
    Identificacion identificacionModelo; //modelo
    Jugador jugadorModelo;
    IdentificacionBD identificacionDatos;//baseDatos
    Object [] dato ;
    

    public InicioSesionFormulario(Inicio_sesion inicio_sesionVista, Identificacion identificacionModelo, IdentificacionBD identificacionDatos) {
        this.inicio_sesionVista = inicio_sesionVista;
        this.identificacionModelo = identificacionModelo;
        this.identificacionDatos = identificacionDatos;
        dato = new Object[4];
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
        if (e.getSource() == inicio_sesionVista.jButtonInicio) {

            if (identificacionDatos.validarUsuario(identificacionDatos.BuscarUsuario(identificacionModelo.getUsuario()), identificacionModelo)) { //valida y compara datos ingresados con la base de datos
                 Vistas.ListaJugadores listaJugadoresVista = new Vistas.ListaJugadores();
                 JugadorBD jugadorbd= new JugadorBD();
                 jugadorModelo=jugadorbd.LlenarJugador(identificacionModelo);
                ListaJugadoresFormulario form = new ListaJugadoresFormulario(dato,listaJugadoresVista,identificacionModelo,jugadorModelo);
                inicio_sesionVista.dispose();
                listaJugadoresVista.setVisible(true);
            }
        }
        if (e.getSource() == inicio_sesionVista.jButtonNuevoUsuario) {

            if ("jugador".equals(identificacionModelo.getRol())) {
                Registrar_Jugador RegistrarJugadorVista = new Registrar_Jugador();
                Jugador JugadorModelo = new Jugador();
                RegistrarJugadorFormulario formu = new RegistrarJugadorFormulario(RegistrarJugadorVista,JugadorModelo);
                RegistrarJugadorVista.setVisible(true);
                inicio_sesionVista.dispose();

            } else if ("admin".equals(identificacionModelo.getRol())) {
                if (identificacionDatos.ExisteAdminBD(identificacionModelo)) {
                    Vistas.Administrador personaVista = new Vistas.Administrador();
                    AdministradorBD admiBD = new AdministradorBD();
                    AdministradorFormulario formu = new AdministradorFormulario(personaVista,admiBD);
                    personaVista.setVisible(true);
                    inicio_sesionVista.dispose();
                }

            }

        }

    }
}
