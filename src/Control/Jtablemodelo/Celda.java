/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Jtablemodelo;

import BaseDatos.InfraccionesBD;
import BaseDatos.JugadorBD;
import Control.InfraccionesFormulario;
import Control.ListaJugadoresFormulario;
import Modelo.Jugador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Celda extends DefaultTableCellRenderer {

    private String tipo = "text";
    private Font normal = new Font("Verdana", Font.PLAIN, 12);
    private Font bold = new Font("Verdana", Font.BOLD, 12);
    Jugador jug;
    int penalizado;

    public Celda(String tipo, Jugador jug) {
        this.tipo = tipo;
        this.jug = jug;
    }

    public Celda(String tipo) {
         this.tipo = tipo;
    }
    

    public Celda(String tipo,Jugador jug, int penalizado) {
        this.tipo = tipo;
        this.jug = jug;
        this.penalizado=penalizado;
    }
    

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//--------------------------------------------------------------------------------------------------global
        Color colorFondo = null;
        Color colorFondoPorDefecto = new Color(192, 192, 192);
        Color colorFondoSeleccion = new Color(140, 140, 140);
        int rowSeleccion = 0;
       
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        if (isSelected) this.setBackground(colorFondoPorDefecto);
        else this.setBackground(Color.GREEN);
        JugadorBD jugaBd = new JugadorBD();
//-------------------------------------------------------------------------------------------------- textos
        if (tipo.equals("texto")) {
        
            if (hasFocus) colorFondo = colorFondoSeleccion;
            else colorFondo = colorFondoPorDefecto;
            
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setText((String) value);
            this.setFont(normal);
                     
            if(value!=null){
                if (jug!=null &&model.getValueAt(row, 1).equals(jug.getNombre())) {
                    rowSeleccion = row; 
                
                    
                if (hasFocus && (value.equals("frecuente") || value.equals("ocasional"))){
                   jug.setModo_suscripcion(value.toString());
                  
                   
                    if(!(jug.isPenalizado()) && jugaBd.updateJugadorModo(jug))
                        System.out.println("actualizado en bd");
                        
                    
                }
                  
                    if((model.getValueAt(rowSeleccion, 1).equals(jug.getNombre())) ||
                       (model.getValueAt(rowSeleccion, 2).equals(jug.getModo_suscripcion())) || 
                       (model.getValueAt(rowSeleccion, 3).equals(jug.getForma_juego()))
                        ) this.setBackground(Color.BLUE);
                
               
                } else this.setBackground((isSelected) ? colorFondo : Color.GREEN);
            }
                
            
        return this;
        }
 
//--------------------------------------------------------------------------------------------------numeros
    
        if (tipo.equals("numerico")) {
            if (hasFocus) colorFondo = colorFondoSeleccion;
            else  colorFondo = colorFondoPorDefecto;
            
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setValue(value);
            this.setFont(bold);
            this.setForeground((isSelected) ? new Color(255, 255, 255) : new Color(32, 117, 32));
            
            if(value!=null && model.getValueAt(row, 1)!=null ){
                if (jug!=null && model.getValueAt(row, 1).equals(jug.getNombre())) {
                    rowSeleccion = row; 
                   
                        if(model.getValueAt(rowSeleccion, 4).equals(jug.getPromedio_general()))
                        this.setBackground(Color.BLUE);
                        else if (model.getValueAt(rowSeleccion, 3).equals(jug.getPromedio_general()))
                             this.setBackground(Color.BLUE);
                   
                } else this.setBackground((isSelected) ? colorFondo : Color.GREEN);
            }
            
        return this;
        }
//--------------------------------------------------------------------------------------------------boolean      
        
        if (tipo.equals("asistio")){
            int foco=0;
            if (hasFocus){
                colorFondo = colorFondoSeleccion;
                foco=row;
            }else colorFondo = colorFondoPorDefecto;
           
            this.setHorizontalAlignment(JLabel.CENTER);
            JCheckBox check = new JCheckBox();
            
//--------------------------------------------------------------------------------------------------modela los check y carga los enable 
            if (model.getValueAt(row, 5).getClass().equals(Boolean.class)) {
                    check.setSelected(Boolean.parseBoolean(model.getValueAt(row, 5).toString()));
                
                if(jug.getRol().equals("jugador")){
                    if((model.getValueAt(row, 1).equals(jug.getNombre())))
                    check.setEnabled(true);
                else check.setEnabled(false);
                }else if(jug.getRol().equals("admin")){
                    check.setEnabled(true);
                    
                }
                                 
                    
            JugadorBD jugadorbd= new JugadorBD();
            Jugador jugaadmin = new Jugador();
//-----------------------------------------------------------------------------------------------------------------------------------------

     
         
//--------------------------------------------------------------------------------------------------si di clicl y quedo true actualiza                 
            
            if (value.equals(true)){
                check.setText("si");
                jug.setAsistio(true);
                    if(hasFocus){
                        if(jug.getNombre()!=null && jug.getRol().equals("jugador") && check.isEnabled()){
                            jugadorbd.updateJugador(jug);
                        }else if (jug.getRol().equals("admin")){
                            
                            if(model.getValueAt(row, 5).equals(true) && !jugaadmin.isAsistio() && check.getText().equals("si")){
                            jugaadmin.setNombre(model.getValueAt(row, 1).toString());
                            jugadorbd.LlenarJugador(jugaadmin);
                                penalizado++;
                                if(penalizado==1){
                                    System.out.println("penalizado "+penalizado);
                                    InfraccionesFormulario infraccionesFormulario = new InfraccionesFormulario();
                                    JOptionPane.showMessageDialog(null, "se genero una infraccion por inasistencia");
                                    jugaadmin.setAsistio(false);
                                    check.setText("no");
                                    check.setSelected(false);
                                    
                                    InfraccionesBD infraccionesBd = new InfraccionesBD();
                                    jugaadmin.setCantidad_infracciones(jugaadmin.getCantidad_infracciones()+1);
                                    infraccionesBd.Updateinfraccion(jugaadmin);
                                    this.setEnabled(false);

                                    infraccionesFormulario.GenerarPenalizacion(jugaadmin, row, model);    
                                }
                                penalizado=0;
                            }

                            jugaadmin.setAsistio(true);
                            jugadorbd.updateJugador(jugaadmin);
                        }
                    }
            }else  if (value.equals(false)){
                jug.setAsistio(false);
                check.setText("no"); 
                    if(hasFocus){
                        if(jug.getNombre()!=null && jug.getRol().equals("jugador")&& check.isEnabled()){
                            System.out.println("personaje actualizado "+jug.getNombre());
                            jugadorbd.updateJugador(jug);
                            
                        } else if (jug.getRol().equals("admin")){
                           
                                 penalizado--;
                             
                            jugaadmin.setNombre(model.getValueAt(row, 1).toString());
                            jugaadmin.setAsistio(false);
                            
                            jugadorbd.updateJugador(jugaadmin);
                            
                        }
                    }
                }
              
//--------------------------------------------------------------------------------------------------pinte segun jug iniciado 
            
                 if(model.getValueAt(row, 1).equals(jug.getNombre())){
                     rowSeleccion=row;
                     if (value.equals(true)) check.setBackground(Color.RED);
                     
                     else if((model.getValueAt(rowSeleccion, 5).equals(jug.isAsistio())))
                         check.setBackground(Color.BLUE);
                     
                 }
                 else check.setBackground((isSelected) ? colorFondo : Color.GREEN);
            return check;
            }
        this.setForeground((isSelected) ? new Color(255, 255, 255) : new Color(32, 117, 32));
        this.setFont(bold);
            
        return this;
        }
//--------------------------------------------------------------------------------------------------penalizado
    
        if (tipo.equals("penalizado")){
            if (hasFocus) colorFondo = colorFondoSeleccion;
            else colorFondo = colorFondoPorDefecto;
           
            this.setHorizontalAlignment(JLabel.CENTER);
            JCheckBox check1 = new JCheckBox();
//--------------------------------------------------------------------------------------------------modela los check            
            if (model.getValueAt(row, 6).getClass().equals(Boolean.class)) {
                    check1.setSelected(Boolean.parseBoolean(model.getValueAt(row, 6).toString()));
                    
                    if (value.equals(true)){
                       check1.setText("si");
                       //check1.setBackground(Color.red);
                    }else check1.setText("no");   
                    check1.setEnabled(false);
             
 //--------------------------------------------------------------------------------------------------colorea checks                   
            if(model.getValueAt(row, 1).equals(jug.getNombre())){
                    rowSeleccion=row;
                    if (value.equals(true)) check1.setBackground(Color.RED);
                    else if((model.getValueAt(rowSeleccion, 6).equals(jug.isPenalizado()))
                             )
                        check1.setBackground(Color.BLUE);
                 } else check1.setBackground((isSelected) ? colorFondo : Color.GREEN);
             return check1;
            }
             this.setForeground((isSelected) ? new Color(255, 255, 255) : new Color(32, 117, 32));
        this.setFont(bold);

        return this;
        }
        
     if (tipo.equals("double")){
          if (hasFocus) colorFondo = colorFondoSeleccion;
            else colorFondo = colorFondoPorDefecto;
           
            this.setHorizontalAlignment(JLabel.CENTER);
            JSpinner JSpinner = new JSpinner();
          if (model.getValueAt(row, 3).getClass().equals(Double.class)){
              
              JSpinner.setValue(0.0);
              if(hasFocus)
                  
           //--------------------------------------------------------------------------------------------------colorea checks                   
            JSpinner.setBackground(Color.GREEN);
             return JSpinner;
              
              
          }
          this.setForeground((isSelected) ? new Color(255, 255, 255) : new Color(32, 117, 32));
        this.setFont(bold);
        
        return this;
     }
     
     if (tipo.equals("numerico2")) {
            if (hasFocus) colorFondo = colorFondoSeleccion;
            else  colorFondo = colorFondoPorDefecto;
            
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setValue(value);
            this.setFont(bold);
            this.setForeground((isSelected) ? new Color(255, 255, 255) : new Color(32, 117, 32));
            
            if(value!=null && model.getValueAt(row, 1)!=null ){
                if (jug!=null && model.getValueAt(row, 1).equals(jug.getNombre())) {
                    rowSeleccion = row; 
                
                        if(model.getValueAt(rowSeleccion, 3).equals(jug.getPromedio_general()))
                        this.setBackground(Color.BLUE);
                   
                } else this.setBackground((isSelected) ? colorFondo : Color.GREEN);
            }
            
        return this;
        }
     
      
    return this;
    }
    

    
}
    
