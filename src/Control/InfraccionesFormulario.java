/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import BaseDatos.InfraccionesBD;
import BaseDatos.JugadorBD;
import Control.Jtablemodelo.Columnas;
import Modelo.Identificacion;
import Modelo.Jugador;
import Vistas.Infracciones;
import Vistas.ListaJugadores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * 
 * 
 * 
 * @author eduar
 */
public class InfraccionesFormulario implements ActionListener{
    Infracciones infraccionesVista;
    ListaJugadores  ListaJugadoresVista;
    DefaultTableModel tb1,tb2;
    Jugador jugadorModelo;
    public InfraccionesFormulario(Jugador jugadorModelo, DefaultTableModel tb1, DefaultTableModel tb2, Infracciones infraccionesVista, ListaJugadores ListaJugadoresVista) {
        this.infraccionesVista = infraccionesVista;
        this.ListaJugadoresVista = ListaJugadoresVista;
        this.tb1=tb1;
        this.tb2=tb2;
        this.jugadorModelo=jugadorModelo;
        this.infraccionesVista.jButtonAplicarInfraccion.addActionListener(this);
        this.infraccionesVista.jButtonEliminarInfraccion.addActionListener(this);
        this.infraccionesVista.jComboBoxJugador.addActionListener(this);
        
        CargarNombres();
        
        this.jugadorModelo=Jugador(jugadorModelo);
        
    }

    public InfraccionesFormulario() {
    }
    
    public Jugador Jugador(Jugador jugadorModelo){
        JugadorBD jugadorBD = new JugadorBD();
         jugadorModelo.setNombre(String.valueOf(this.infraccionesVista.jComboBoxJugador.getSelectedItem()));
         return jugadorBD.LlenarJugador(jugadorModelo);
    }
    
    public void GenerarPenalizacion(Jugador jugadorModelo, Object[] datos,DefaultTableModel tabla,DefaultTableModel tablaequipo,int indice){
       // if(jugadorModelo.getCantidad_partidos_jugados()<=10){
           ListaJugadoresFormulario listaJugadoresFormulario = new ListaJugadoresFormulario();
           System.out.println(Arrays.toString(datos));
            if(jugadorModelo.getCantidad_infracciones()==2){
                 datos[6]=true;
                datos[3]="solidaria";
                jugadorModelo.setPenalizado(true);
                jugadorModelo.setForma_juego("solidaria");
                listaJugadoresFormulario.reemplazarSolidario(datos, tablaequipo, indice);
                InfraccionesBD infraccionesBD = new InfraccionesBD();
                infraccionesBD.updatePenalizacion(jugadorModelo);
                for (int i = 0; i < tabla.getRowCount(); i++) {
                        if(tabla.getValueAt(i, Columnas.NOMBRE).equals(datos[1]))
                            listaJugadoresFormulario.reemplazarSolidario(datos, tabla, i);
                }         
                } 
     //   }
    }
    public void GenerarPenalizacion(Jugador jugadorModelo, int row,DefaultTableModel tabla){
       // if(jugadorModelo.getCantidad_partidos_jugados()<=10){
           ListaJugadoresFormulario listaJugadoresFormulario = new ListaJugadoresFormulario();
            if(jugadorModelo.getCantidad_infracciones()==2){
                jugadorModelo.setPenalizado(true);
                jugadorModelo.setForma_juego("solidaria");
                
                InfraccionesBD infraccionesBD = new InfraccionesBD();
                infraccionesBD.updatePenalizacion(jugadorModelo);   
                } 
     //   }
    }


    public void CargarNombres(){
         for (int i = 0; i < tb1.getRowCount(); i++) {
            if(tb1.getValueAt(i, Columnas.NOMBRE)!=null || tb2.getValueAt(i, Columnas.NOMBRE)!=null ){
                this.infraccionesVista.jComboBoxJugador.addItem(String.valueOf(tb1.getValueAt(i, Columnas.NOMBRE)));
                System.out.println(tb1.getValueAt(i, Columnas.NOMBRE));
                
            } if(tb2.getValueAt(i, Columnas.NOMBRE)!=null){
                this.infraccionesVista.jComboBoxJugador.addItem(String.valueOf(tb2.getValueAt(i, Columnas.NOMBRE)));
                System.out.println(tb2.getValueAt(i, Columnas.NOMBRE));
            }   
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        int Tipoinfraccion =this.infraccionesVista.jComboBoxTipoInfraccion.getSelectedIndex();
        InfraccionesBD infraccionesBD = new InfraccionesBD();
        
        if(e.getSource()==infraccionesVista.jButtonAplicarInfraccion){
           
           if(Tipoinfraccion>-1 && Tipoinfraccion<3){
               jugadorModelo.setCantidad_infracciones(jugadorModelo.getCantidad_infracciones()+1);
               
               if (Tipoinfraccion==0)infraccionesBD.Updateinfraccion(jugadorModelo, Tipoinfraccion);
               else infraccionesBD.Updateinfraccion(jugadorModelo);
               
               if(jugadorModelo.getCantidad_infracciones()>=2){
                   jugadorModelo.setPenalizado(true);
                   infraccionesBD.updatePenalizacion(jugadorModelo);
               }               
           }

            
        }else if(e.getSource()==infraccionesVista.jButtonEliminarInfraccion){
           
          
            
            if(Tipoinfraccion>-1 && Tipoinfraccion<3){
               jugadorModelo.setCantidad_infracciones(jugadorModelo.getCantidad_infracciones()-1);
               
               infraccionesBD.Updateinfraccion(jugadorModelo);
               if(jugadorModelo.getCantidad_infracciones()<2){
                   jugadorModelo.setPenalizado(false);
                   infraccionesBD.updatePenalizacion(jugadorModelo);
               }
                   
            }
  
        }else if (e.getSource()==infraccionesVista.jComboBoxJugador)
            this.jugadorModelo=Jugador(jugadorModelo);
         
        
        
    }
}
