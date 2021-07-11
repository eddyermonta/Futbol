/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import BaseDatos.AdministradorBD;
import BaseDatos.IdentificacionBD;
import Modelo.Identificacion;
import Vistas.Administrador;
import Vistas.Inicio_sesion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author eduar
 */
public class AdministradorFormulario implements ActionListener {

    Vistas.Administrador personaVista;//vista
    AdministradorBD adminBD;
    Modelo.Administrador admiModelo;
  
    public AdministradorFormulario(Administrador personaVista,AdministradorBD adminBD) {
        this.personaVista = personaVista;
        this.adminBD=adminBD;
        this.personaVista.jButtonAtras.addActionListener(this);
        this.personaVista.jButtonGuardar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == personaVista.jButtonAtras) {
            Inicio_sesion inicio_sesionVista = new Inicio_sesion();
            Identificacion identificacionModelo = new Identificacion();
            IdentificacionBD identificacionDatos = new IdentificacionBD();
            
            InicioSesionFormulario form = new InicioSesionFormulario
        (inicio_sesionVista, identificacionModelo, identificacionDatos);
            
            inicio_sesionVista.setVisible(true);
            personaVista.dispose();
        }
        if (e.getSource() == personaVista.jButtonGuardar) {
            
            Inicio_sesion inicio_sesionVista = new Inicio_sesion();
            Identificacion identificacionModelo = new Identificacion();
            IdentificacionBD identificacionDatos = new IdentificacionBD();
            
            identificacionModelo.setRol("admin");
            if(identificacionDatos.ExisteAdminBD(identificacionModelo)){
              admiModelo=  Modelo.Administrador.getSingletonInstance(
                    personaVista.jTextFieldNombre.getText(),
                    personaVista.jTextFieldCedula.getText(),
                    identificacionModelo.getRol(),
                    personaVista.jTextFieldCorreo.getText(),
                    personaVista.jTextFieldUsuario.getText(),
                    String.valueOf(personaVista.jPasswordField.getPassword()));
           
            adminBD.registrarAdministrador(admiModelo);
            }
     
            InicioSesionFormulario form = new InicioSesionFormulario(inicio_sesionVista, identificacionModelo, identificacionDatos);
            inicio_sesionVista.setVisible(true);
            personaVista.dispose();
        }
    }

}
