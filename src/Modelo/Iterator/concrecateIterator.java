/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Iterator;

import Modelo.Jugador;
import java.util.ArrayList;


/**
 *
 * @author eduar
 */
public class concrecateIterator implements Itf_iterator{
    private ListaJugadores lista;
    private int pos=0;

    public concrecateIterator(ListaJugadores lista) {
        this.lista = lista;
    }
    
    @Override
    public Object first() {
    Object obj = null;
    if (this.lista.ListaJugadores.isEmpty() == false) {
      this.pos = 0;
      obj = this.lista.ListaJugadores.get(0);
    }
    return obj;
    }

    @Override //modificar para que traiga lo que nesecito
    public Object[] next() {
    Object[] datos = new Object[7];
    if ((this.pos) <= this.lista.ListaJugadores.size()) {
     datos[0]= this.pos;
     datos[1]= this.lista.ListaJugadores.get(this.pos).getNombre();
     datos[2]= this.lista.ListaJugadores.get(this.pos).getModo_suscripcion();
     datos[3]=this.lista.ListaJugadores.get(this.pos).getForma_juego();
     datos[6]= this.lista.ListaJugadores.get(this.pos).isPenalizado();
     datos[5]= this.lista.ListaJugadores.get(this.pos).isAsistio();
     datos[4]= this.lista.ListaJugadores.get(this.pos).getPromedio_general();
      this.pos = this.pos + 1;
    }
    return datos;
    }
   
    
   
    @Override
    public ArrayList dameArray (ArrayList<Jugador>array){
    if((this.pos) < this.lista.ListaJugadores.size()){
        if(this.lista.ListaJugadores.get(this.pos).getModo_suscripcion().equals("frecuente")){
         array.add(this.lista.ListaJugadores.get(this.pos));
        
    }this.pos = this.pos + 1;
    }
    return array;
    }
    
    @Override
    public ArrayList dameArrayOcasional (ArrayList<Jugador>array){
    if((this.pos) < this.lista.ListaJugadores.size()){
        if(this.lista.ListaJugadores.get(this.pos).getModo_suscripcion().equals("ocasional")){
         array.add(this.lista.ListaJugadores.get(this.pos));
        
    }this.pos = this.pos + 1;
    }
    return array;
    }

    @Override
    public boolean isDone() {
     boolean ok = false;
    if (this.pos < this.lista.ListaJugadores.size())
      ok = true;
    return ok;
    }

    @Override
    public Object currentItem(int pos) {
      Object obj = null;
    if (pos < this.lista.ListaJugadores.size())
      obj = this.lista.ListaJugadores.get(pos);
    return obj;
    }
    
    public Object[] actualizarDatosJug(Jugador jug){
        Object[] datos = new Object[20];
        for (int i = 0; i < this.lista.ListaJugadores.size(); i++) {
            datos[0]=i;
            datos[1]= this.lista.ListaJugadores.get(i).getNombre();
            datos[2]= this.lista.ListaJugadores.get(i).getModo_suscripcion();
            datos[3]=this.lista.ListaJugadores.get(i).getForma_juego();
            datos[4]= this.lista.ListaJugadores.get(i).isPenalizado();
            datos[5]= this.lista.ListaJugadores.get(i).isAsistio();
            datos[6]= this.lista.ListaJugadores.get(i).getPromedio_general();
        
        }
        return datos;
       
    }
    
}
