/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Control.*;
import Vistas.*;
import BaseDatos.*;
/*****
 *
 * @author eduar
 */
public class Main {
    public static void main(String[] args) {
        Inicio_sesion ini = new Inicio_sesion();
        Identificacion iden = new Identificacion();
        IdentificacionBD idedatos = new IdentificacionBD();

        InicioSesionFormulario form = new InicioSesionFormulario(ini, iden);

        ini.setVisible(true);
        
       
    }
}
