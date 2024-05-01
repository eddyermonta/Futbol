/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.Jtablemodelo.Celda;
import Control.Jtablemodelo.Columnas;
import Modelo.Identificacion;
import Modelo.Iterator.Itf_iterator;
import Modelo.Jugador;
import Vistas.Agregar_Ocasional;
import Vistas.ListaJugadores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author eduar
 */
public class AgregarOcasionalFormulario implements ActionListener{
    
   
    ArrayList<Jugador> jugadoresOcasionales;
    
    
    Agregar_Ocasional ocasionalVista;
    ListaJugadores ListaJugadoresVista ;
    Jugador jugadorModelo;
    int indice;
    DefaultTableModel tb1, tb2,tablaocasional;

    public AgregarOcasionalFormulario(DefaultTableModel tb1,DefaultTableModel tb2,Itf_iterator iteracion, 
            Agregar_Ocasional ocasionalVista,ListaJugadores ListaJugadoresVista) {
        
        this.ocasionalVista = ocasionalVista;
        this.ListaJugadoresVista=ListaJugadoresVista;
        
        this.tb1=tb1;
        this.tb2=tb2;
        
        tablaocasional = new DefaultTableModel();
        jugadoresOcasionales = new ArrayList<>();
        
        ListaJugadoresFormulario listaJugadoresFormulario = new ListaJugadoresFormulario();
        listaJugadoresFormulario.EncabezadoTabla(this.ocasionalVista.jTableJugadores);
        cuerpoTablaOriginal(this.ocasionalVista.jTableJugadores, jugadorModelo);
        if(jugadoresOcasionales.isEmpty())while (iteracion.isDone()) 
             iteracion.dameArrayOcasional(jugadoresOcasionales);
           iteracion.first();
        
        jugadoresOcasionales.stream().forEach((jugadoresOcasionale) -> {
            this.ocasionalVista.jComboBoxJugadores.addItem(jugadoresOcasionale.getNombre());});
        this.ocasionalVista.jCheckBoxEstandar.setSelected(true);
        
        indice=ocasionalVista.jComboBoxJugadores.getSelectedIndex();
        
        ActualizaForma();
        columnasTable();
         
        this.ocasionalVista.jComboBoxJugadores.addActionListener(this);
        this.ocasionalVista.jCheckBoxEstandar.addActionListener(this);
        this.ocasionalVista.jCheckBoxOcasional.addActionListener(this);
        this.ocasionalVista.jButtonAgregar.addActionListener(this);     
    }

   public void columnasTable(){
       tablaocasional.addColumn("posicion");//numero
        tablaocasional.addColumn("nombre");
        tablaocasional.addColumn("suscripcion");//si
        tablaocasional.addColumn("forma");
        tablaocasional.addColumn("calificacion");
        this.ocasionalVista.jTableJugadores.setModel(tablaocasional);  
        tablaocasional.addRow(llenarDatos(tb1,tb2));
   }
           
   
    public int contarTabla(DefaultTableModel tabla){
        int contar=0;
        for (int i = 0; i <tabla.getRowCount(); i++) {
            if(tabla.getValueAt(i, 0)==null)
                return contar;
            else contar ++;
        }
        return contar;
    }

    
    public Object[] llenarDatos(DefaultTableModel tb1, DefaultTableModel tb2){
        Object[] datos = new Object[5];
        
            if(contarTabla(tb1)<5){
                datos[0]= contarTabla(tb1);
                datos[1]= ocasionalVista.jComboBoxJugadores.getSelectedItem().toString() ;
                datos[2]= traeJugador().getModo_suscripcion();
                datos[3]=traeJugador().getForma_juego().toLowerCase();
                datos[4]=traeJugador().getPromedio_general();
             
         }else if(contarTabla(tb2)<5)
             datos[0]= contarTabla(tb2)+5;
             datos[1]= ocasionalVista.jComboBoxJugadores.getSelectedItem().toString() ;
             datos[2]= traeJugador().getModo_suscripcion();
             datos[3]=traeJugador().getForma_juego().toLowerCase();
             datos[4]=traeJugador().getPromedio_general();
             
        // }
     return datos;
    }
  
    public Jugador traeJugador(){
        return jugadoresOcasionales.get(indice);
    }
    
     public void cuerpoTablaOriginal(JTable tabla, Jugador jugador){
        //tabla.getColumnModel().getColumn(4).setCellRenderer(new Celda("double",jugador));
        tabla.getColumnModel().getColumn(Columnas.FORMA).setCellRenderer(new Celda("texto",jugador));
        tabla.getColumnModel().getColumn(Columnas.NOMBRE).setCellRenderer(new Celda("texto",jugador));
        tabla.getColumnModel().getColumn(Columnas.POSICION).setCellRenderer(new Celda("numerico",jugador));
        tabla.getColumnModel().getColumn(Columnas.SUSCRIPCION).setCellRenderer(new Celda("texto",jugador));
        }
    
    
    public void ActualizaForma(){
        if(ocasionalVista.jCheckBoxEstandar.isSelected()){
            jugadorModelo = new Jugador(traeJugador().getPromedio_general(), traeJugador().getCantidad_infracciones(),
                    traeJugador().getCantidad_partidos_jugados(),traeJugador().getModo_suscripcion() , ocasionalVista.jCheckBoxEstandar.getText(),
                    traeJugador().isAsistio(), traeJugador().isPenalizado(),
                    traeJugador().getNombre(), traeJugador().getCedula(), traeJugador().getRol(), traeJugador().getCorreo(),
                    traeJugador().getUsuario(), traeJugador().getContrasena());
        }else if(ocasionalVista.jCheckBoxOcasional.isSelected()){
            jugadorModelo = new Jugador(traeJugador().getPromedio_general(), traeJugador().getCantidad_infracciones(),
                    traeJugador().getCantidad_partidos_jugados(),traeJugador().getModo_suscripcion() , ocasionalVista.jCheckBoxOcasional.getText(),
                    traeJugador().isAsistio(), traeJugador().isPenalizado(),
                    traeJugador().getNombre(), traeJugador().getCedula(), traeJugador().getRol(), traeJugador().getCorreo(),
                    traeJugador().getUsuario(), traeJugador().getContrasena());
            
        }jugadoresOcasionales.set(indice, jugadorModelo);
    }

    public void ActualizarTabla(){
        ActualizaForma();
             for (int i = 0; i < tablaocasional.getColumnCount(); i++) {
                for (int j = 0; j < tablaocasional.getRowCount(); j++) {
                    if(tablaocasional.getValueAt(j, i)!=null)
                    tablaocasional.setValueAt(llenarDatos(tb1,tb2)[i],j,i);
                }
            }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ocasionalVista.jButtonAgregar){
            ActualizaForma();
            if(ocasionalVista.jCheckBoxEstandar.isSelected() || ocasionalVista.jCheckBoxOcasional.isSelected()){
               
                ListaJugadoresFormulario ListaJugadoresFormulario = new ListaJugadoresFormulario(tb1, tb2, llenarDatos(tb1,tb2));
                ocasionalVista.dispose();
                ListaJugadoresVista.setVisible(true);
            }else System.out.println("no se puede agregar");
           
        
        }
        if(e.getSource() == ocasionalVista.jCheckBoxEstandar){
            ocasionalVista.jCheckBoxOcasional.setSelected(false);
            ActualizaForma();
             ActualizarTabla();
        }
            
        if(e.getSource() == ocasionalVista.jCheckBoxOcasional){
            ocasionalVista.jCheckBoxEstandar.setSelected(false);
            ActualizaForma();
            ActualizarTabla();
            
        }
        if(e.getSource()==ocasionalVista.jComboBoxJugadores){
            indice = ocasionalVista.jComboBoxJugadores.getSelectedIndex();
            ActualizaForma();
            ActualizarTabla();
        } 
        
    }
    
}
