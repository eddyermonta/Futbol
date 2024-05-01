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
public class Identificacion {
    private String Usuario;
    private String contrasena;
    private String rol;
    
    public Identificacion() {
    }

    public Identificacion(String Usuario, String contrasena, String rol) {
        this.Usuario = Usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    
    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Identificacion{" + "Usuario=" + Usuario + ", contrasena=" + contrasena + ", rol=" + rol + '}';
    }

    
    
   

    
}
