/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import BaseDatos.CalificacionBD;
import BaseDatos.JugadorBD;
import BaseDatos.PartidosBD;
import Control.Jtablemodelo.Celda;
import Control.Jtablemodelo.Columnas;
import Modelo.Calificaciones;
import Modelo.Identificacion;
import Modelo.Iterator.Itf_iterator;
import Modelo.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vistas.Calificacion;
import Vistas.ListaJugadores;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author eduar
 */
public class CalificacionFormulario implements ActionListener{
Calificacion calificacionVista;
ListaJugadores  ListaJugadoresVista;
ArrayList<Jugador> ListaJugadores;
DefaultTableModel tb1,tb2, tbcalificaciones;
Jugador jugadormodelo;
double promedio;

    public CalificacionFormulario(Jugador jugadorModelo,DefaultTableModel tb1, DefaultTableModel tb2,Itf_iterator iteracion,
            Calificacion calificacionVista, ListaJugadores ListaJugadoresVista) {
        
        this.calificacionVista = calificacionVista;
        this.ListaJugadoresVista = ListaJugadoresVista;
        this.calificacionVista.jButtonTerminar.addActionListener(this);
        this.jugadormodelo=jugadorModelo;
       
       
        
        this.tb1=tb1;
        this.tb2=tb2;
        
        ListaJugadores= new ArrayList<>();
        tbcalificaciones = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==2 || column==3;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 ) return Integer.class;
                if (columnIndex == 1 || columnIndex == 2)  return String.class;
                if (columnIndex == 3)  return Double.class;
                return super.getColumnClass(columnIndex);
            }
  
        };
        
        CargarColumnas();
        
        CargarTablaDatos(this.tb1);
        CargarTablaDatos(this.tb2);
        
        cuerpoTablaOriginal(this.calificacionVista.jTableJugadores);
        
        ListaJugadoresFormulario listaJugadoresFormulario = new ListaJugadoresFormulario();
        listaJugadoresFormulario.EncabezadoTabla(this.calificacionVista.jTableJugadores);
         
        
        
    }

    
    
    
    public void CargarColumnas(){
        tbcalificaciones.addColumn("posicion");
        tbcalificaciones.addColumn("nombre");
        tbcalificaciones.addColumn("descripcion");
        tbcalificaciones.addColumn("calificacion");
        this.calificacionVista.jTableJugadores.setModel(tbcalificaciones);
    }
    
    public void CargarTablaDatos(DefaultTableModel tb1){
        for (int i = 0; i < tb1.getRowCount(); i++) {
           if(tb1.getValueAt(i, 0)!=null)
            tbcalificaciones.addRow(llenarDatos(tb1,i));
        }
    }
    
    public void cuerpoTablaOriginal(JTable tabla ){
        tabla.getColumnModel().getColumn(Columnas.POSICION).setCellRenderer(new Celda("numerico"));
        tabla.getColumnModel().getColumn(Columnas.NOMBRE).setCellRenderer(new Celda("texto"));
        tabla.getColumnModel().getColumn(2).setCellRenderer(new Celda("texto"));
        tabla.getColumnModel().getColumn(3).setCellRenderer(new Celda("numerico2"));
        }
    
    
    
    public Object[] llenarDatos(DefaultTableModel tb1, int i){
        Object[] datos = new Object[4];
        if(tb1.getValueAt(i, 0)!=null){
             datos[0]=tb1.getValueAt(i, 0);
             datos[1]=tb1.getValueAt(i, 1);
             datos[2]=null;
             datos[3]=0.0;
        }
            
        
     return datos;  
    }
    
    public boolean validarCalificaciones (DefaultTableModel tabla){
      boolean  estado=false;
      int contar=0;
      for (int i = 0; i < tabla.getRowCount(); i++) {
                 if(tabla.getValueAt(i, 3)!=null &&(double)tabla.getValueAt(i, 3)<=10 && (double)tabla.getValueAt(i, 3)>0.0 )
                     contar++;
                 else if(!((double)tabla.getValueAt(i, 3)<=10 && (double)tabla.getValueAt(i, 3)>0))
                     estado= false;
            }
      if(contar==tabla.getRowCount())
          estado=true;
      return estado;
    }
    
    
  
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.calificacionVista.jButtonTerminar==e.getSource()){
            JugadorBD jugadorBD = new JugadorBD();
            
           if(validarCalificaciones(tbcalificaciones)){
                for (int i = 0; i < tbcalificaciones.getRowCount(); i++) {
                    
                  Jugador jugador = new Jugador(); 
                  Calificaciones calificaciones = new Calificaciones();
                  CalificacionBD cbd = new CalificacionBD();
                  PartidosBD pbd = new PartidosBD();
                  
                  
                  jugador.setNombre(tbcalificaciones.getValueAt(i, 1).toString());
                  jugador=jugadorBD.LlenarJugador(jugador);
                  
                  calificaciones.setPuntuacionJugador((double)tbcalificaciones.getValueAt(i, 3));
                  
                  calificaciones.setCritica(String.valueOf(tbcalificaciones.getValueAt(i, 2)));
                  if(calificaciones.getCritica().equals("null"))
                      calificaciones.setCritica("");
                  
                  cbd.insertarCalificaciones(calificaciones, jugador);
                    
                  jugador.setCantidad_partidos_jugados(pbd.BuscarIndicePartido());
                  cbd.UpdatePartidos(jugador);                  
                  }  
               
               }
                
                ListaJugadoresFormulario ListaJugadoresFormulario = new ListaJugadoresFormulario(tb1,tb2);
    
             }else JOptionPane.showMessageDialog(null,"calificaciones no validas");
           
          
            calificacionVista.dispose();
        }
    }
    
