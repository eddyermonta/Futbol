/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import BaseDatos.IdentificacionBD;
import Control.Jtablemodelo.Encabezado;
import Control.Jtablemodelo.Columnas;
import Control.Jtablemodelo.Celda;
import Modelo.Identificacion;
import Modelo.Iterator.Itf_iterator;
import Modelo.Jugador;
import Vistas.Agregar_Ocasional;
import Vistas.Calificacion;
import Vistas.Infracciones;
import Vistas.Inicio_sesion;
import Vistas.ListaAutomatica;
import Vistas.ListaJugadores;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *
 * @author eduar
 */
public class ListaJugadoresFormulario implements ActionListener {

    ListaJugadores listaJugadoresVista;
    Identificacion identificacionModelo;
    Jugador jugadorModelo;
    Modelo.Iterator.ListaJugadores listaModelo;
    DefaultTableModel tabla1;
    DefaultTableModel tabla3; 
    DefaultTableModel tabla;
    ArrayList<Jugador> Listaequipo1;
    
    public ListaJugadoresFormulario(Object[] datosocasionales,ListaJugadores listaJugadoresVista, Identificacion identificacionModelo, Jugador jugadorModelo){
        this.listaJugadoresVista = listaJugadoresVista;
        this.jugadorModelo = jugadorModelo;
        this.identificacionModelo = identificacionModelo;
        this.listaJugadoresVista.jButtonAgregarOcasional.addActionListener(this);
        this.listaJugadoresVista.jButtonAtras.addActionListener(this);
        this.listaJugadoresVista.jButtonConfirmarEquipos.addActionListener(this);
        this.listaJugadoresVista.jButtonFormarEquipos.addActionListener(this);
        this.listaJugadoresVista.jButtonInfracciones.addActionListener(this);
        
        listaModelo = new Modelo.Iterator.ListaJugadores(); //listaj
        Itf_iterator iteracion = listaModelo.getIterator();
        
        this.jugadorModelo.setUsuario(identificacionModelo.getUsuario());
        this.jugadorModelo.setRol(identificacionModelo.getRol());
       
        
        DefaultTableModel tabla = new DefaultTableModel();
        tabla=generarTablaOriginal(iteracion);
        
        generarEquipos(iteracion,tabla);
        deshabilitarJugador();
        System.out.println("sss");

        JOptionPane.showMessageDialog(null, "BIENVENIDO AL SISTEMA " + identificacionModelo.getRol() + " " + identificacionModelo.getUsuario());
    
        if (datosocasionales[0]!=null) {
            if(tabla1.getRowCount()<5){
            tabla1.addRow(datosocasionales);
            System.out.println("agrego 1");
        } else if(tabla3.getRowCount()<5){
             tabla3.addRow(datosocasionales);
             System.out.println("agrego 2");
        }
        }
    }
    
    public DefaultTableModel generarTablaOriginal(Itf_iterator iteracion){
        tabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Integer.class;
                if (columnIndex == 1 || columnIndex == 2 || columnIndex == 3)  return String.class;
                if (columnIndex == 5 || columnIndex == 6) return Boolean.class;
                if (columnIndex == 4)  return double.class;
                return super.getColumnClass(columnIndex); //To change body of generated methods, choose Tools | Templates.
            }  
        };
                   
        generarTabla(tabla);
        llenarLista(iteracion,tabla);
        cuerpoTablaOriginal();
        EncabezadoTabla(this.listaJugadoresVista.jTableListaJugadores);
        
        
        
        JPopupMenu menuLista= new JPopupMenu();
        if(jugadorModelo.getRol().equals("admin")){
            
        
        JMenuItem agregar = new JMenuItem("agregar", new ImageIcon(getClass().getResource("agregar.png")));
        menuLista.add(agregar);
         menuLista.addPopupMenuListener(new PopupMenuListener() {
            int rowAtPoint,jugadoresEquipos;
            @Override
             public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
             SwingUtilities.invokeLater(() -> {
                     rowAtPoint = listaJugadoresVista.jTableListaJugadores.rowAtPoint(SwingUtilities.convertPoint(menuLista, new Point(0, 0), listaJugadoresVista.jTableListaJugadores));
                    System.out.println(rowAtPoint);
                    jugadoresEquipos=tabla1.getRowCount()+tabla3.getRowCount();
                });
             }
             

           @Override
           public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    if (rowAtPoint > -1) {                                      
                       // if(jugadoresEquipos==10){
                            if(indiceSolidario(tabla1)!=0)
                                reemplazarSolidario(ObtenerDatosTabla(rowAtPoint),tabla1,indiceSolidario(tabla1)); //-> tomo el objeto con que voy a remplazar
                            else if(indiceSolidario(tabla3)!=0)
                                reemplazarSolidario(ObtenerDatosTabla(rowAtPoint),tabla3,indiceSolidario(tabla3));
                    //}
                        System.out.println("lista de jugadores en equipo "+jugadoresEquipos);  
                    }
           }
           @Override
           public void popupMenuCanceled(PopupMenuEvent e) {
               System.out.println("cancelado");
           }

         });
     
        this.listaJugadoresVista.jTableListaJugadores.setComponentPopupMenu(menuLista);
        
       if(jugadorModelo.getRol().equals("jugador")){
        TableColumn sportColumn = this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(2);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("frecuente");
        comboBox.addItem("ocasional");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox)); 
        }
       
        
       
    } return tabla;
    }
    
    public void reemplazarSolidario(Object datos[],DefaultTableModel tabla, int indice){
        
              for (int i = 0; i < tabla1.getColumnCount(); i++) {
                  tabla.setValueAt(datos[i],indice, i);
                  
                  Listaequipo1.set(indice,new Jugador()); //pendiente a modificar
                 }
              
              
              
    }
    
    public int indiceSolidario(DefaultTableModel tabla){
        int j = 0;
        for (int i = 0; i < tabla.getRowCount(); i++) {
            for (j=0; j < tabla.getRowCount(); j++) {
                    if(tabla.getValueAt(j, i).equals("solidaria"))
                        return j;
                    
            }
    }
        return j;
    }
    
    public Object[] ObtenerDatosTabla(int rowpoint){
        Object[] datos = new Object[7];
        for (int i = 0; i < tabla.getColumnCount(); i++) {
             datos[i]=tabla.getValueAt(rowpoint, i);
           
        }
        return datos;
    }
    
    public void llenarLista(Itf_iterator iteracion,DefaultTableModel tabla){
        while (iteracion.isDone()) {
            tabla.addRow(iteracion.next());
        }iteracion.first();
       
    }
    
    public void generarTabla(DefaultTableModel tabla){
        tabla.addColumn("posicion");//numero
        tabla.addColumn("nombre");
        tabla.addColumn("suscripcion");//si
        tabla.addColumn("forma");
        tabla.addColumn("calificacion");
        tabla.addColumn("asistencia");
        tabla.addColumn("penalizado");//SU
        this.listaJugadoresVista.jTableListaJugadores.setModel(tabla);
    }
    
    
    public void cuerpoTablaOriginal(){
        this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(Columnas.ASISTENCIA).setCellRenderer(new Celda("asistio",jugadorModelo));
        this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(Columnas.CALIFICACION).setCellRenderer(new Celda("numerico",jugadorModelo));
        this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(Columnas.FORMA).setCellRenderer(new Celda("texto",jugadorModelo));
        this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(Columnas.NOMBRE).setCellRenderer(new Celda("texto",jugadorModelo));
        this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(Columnas.PENALIZADO).setCellRenderer(new Celda("penalizado",jugadorModelo));
        this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(Columnas.POSICION).setCellRenderer(new Celda("numerico",jugadorModelo));
        this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(Columnas.SUSCRIPCION).setCellRenderer(new Celda("texto",jugadorModelo));
    
        }
    
    public void EncabezadoTabla(JTable tabla){
        JTableHeader jtableHeader = tabla.getTableHeader() ;
        jtableHeader.setDefaultRenderer(new Encabezado());
        tabla.setTableHeader(jtableHeader);
    }
    
    public void generarTablaequipos(JTable tablavista, Itf_iterator iteracion,DefaultTableModel tabla){
        tabla.addColumn("posicion");//numero
        tabla.addColumn("nombre");
        tabla.addColumn("suscripcion");//si
        tabla.addColumn("forma");
        tabla.addColumn("calificacion");
        tablavista.setModel(tabla);  
        
    }
    
    public ArrayList<Jugador> llenarArray( ArrayList<Jugador> Listaequipo1,Itf_iterator iteracion){
        if(Listaequipo1.isEmpty()){
            while (iteracion.isDone()) {
            iteracion.dameArray(Listaequipo1).toArray();
        }
        } iteracion.first();
        
        return Listaequipo1;
    }
    
    public void generarEquipos(Itf_iterator iteracion, DefaultTableModel listajugadores){
          tabla1 = new DefaultTableModel(){
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
             
         };
         tabla3 = new DefaultTableModel(){
            @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        
        generarTablaequipos(this.listaJugadoresVista.jTableEquipo1, iteracion,tabla1);
        generarTablaequipos(this.listaJugadoresVista.jTableEquipo2, iteracion,tabla3);
        
          Listaequipo1 = new ArrayList<>();
          for (int i = 0; i < 10; i++) {
            if(i<5){
                tabla1.addRow(llenarObjeto(i, llenarArray(Listaequipo1, iteracion)));
         
            }
                 
            else{
                if(llenarObjeto(i, llenarArray(Listaequipo1, iteracion))[1]!=null)
                tabla3.addRow(llenarObjeto(i, llenarArray(Listaequipo1, iteracion)));
                
                
            }  
        }   
       cuerpoTablaEquipos(this.listaJugadoresVista.jTableEquipo2);
       cuerpoTablaEquipos(this.listaJugadoresVista.jTableEquipo1);
              
        EncabezadoTabla(this.listaJugadoresVista.jTableEquipo1);
        EncabezadoTabla(this.listaJugadoresVista.jTableEquipo2);
        
        JPopupMenu menuLista= new JPopupMenu();
        JMenuItem eliminar = new JMenuItem("eliminar", new ImageIcon(getClass().getResource("eliminar.png")));
        menuLista.add(eliminar);
        
        menuLista.addPopupMenuListener(new PopupMenuListener() {
            int rowAtPoint;
            @Override
             public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
             SwingUtilities.invokeLater(() -> {
                     rowAtPoint = listaJugadoresVista.jTableListaJugadores.rowAtPoint(SwingUtilities.convertPoint(menuLista, new Point(0, 0), listaJugadoresVista.jTableListaJugadores));
                    System.out.println(rowAtPoint);
                    if (rowAtPoint > -1) {
                        listaJugadoresVista.jTableListaJugadores.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        System.out.println("sas");
                    }
                });
             }

             @Override
             public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    tabla1.removeRow(rowAtPoint);
             }

             @Override
             public void popupMenuCanceled(PopupMenuEvent e) {
                 System.out.println("me cancele");
             }
         });
          
        this.listaJugadoresVista.jTableEquipo1.setComponentPopupMenu(menuLista);
        this.listaJugadoresVista.jTableEquipo2.setComponentPopupMenu(menuLista);
        
    }
    
    public void cuerpoTablaEquipos(JTable equipo){
        equipo.getColumnModel().getColumn(4).setCellRenderer(new Celda("numerico",jugadorModelo));
        equipo.getColumnModel().getColumn(3).setCellRenderer(new Celda("texto",jugadorModelo));
        equipo.getColumnModel().getColumn(1).setCellRenderer(new Celda("texto",jugadorModelo));
        equipo.getColumnModel().getColumn(0).setCellRenderer(new Celda("numerico",jugadorModelo));
        equipo.getColumnModel().getColumn(2).setCellRenderer(new Celda("texto",jugadorModelo));
        
    }
    

    //llenar datos de los equipos
    public Object[] llenarObjeto(int i, ArrayList<Jugador> lista) {
        Object[] datos = new Object[5];
       
            if (i < lista.size()) {
             datos[0] = i;
             datos[1] = lista.get(i).getNombre();
             datos[2] = lista.get(i).getModo_suscripcion();
             datos[3] = lista.get(i).getForma_juego();
             datos[4] = lista.get(i).getPromedio_general();
        }
        
        System.out.println(datos[0]);
        return datos;
    }

    //para deshabilitar opciones de jugador
    public void deshabilitarJugador() {
        if (identificacionModelo.getRol().equals("jugador")) {
            this.listaJugadoresVista.jButtonAgregarOcasional.setEnabled(false);
            this.listaJugadoresVista.jButtonConfirmarEquipos.setEnabled(false);
            this.listaJugadoresVista.jButtonFormarEquipos.setEnabled(false);
            this.listaJugadoresVista.jButtonInfracciones.setEnabled(false);
        }
    }
    //llamados a botones de acciongit
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == listaJugadoresVista.jButtonAgregarOcasional) {
            Agregar_Ocasional ocasionalVista = new Agregar_Ocasional();
            AgregarOcasionalFormulario agregarFormulario = new AgregarOcasionalFormulario(tabla1,tabla3,listaJugadoresVista, listaModelo, identificacionModelo, jugadorModelo, ocasionalVista);
            ocasionalVista.setVisible(true);
        }
        if (e.getSource() == listaJugadoresVista.jButtonAtras) {
            Inicio_sesion inicio_sesionVista = new Inicio_sesion();
             identificacionModelo = new Identificacion();
            IdentificacionBD identificacionDatos = new IdentificacionBD();

            InicioSesionFormulario sesionFormulario = new InicioSesionFormulario(inicio_sesionVista, identificacionModelo, identificacionDatos);
            listaJugadoresVista.dispose();
            inicio_sesionVista.setVisible(true);

        }
        if (e.getSource() == listaJugadoresVista.jButtonConfirmarEquipos) {
            Calificacion calificacionVista = new Calificacion();
            CalificacionFormulario CalificacionFormulario = new CalificacionFormulario(calificacionVista, listaJugadoresVista, identificacionModelo, jugadorModelo);
            calificacionVista.setVisible(true);
        }
        if (e.getSource() == listaJugadoresVista.jButtonFormarEquipos) {
            ListaAutomatica ListaAutoVista = new ListaAutomatica();
            ListaAutomaticaFormulario ListaAutomarticaFormulario = new ListaAutomaticaFormulario(ListaAutoVista, listaJugadoresVista, identificacionModelo, jugadorModelo);
            ListaAutoVista.setVisible(true);
        }
        if (e.getSource() == listaJugadoresVista.jButtonInfracciones) {
            Infracciones infraccionesVista = new Infracciones();
            InfraccionesFormulario infraccionesFormulario = new InfraccionesFormulario(infraccionesVista, listaJugadoresVista, identificacionModelo, jugadorModelo);
            infraccionesVista.setVisible(true);
        }
    }
}
   