/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import BaseDatos.CalificacionBD;
import BaseDatos.InfraccionesBD;
import BaseDatos.JugadorBD;
import BaseDatos.PartidosBD;
import Control.Jtablemodelo.Encabezado;
import Control.Jtablemodelo.Columnas;
import Control.Jtablemodelo.Celda;
import Modelo.Identificacion;
import Modelo.Iterator.Itf_iterator;
import Modelo.Jugador;
import Vistas.Agregar_Ocasional;
import Vistas.Calificacion;
import Vistas.Infracciones;
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
    int rowAtPoint=0;
    int rowAtPoint2=0,penalizado=0;
    String nombre ="";
    int indice;
    
    public ListaJugadoresFormulario(ListaJugadores listaJugadoresVista, Identificacion identificacionModelo, Jugador jugadorModelo){
        this.listaJugadoresVista = listaJugadoresVista;
        this.jugadorModelo = jugadorModelo;
        this.identificacionModelo = identificacionModelo;
        this.listaJugadoresVista.jButtonAgregarOcasional.addActionListener(this);
        this.listaJugadoresVista.jButtonConfirmarEquipos.addActionListener(this);
        this.listaJugadoresVista.jButtonFormarEquipos.addActionListener(this);
        this.listaJugadoresVista.jButtonInfracciones.addActionListener(this);
        
        listaModelo = new Modelo.Iterator.ListaJugadores(); //listaj
        Itf_iterator iteracion = listaModelo.getIterator();
        
        
        actualizarDatosInicio();
        
        DefaultTableModel tabla = new DefaultTableModel();
        tabla=generarTablaOriginal(iteracion);
        
        generarEquipos(iteracion,tabla);
        deshabilitarJugador();
  
    }
    
     public ListaJugadoresFormulario(DefaultTableModel tabla1,DefaultTableModel tabla3) {
        this.tabla1=tabla1;
        this.tabla3=tabla3;
       
        int cantidadJugadores=0;
        CalificacionBD cbd = new CalificacionBD();
        JugadorBD jbd = new JugadorBD();
        int tamano=contarTabla(tabla1)+contarTabla(tabla3);
       
        for (int i = 0; i < tabla1.getRowCount(); i++) {
             if(tabla1.getValueAt(i, 1)!=null){
                 
                 if(cbd.CalificacionJugador(tabla1.getValueAt(i, 1).toString())==tamano){
                     Double sumaCalificaciones=0.0, promedio=0.0,promedioGeneral=0.0; 
                     
                    sumaCalificaciones=cbd.SumarCalificaciones(tabla1.getValueAt(i, 1).toString());
                    cantidadJugadores=cbd.CalificacionJugador(tabla1.getValueAt(i, 1).toString());
                
                    promedio=sumaCalificaciones/cantidadJugadores;
                    promedioGeneral=(jbd.BuscarPromedioGeneral(tabla1.getValueAt(i, 1).toString())+promedio)/2; //revisar
                    cbd.UpdatePromedios(promedio, tabla1.getValueAt(i, 1).toString());
                    jbd.UpdatePromedios(promedioGeneral, tabla1.getValueAt(i, 1).toString());
               }
             }
                             
             if(tabla3.getValueAt(i, 1)!=null){
                 if(cbd.CalificacionJugador(tabla3.getValueAt(i, 1).toString())==tamano){
                     Double sumaCalificaciones=0.0, promedio=0.0,promedioGeneral=0.0;
                     
                     sumaCalificaciones=cbd.SumarCalificaciones(tabla3.getValueAt(i, 1).toString());
                     cantidadJugadores=cbd.CalificacionJugador(tabla3.getValueAt(i, 1).toString());
                     
                     promedio=sumaCalificaciones/cantidadJugadores;
                     promedioGeneral=(jbd.BuscarPromedioGeneral(tabla3.getValueAt(i, 1).toString())+promedio)/2;
                     cbd.UpdatePromedios(promedio, tabla3.getValueAt(i, 1).toString());
                     jbd.UpdatePromedios(promedioGeneral, tabla3.getValueAt(i, 1).toString());
                 }
             }
                 
                
         }
        
    }
    
    public ListaJugadoresFormulario() {
    }
     
  
    public ListaJugadoresFormulario(DefaultTableModel tb1,DefaultTableModel tb2, Object[] datosocasionales) {
        this.tabla1=tb1;
        this.tabla3=tb2;
        if(!buscarRepetidoTabla(tabla1, tabla3, datosocasionales)){
            for (int i = 4; i >= 0; i--) {
                if(contarTabla(tabla1)<5) tabla1.setValueAt(datosocasionales[i], FilaVacia(tabla1), i);
                else if(contarTabla(tabla3)<5)tabla3.setValueAt(datosocasionales[i], FilaVacia(tabla3), i);
            } 
        }else JOptionPane.showMessageDialog(null,"El jugador esta repetido");
        
    }
    
      public void actualizarDatosInicio(){
        this.jugadorModelo.setUsuario(identificacionModelo.getUsuario());
        this.jugadorModelo.setRol(identificacionModelo.getRol());
        JOptionPane.showMessageDialog(null, "BIENVENIDO AL SISTEMA " + identificacionModelo.getRol() + " " + identificacionModelo.getUsuario());
    }
      
     
   
  
    public int FilaVacia(DefaultTableModel tabla){
        int contar=0;
        for (int i = 0; i <tabla.getRowCount(); i++) {
            if(tabla.getValueAt(i, 0)==null)
                return contar;
            else contar ++;
        }
        return contar;
    }
    public int contarTabla(DefaultTableModel tabla){
        int contar=0;
        for (int i = 0; i <tabla.getRowCount(); i++) {
            if(tabla.getValueAt(i, 0)!=null)
              contar ++;
        }
        return contar;
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
            
        
        JMenuItem agregar = new JMenuItem("agregar", new ImageIcon(getClass().getResource("/Imagenes/agregar.png")));
        
        
        menuLista.add(agregar);
        
       
        
        
        menuLista.addPopupMenuListener(new PopupMenuListener() {
            
             int rowAtPoint, jugadoresEquipos;
            @Override
             public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
             SwingUtilities.invokeLater(() -> {
                     System.out.println("seleccionado"+listaJugadoresVista.jTableListaJugadores.getSelectedColumn());
                     rowAtPoint = listaJugadoresVista.jTableListaJugadores.rowAtPoint(SwingUtilities.convertPoint(menuLista, new Point(0, 0), listaJugadoresVista.jTableListaJugadores));
                     jugadoresEquipos=contarTabla(tabla1)+contarTabla(tabla3);
                      
                     
                    
                            
                });
             }
           @Override
           public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            if (rowAtPoint > -1) {  
                        Object [] datosSeleccionados = ObtenerDatosTabla(rowAtPoint);
                        if(!buscarRepetidoTabla(tabla1,tabla3, datosSeleccionados)){
                            if(jugadoresEquipos==10){
                                if(TableToSet(tabla1, tabla3)!=null)
                                    reemplazarSolidario(datosSeleccionados,TableToSet(tabla1, tabla3),ObtenerIndice(TableToSet(tabla1, tabla3)));
                            }else{
                                int equipoEscogido = Integer.parseInt(JOptionPane.showInputDialog(
                                        "seleccione el equipo deseado \n"
                                                + "|1| equipo 1 \n"
                                                + "|2| equipo 2"));
                                switch (equipoEscogido) {
                                    case 1:
                                        if(FilaVacia(tabla1)!=0 && FilaVacia(tabla1)!=5) reemplazarSolidario(datosSeleccionados, tabla1, FilaVacia(tabla1));
                                        else{
                                            if (FilaVacia(tabla3)!=0 && FilaVacia(tabla3)!=5) reemplazarSolidario(datosSeleccionados, tabla3, FilaVacia(tabla3));
                                            JOptionPane.showMessageDialog(null, "equipo 1 esta lleno, agregado a equipo 2");
                                        }  break;
                                    case 2:
                                        if(FilaVacia(tabla3)!=0 && FilaVacia(tabla3)!=5) reemplazarSolidario(datosSeleccionados, tabla3, FilaVacia(tabla3));
                                        else{
                                            if (FilaVacia(tabla1)!=0 && FilaVacia(tabla1)!=5) reemplazarSolidario(datosSeleccionados, tabla1, FilaVacia(tabla1));
                                            JOptionPane.showMessageDialog(null, "equipo 1 esta lleno, agregado a equipo 2");
                                        }  break;
                                    default:
                                        JOptionPane.showMessageDialog(null, "Solo existen equipos 1 y 2");
                                        break;
                                }
                                
                            } 
                        } else JOptionPane.showMessageDialog(null, "el jugador seleccionado ya esta en uno de los 2 equipos");                    
                    }
                }
          
           @Override
           public void popupMenuCanceled(PopupMenuEvent e) {
               System.out.println("cancelado");
           }
         });
         
     
        this.listaJugadoresVista.jTableListaJugadores.setComponentPopupMenu(menuLista);
        }
        
       if(jugadorModelo.getRol().equals("jugador")){
        TableColumn sportColumn = this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(2);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("frecuente");
        comboBox.addItem("ocasional");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox)); 
        }
  
     return tabla;
    }
    
    public DefaultTableModel TableToSet(DefaultTableModel tabla, DefaultTableModel tabla2){
        for (int j = 0; j<tabla.getRowCount(); j++) {
            if(tabla.getValueAt(j, Columnas.FORMA).equals("solidaria"))
                return tabla;
            else if (tabla2.getValueAt(j, Columnas.FORMA).equals("solidaria"))
                return tabla2;
        }
        return null;
    }
    
    
    public int ObtenerIndice(DefaultTableModel tabla){
        if(tabla!=null){
           for (int j = 0; j<tabla.getRowCount(); j++) {
               if(tabla.getValueAt(j, Columnas.FORMA).equals("solidaria"))
                   return (int)tabla.getValueAt(j, 0);
            }
        
        }return -1;
    }
    
    public void reemplazarSolidario(Object datos[],DefaultTableModel tabla, int indice){
              for (int i = 0; i < tabla.getColumnCount(); i++) {
                  tabla.setValueAt(datos[i],indice, i);               
                 }
    }

    
    public boolean buscarRepetidoTabla(DefaultTableModel tabla,DefaultTableModel tabla2,Object datos[]){
       boolean estado =false;
       for (int i=0; i<tabla.getRowCount();i++){
           if(datos[1].equals(tabla.getValueAt(i, 1)) || datos[1].equals(tabla2.getValueAt(i, 1))){ // quede aqui
                 return true;
       }
       }
        return estado;
    }
    
    public Object[] ObtenerDatosTabla(int rowpoint){
        Object[] datos = new Object[7];
        for (int i = 0; i < tabla.getColumnCount(); i++) {
             datos[i]=tabla.getValueAt(rowpoint, i);
           
        }
        return datos;
    }
    public Object[] ObtenerDatosTabla(int rowpoint,DefaultTableModel tabla){
        Object[] datos = new Object[5];
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
        this.listaJugadoresVista.jTableListaJugadores.getColumnModel().getColumn(Columnas.PENALIZADO).setCellRenderer(new Celda("penalizado",jugadorModelo,penalizado));
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
                if(i<5) tabla1.addRow(llenarObjeto(i, llenarArray(Listaequipo1, iteracion)));
                else tabla3.addRow(llenarObjeto(i, llenarArray(Listaequipo1, iteracion)));
            }
         
       cuerpoTablaEquipos(this.listaJugadoresVista.jTableEquipo2);
       cuerpoTablaEquipos(this.listaJugadoresVista.jTableEquipo1);
              
        EncabezadoTabla(this.listaJugadoresVista.jTableEquipo1);
        EncabezadoTabla(this.listaJugadoresVista.jTableEquipo2);
       
        JPopupMenu menuLista= new JPopupMenu();
        JMenuItem eliminar = null;
        if(jugadorModelo.getRol().equals("jugador")){
            eliminar = new JMenuItem("darse de baja", new ImageIcon(getClass().getResource("/Imagenes/eliminar.png")));
        }
        
        if(jugadorModelo.getRol().equals("admin")){
        
            JMenuItem cambiar = new JMenuItem("Mover jugador", new ImageIcon(getClass().getResource("/Imagenes/Cambiar.jpg")));
            eliminar = new JMenuItem("eliminar", new ImageIcon(getClass().getResource("/Imagenes/eliminar.png")));
        
            cambiar.addActionListener((ActionEvent e)->{
            if(rowAtPoint>-1){
                if(moverJugador(tabla3, tabla1, rowAtPoint))
                    JOptionPane.showMessageDialog(null, "jugador cambiado");
                    
                  
                }
            else if (rowAtPoint2>-1){ // here
                if(moverJugador(tabla1, tabla3, rowAtPoint2))
                JOptionPane.showMessageDialog(null, "jugador cambiado");
            }
            });
            menuLista.add(cambiar);
        }
    
        eliminar.addActionListener((ActionEvent e)->{
           if(rowAtPoint>-1){ // tabla 1
               if(EliminarJugador(tabla1, rowAtPoint,nombre))
                   JOptionPane.showMessageDialog(null, "El jugador "+nombre+" fue eliminado");
               
           }else if (rowAtPoint2>-1){
               if(EliminarJugador(tabla3, rowAtPoint2,nombre))
                   JOptionPane.showMessageDialog(null, "El jugador "+nombre+" fue eliminado");
           }
            
        });
          
        menuLista.add(eliminar);
        
        menuLista.addPopupMenuListener(new PopupMenuListener() {
            
            @Override
             public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
             SwingUtilities.invokeLater(() -> {
                     rowAtPoint = listaJugadoresVista.jTableEquipo1.rowAtPoint(SwingUtilities.convertPoint(menuLista, new Point(0, 0), listaJugadoresVista.jTableEquipo1));
                     rowAtPoint2 = listaJugadoresVista.jTableEquipo2.rowAtPoint(SwingUtilities.convertPoint(menuLista, new Point(0, 0), listaJugadoresVista.jTableEquipo2));
                     
             });
             }

             @Override
             public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

             @Override
             public void popupMenuCanceled(PopupMenuEvent e) {
                 System.out.println("me cancele");
             }
         });
          
        this.listaJugadoresVista.jTableEquipo1.setComponentPopupMenu(menuLista);
        this.listaJugadoresVista.jTableEquipo2.setComponentPopupMenu(menuLista);
        
    }
    
    public boolean moverJugador(DefaultTableModel tablaCambio, DefaultTableModel tablaActual, int seleccion){
        boolean estado=false;
        if(FilaVacia(tablaCambio)<5){
                    reemplazarSolidario(ObtenerDatosTabla(seleccion,tablaActual), tablaCambio, FilaVacia(tablaCambio));
                    EliminarJugador(tablaActual, seleccion,nombre);
                    estado=true;
                }
     return estado;
    }
    
    public boolean EliminarJugador(DefaultTableModel tabla1, int rowAtPoint,String nombre ){
        int i =0;
        boolean estado=false;
        Object[] datos = new Object[5];
        if(jugadorModelo.getRol().equals("admin")){
                   reemplazarSolidario(new Object[5], tabla1, rowAtPoint);
                   JOptionPane.showMessageDialog(null, "jugador eliminado");
                   
               }else if (jugadorModelo.getRol().equals("jugador") && (jugadorModelo.getNombre().equals(tabla1.getValueAt(rowAtPoint, Columnas.NOMBRE)))){
                    
                   nombre = JOptionPane.showInputDialog(null, "debe elegir reemplazo si no se le generara una infraccion");
                     
                    for ( i = 0; i < tabla.getRowCount(); i++) {   
                  
                        if (tabla.getValueAt(i, Columnas.NOMBRE).equals(nombre)){
                            datos = ObtenerDatosTabla(i);
                            if(!buscarRepetidoTabla(this.tabla1, tabla3, datos)) {
                                reemplazarSolidario(datos, tabla1, rowAtPoint);
                                 System.out.println("datos del personaje"+Arrays.toString(datos)+"\n"+
                                   "personaje encontrado en fila"+ i+" columna"+Columnas.NOMBRE); 
                                 estado=true;
                            }
                        }
                        
                       }
                    if(datos[0] == null && !nombre.equals(""))
                           JOptionPane.showMessageDialog(null, "el jugador "+nombre+" no esta en la comunidad ");
                    else if(datos[0]!=null && buscarRepetidoTabla(this.tabla1, tabla3, datos))
                           JOptionPane.showMessageDialog(null, "el jugador "+nombre+" ya esta en una de las tablas ");
                     else if(nombre.equals("")){
                         InfraccionesBD infraccionesBd = new InfraccionesBD();
                         jugadorModelo.setCantidad_infracciones(jugadorModelo.getCantidad_infracciones()+1);
                         infraccionesBd.Updateinfraccion(jugadorModelo);
                         InfraccionesFormulario infraccionesFormulario = new  InfraccionesFormulario();
                         if(this.rowAtPoint>-1)
                         
                         infraccionesFormulario.GenerarPenalizacion(jugadorModelo, ObtenerDatosTabla(this.rowAtPoint),tabla,tabla1,rowAtPoint); //here
                         else if(rowAtPoint2>-1)
                         infraccionesFormulario.GenerarPenalizacion(jugadorModelo, ObtenerDatosTabla(this.rowAtPoint2+5),tabla,tabla1,rowAtPoint);  
                         JOptionPane.showMessageDialog(null, "el jugador "+nombre+" tiene una infraccion "+jugadorModelo.getCantidad_infracciones());
                         
                           
                       }
                   
               }else JOptionPane.showMessageDialog(null, "la fila seleccionada no es su usuario");
        return estado;
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
            if (i < lista.size() && lista.get(i).isAsistio()) {
             datos[0] = i;
             datos[1] = lista.get(i).getNombre();
             datos[2] = lista.get(i).getModo_suscripcion();
             datos[3] = lista.get(i).getForma_juego();
             datos[4] = lista.get(i).getPromedio_general();
        }
        
        
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
    
    public void cargarVentana(DefaultTableModel tabla,DefaultTableModel tabla2){
       JugadorBD jugadorBD = new JugadorBD();
        for (indice = 0; indice < tabla.getRowCount(); indice++) {
            if(tabla.getValueAt(indice, 1)!=null){
                jugadorModelo.setNombre(tabla.getValueAt(indice, 1).toString());
                jugadorModelo=jugadorBD.LlenarJugador(jugadorModelo);
                Calificacion calificacionVista = new Calificacion();
                CalificacionFormulario CalificacionFormulario = new CalificacionFormulario(jugadorModelo,tabla1, tabla3, listaModelo.getIterator(), calificacionVista, listaJugadoresVista);
                calificacionVista.setVisible(true);   
            }
               
            if(tabla2.getValueAt(indice, 1)!=null){
                 jugadorModelo.setNombre(tabla2.getValueAt(indice, 1).toString());
                 jugadorModelo=jugadorBD.LlenarJugador(jugadorModelo);
                 Calificacion calificacionVista = new Calificacion();
                 CalificacionFormulario CalificacionFormulario = new CalificacionFormulario(jugadorModelo,tabla1, tabla3, listaModelo.getIterator(), calificacionVista, listaJugadoresVista);
                 calificacionVista.setVisible(true); 
            }
                 
        }
        
    }
    
    //llamados a botones de acciongit
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == listaJugadoresVista.jButtonAgregarOcasional) {
            Agregar_Ocasional ocasionalVista = new Agregar_Ocasional();
            AgregarOcasionalFormulario agregarFormulario = new AgregarOcasionalFormulario(tabla1,tabla3, listaModelo.getIterator(), ocasionalVista,listaJugadoresVista);
            ocasionalVista.setVisible(true);
            listaJugadoresVista.dispose();
        }

        if (e.getSource() == listaJugadoresVista.jButtonConfirmarEquipos) {
            if(contarTabla(tabla1)==contarTabla(tabla3)){
                PartidosBD pbd = new PartidosBD();
                cargarVentana(tabla1,tabla3); 
                pbd.insertarPartido();
                
            }else JOptionPane.showMessageDialog(null,"los equipos estan disparejos");
            
    
        }
        if (e.getSource() == listaJugadoresVista.jButtonFormarEquipos) {
            ListaAutomatica ListaAutoVista = new ListaAutomatica();
            ListaAutomaticaFormulario ListaAutomarticaFormulario = new ListaAutomaticaFormulario(ListaAutoVista, listaJugadoresVista);
            ListaAutoVista.setVisible(true);
        }
        if (e.getSource() == listaJugadoresVista.jButtonInfracciones) {
            Infracciones infraccionesVista = new Infracciones();
            InfraccionesFormulario infraccionesFormulario = new InfraccionesFormulario(jugadorModelo,tabla1,tabla3,infraccionesVista, listaJugadoresVista);
            infraccionesVista.setVisible(true);
        }
    }
}
   