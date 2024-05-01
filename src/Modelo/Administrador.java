/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author eduar
 */
public class Administrador extends Persona {

    private static  Administrador admin;

    private Administrador(
            String nombre,
            String cedula,
            String rol,
            String correo,
            String Usuario,
            String contrasena) {
        super(nombre, cedula, rol, correo, Usuario, contrasena);
    }

    public static Administrador getSingletonInstance(
            String nombre,
            String cedula,
            String rol,
            String correo,
            String Usuario,
            String contrasena) {
        
        if (admin == null) {
            admin = new Administrador(
                    nombre,
                    cedula,
                    rol,
                    correo,
                    Usuario,
                    contrasena);
            
        }
        return admin;
        
    }

}
