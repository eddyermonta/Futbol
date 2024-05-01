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
public interface Itf_iterator {
    public Object first();
    public Object[] next();
    public boolean isDone();
    public Object currentItem(int pos);
    public ArrayList dameArray (ArrayList<Jugador>array);
    public ArrayList dameArrayOcasional (ArrayList<Jugador>array);
}
