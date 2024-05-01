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
public abstract class Persona extends Identificacion {

    private String nombre;
    private String cedula;
    private String correo;

    public Persona(String nombre,String cedula,String rol,String correo,String Usuario,String contrasena) {
        super(Usuario, contrasena, rol);
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
    }

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", cedula=" + cedula + ", correo=" + correo + '}';
    }

}
