/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Identificacion;
import Modelo.Iterator.Itf_iterator;
import Modelo.Jugador;
import Vistas.Agregar_Ocasional;
import Vistas.ListaAutomatica;
import Vistas.ListaJugadores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author eduar
 */
public class AgregarOcasionalFormulario implements ActionListener{
    
   
    ArrayList<Jugador> jugadoresOcasionales;
    Modelo.Iterator.ListaJugadores listaJugadores;
    
    Agregar_Ocasional ocasionalVista;
    ListaJugadores  ListaJugadoresVista;
    Identificacion identificacionModelo;
    Jugador jugadorModelo;
    int indice;
    DefaultTableModel tb1, tb2,tablaocasional;

    public AgregarOcasionalFormulario(DefaultTableModel tb1,DefaultTableModel tb2,ListaJugadores ListaJugadoresVista,Modelo.Iterator.ListaJugadores listaJugadores,Identificacion identificacionModelo, Jugador jugadorModelo,Agregar_Ocasional ocasionalVista) {
        
        this.ocasionalVista = ocasionalVista;
        this.identificacionModelo = identificacionModelo;
        this.jugadorModelo = jugadorModelo;
        this.ListaJugadoresVista=ListaJugadoresVista;
        this.tb1=tb1;
        this.tb2=tb2;
        
        Itf_iterator iteracion = listaJugadores.getIterator();
        jugadoresOcasionales = new ArrayList<>();
        
         if(jugadoresOcasionales.isEmpty())
            while (iteracion.isDone())  iteracion.dameArrayOcasional(jugadoresOcasionales);
           iteracion.first();
        
        jugadoresOcasionales.stream().forEach((jugadoresOcasionale) -> {
            this.ocasionalVista.jComboBoxJugadores.addItem(jugadoresOcasionale.getNombre());  
        });
        
        indice=ocasionalVista.jComboBoxJugadores.getSelectedIndex();
        this.ocasionalVista.jCheckBoxEstandar.setSelected(true);
        
         tablaocasional = new DefaultTableModel();
        
         ActualizaForma();
         
         System.out.println(jugadorModelo.toString());
         
        tablaocasional.addColumn("posicion");//numero
        tablaocasional.addColumn("nombre");
        tablaocasional.addColumn("suscripcion");//si
        tablaocasional.addColumn("forma");
        tablaocasional.addColumn("calificacion");
        this.ocasionalVista.jTableJugadores.setModel(tablaocasional);  
        
        tablaocasional.addRow(llenarDatos(tb1,tb2));
        
       
        this.ocasionalVista.jComboBoxJugadores.addActionListener(this);
        this.ocasionalVista.jCheckBoxEstandar.addActionListener(this);
        this.ocasionalVista.jCheckBoxOcasional.addActionListener(this);
        this.ocasionalVista.jButtonAgregar.addActionListener(this);
        
       
    }

    
    public Object[] llenarDatos(DefaultTableModel tb1, DefaultTableModel tb2){
         Object[] datos = new Object[5];
         if((tb1.getRowCount()<5)){
             datos[0]= tb1.getRowCount()+1;
             datos[1]= ocasionalVista.jComboBoxJugadores.getSelectedItem().toString() ;
             datos[2]= jugadoresOcasionales.get(indice).getModo_suscripcion();
             datos[3]=jugadoresOcasionales.get(indice).getForma_juego().toLowerCase();
             datos[4]=traeJugador().getPromedio_general();
             
         }else if((tb2.getRowCount()<5)){
             datos[0]= tb2.getRowCount()+tb1.getRowCount();
             datos[1]= ocasionalVista.jComboBoxJugadores.getSelectedItem().toString() ;
             datos[2]= jugadoresOcasionales.get(indice).getModo_suscripcion();
             datos[3]=jugadoresOcasionales.get(indice).getForma_juego().toLowerCase();
             datos[4]=traeJugador().getPromedio_general();
         }
     return datos;
    }
  
    public Jugador traeJugador(){
        return jugadoresOcasionales.get(indice);
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
             System.out.println(jugadoresOcasionales.get(indice).getForma_juego().toLowerCase());
            if(ocasionalVista.jCheckBoxEstandar.isSelected() || ocasionalVista.jCheckBoxOcasional.isSelected()){
                ListaJugadoresFormulario ListaJugadoresFormulario = new ListaJugadoresFormulario(llenarDatos(tb1,tb2),ListaJugadoresVista, 
                        identificacionModelo, jugadorModelo);
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
