/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Iterator;

/**
 *
 * @author eduar
 */
public class pruebaIterator {
   
    public static void main(String[] args) {
    ListaJugadores sergio = new ListaJugadores();
    // Crea el objeto iterador para recorrer la lista
    Itf_iterator iteracion = sergio.getIterator();
     
    System.out.println("primerara iteracion "+iteracion.first());
    // muestra todos
    while (iteracion.isDone()) {
      System.out.println(iteracion.next());
    }
       
    }
    
   
}
