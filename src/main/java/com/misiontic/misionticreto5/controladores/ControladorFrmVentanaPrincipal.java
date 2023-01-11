/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misiontic.misionticreto5.controladores;

import com.misiontic.misionticreto5.modelos.Farmacia;
import com.misiontic.misionticreto5.modelos.Producto;
import com.misiontic.misionticreto5.vistas.FrmVentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView;

/**
 *
 * @author Daniel Cifuentes
 */
public class ControladorFrmVentanaPrincipal implements ActionListener, MouseListener, ChangeListener, KeyListener{

    private final FrmVentanaPrincipal ventana;
    
    public ControladorFrmVentanaPrincipal() {
        ventana = new FrmVentanaPrincipal();
        ventana.getBtnBuscar().addActionListener(this);
        ventana.getBtnNuevo().addActionListener(this);
        ventana.getBtnGuardar().addActionListener(this);
        ventana.getBtnEliminar().addActionListener(this);
        ventana.getTblFarmacias().addMouseListener(this);
        ventana.getBtnNuevoProducto().addActionListener(this);
        ventana.getBtnGuardarProducto().addActionListener(this);
        ventana.getBtnEliminarProducto().addActionListener(this);
        ventana.getBtnBuscarProducto().addActionListener(this);
        ventana.getTplTabMenu().addChangeListener(this);
        ventana.getTblProductos().addMouseListener(this);
        ventana.getTblFarmacias().addKeyListener(this);
        ventana.getTblProductos().addKeyListener(this);
        ventana.setVisible(true);
        buscarFarmacia();
        buscarProducto();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //System.out.println(ventana.getTplTabMenu().getSelectedIndex());
        // 0 para farmacias; 1 para productos.
        if (e.getSource().equals(ventana.getBtnBuscar())) {
            if (ventana.getTxtNit().getText().isEmpty()) {
                buscarFarmacia();
            }
            else {
                buscarFarmacia(ventana.getTxtNit().getText());
            }
        }
        
        else if (e.getSource().equals(ventana.getBtnEliminar())) {
            if (ventana.getTxtNit().getText().isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "Digite el nit de la farmacia.", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                eliminarFarmacia(ventana.getTxtNit().getText());
            }
            
        }
        
        else if (e.getSource().equals(ventana.getBtnGuardar())) {
            if (ventana.getTxtNit().isEnabled()) {
                agregarFarmacia();
            }
            else {
                editarFarmacia();
            }
        }
        
        else if (e.getSource().equals(ventana.getBtnNuevo())) {
           nuevaFarmacia(); 
        }
        
        else if (e.getSource().equals(ventana.getBtnBuscarProducto())) {
            //System.out.println("Nuevo Prod.");
            if (ventana.getTxtIdProducto().getText().isEmpty()) {
                buscarProducto();
            }
            else {
                //System.out.println("Nuevo Prod. ID");
                buscarProducto(ventana.getTxtIdProducto().getText());
            }
        }
        
        else if (e.getSource().equals(ventana.getBtnEliminarProducto())) {
            if (ventana.getTxtIdProducto().getText().isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "Digite el id del producto.", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                eliminarProducto(ventana.getTxtIdProducto().getText());
            }
        }
        
        else if (e.getSource().equals(ventana.getBtnGuardarProducto())) {
            if (ventana.getTxtIdProducto().isEnabled()) {
                agregarProducto();
            }
            else {
                editarProducto();
            }
        }
        
        else if (e.getSource().equals(ventana.getBtnNuevoProducto())) {
            nuevoProducto();
        }
    }
    
    private void agregarFarmacia()
    {
        if(validarRegistro())
        {
            Farmacia f = new Farmacia(ventana.getTxtNit().getText(), ventana.getTxtNombre().getText(), ventana.getTxtDireccion().getText(), ventana.getTxtTelefono().getText());
            if(f.guardarFarmacia())
            {
                JOptionPane.showMessageDialog(ventana, "Se ha agregado el registro correctamente.", "Registro agregado", JOptionPane.INFORMATION_MESSAGE);
                nuevaFarmacia();
                buscarFarmacia();
            }
            else
                JOptionPane.showMessageDialog(ventana, "Error al guardar el registro", "Se ha producido un error al intentar guardar el registro.", JOptionPane.ERROR_MESSAGE);            
        }
    }
    
    private void agregarProducto() {
        if (validarRegistroProducto()) {
            double valorBaseDouble = 0;
            double temperaturaDouble = 0;
            if ((ventana.getTxtValorBaseProducto().getText().isBlank()) && (ventana.getTxtTemperaturaProducto().getText().isBlank()) ) {            
                valorBaseDouble = 0;
                temperaturaDouble = 0;
            }

            else {
                valorBaseDouble = Double.parseDouble(ventana.getTxtValorBaseProducto().getText());
                temperaturaDouble = Double.parseDouble(ventana.getTxtTemperaturaProducto().getText());
            }
            Producto p = new Producto(ventana.getTxtNombreProducto().getText(), ventana.getTxtIdProducto().getText(), temperaturaDouble, valorBaseDouble);
            
            if (p.guardarProducto()) {
                JOptionPane.showMessageDialog(ventana, "Se ha agregado el registro correctamente.", "Registro agregado", JOptionPane.INFORMATION_MESSAGE);
                nuevoProducto();
                buscarProducto();
            }
        }
    }
    
    private void editarFarmacia()
    {
        if(validarRegistro())
        {
            Farmacia f = new Farmacia(ventana.getTxtNit().getText(), ventana.getTxtNombre().getText(), ventana.getTxtDireccion().getText(), ventana.getTxtTelefono().getText());
            if(f.actualizarFarmacia())
            {
                JOptionPane.showMessageDialog(ventana, "Se ha actualizado el registro correctamente.", "Registro actualizado", JOptionPane.INFORMATION_MESSAGE);
                nuevaFarmacia();
                buscarFarmacia();
            }
            else
                JOptionPane.showMessageDialog(ventana, "Se ha producido un error al intentar actualizar el registro.", "Error al actualizar el registro.", JOptionPane.ERROR_MESSAGE);        
        }    
    }
    
    private void editarProducto() {
            if (validarRegistroProducto()) {
                double valorBaseDouble = 0;
                double temperaturaDouble = 0;
                if ((ventana.getTxtValorBaseProducto().getText().isBlank()) && (ventana.getTxtTemperaturaProducto().getText().isBlank()) ) {            
                    valorBaseDouble = 0;
                    temperaturaDouble = 0;
                }

                else {
                    valorBaseDouble = Double.parseDouble(ventana.getTxtValorBaseProducto().getText());
                    temperaturaDouble = Double.parseDouble(ventana.getTxtTemperaturaProducto().getText());
                }
                Producto p = new Producto(ventana.getTxtNombreProducto().getText(), ventana.getTxtIdProducto().getText(), temperaturaDouble, valorBaseDouble);

                if (p.actualizarProducto()) {
                    JOptionPane.showMessageDialog(ventana, "Se ha actualizado el registro correctamente.", "Registro actualizado", JOptionPane.INFORMATION_MESSAGE);
                    nuevoProducto();
                    buscarProducto();
                }
                else {
                    JOptionPane.showMessageDialog(ventana, "Se ha producido un error al intentar actualizar el registro.", "Error al actualizar el registro.", JOptionPane.ERROR_MESSAGE); 
                }
            }
    }
    
    private void eliminarFarmacia(String id)
    {
        if(JOptionPane.showConfirmDialog(ventana, "¿Realmente desea eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            if(validarRegistro())
            {
                Farmacia f = new Farmacia(ventana.getTxtNit().getText(), ventana.getTxtNombre().getText(), ventana.getTxtDireccion().getText(), ventana.getTxtTelefono().getText());
                if(f.eliminarFarmacia())
                    JOptionPane.showMessageDialog(ventana,  "Se ha eliminado el registro correctamente.", "Registro eliminado", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(ventana, "Se ha producido un error al intentar eliminar el registro.", "Error al eliminar el registro.", JOptionPane.ERROR_MESSAGE);            
            }
        nuevaFarmacia();
        buscarFarmacia();
    }
    
    private void eliminarProducto(String id) {
        if (JOptionPane.showConfirmDialog(ventana, "¿Realmente desea eliminar el registro?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (validarRegistroProducto()) {
                double valorBaseDouble = 0;
                double temperaturaDouble = 0;
                if ((ventana.getTxtValorBaseProducto().getText().isBlank()) && (ventana.getTxtTemperaturaProducto().getText().isBlank()) ) {            
                    valorBaseDouble = 0;
                    temperaturaDouble = 0;
                }

                else {
                    valorBaseDouble = Double.parseDouble(ventana.getTxtValorBaseProducto().getText());
                    temperaturaDouble = Double.parseDouble(ventana.getTxtTemperaturaProducto().getText());
                }
                Producto p = new Producto(ventana.getTxtNombreProducto().getText(), ventana.getTxtIdProducto().getText(), temperaturaDouble, valorBaseDouble);

                if (p.eliminarProducto()) {
                    JOptionPane.showMessageDialog(ventana, "Se ha actualizado el registro correctamente.", "Registro eliminado", JOptionPane.INFORMATION_MESSAGE);                    
                }
                else {
                    JOptionPane.showMessageDialog(ventana, "Se ha producido un error al intentar actualizar el registro.", "Error al actualizar el registro.", JOptionPane.ERROR_MESSAGE); 
                }
            }
        }
        nuevoProducto();
        buscarProducto();
    }
      
    private void cargarDatos(List<Farmacia> farmacias) {
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
              return false;
          } 
        };
        modelo.addColumn("Nit");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
               
        
        for(Farmacia item : farmacias)
        {
            Object[] fila = new Object[4];
            fila[0] = item.getNit();
            fila[1] = item.getNombre();
            fila[2] = item.getDireccion();
            fila[3] = item.getTelefono();
            modelo.addRow(fila);
        }
                       
        ventana.getTblFarmacias().setModel(modelo);        
    }
    
    private void cargarDatosProductos(List<Producto> productos) {
        DefaultTableModel modeloProductos = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
              return false;
          } 
        };
        modeloProductos.addColumn("id");
        modeloProductos.addColumn("Nombre");
        modeloProductos.addColumn("Temperatura");
        modeloProductos.addColumn("Valor Base");
        
        for (Producto producto : productos) {
            Object[] fila = new Object[4];
            fila[0] = producto.getId();
            fila[1] = producto.getNombre();
            fila[2] = producto.getTemperatura();
            fila[3] = producto.getValorBase();
            modeloProductos.addRow(fila);            
        }
        
        ventana.getTblProductos().setModel(modeloProductos);
                
    }
    
    private void cargarCampos(Farmacia registro)
    {
        ventana.getTxtNit().setText(registro.getNit());
        ventana.getTxtNombre().setText(registro.getNombre());
        ventana.getTxtDireccion().setText(registro.getDireccion());
        ventana.getTxtTelefono().setText(registro.getTelefono());
    }

    private void cargarCampos(Object[] registro)
    {
        ventana.getTxtNit().setText(registro[0].toString());
        ventana.getTxtNombre().setText(registro[1].toString());
        ventana.getTxtDireccion().setText(registro[2].toString());
        ventana.getTxtTelefono().setText(registro[3].toString());
    }
    
    private void cargarCamposProductos(Producto registro) {
        ventana.getTxtIdProducto().setText(registro.getId());
        ventana.getTxtNombreProducto().setText(registro.getNombre());
        ventana.getTxtTemperaturaProducto().setText(Double.toString(registro.getTemperatura()));
        ventana.getTxtValorBaseProducto().setText(Double.toString(registro.getValorBase()));
    }
    
    private void cargarCamposProductos(Object[] registro) {
        ventana.getTxtIdProducto().setText(registro[0].toString());
        ventana.getTxtNombreProducto().setText(registro[1].toString());
        ventana.getTxtTemperaturaProducto().setText(registro[2].toString());
        ventana.getTxtValorBaseProducto().setText(registro[3].toString());
    }
    
    private void buscarFarmacia()
    {
        Farmacia f = new Farmacia(ventana.getTxtNit().getText(), ventana.getTxtNombre().getText(), ventana.getTxtDireccion().getText(), ventana.getTxtTelefono().getText());
        List<Farmacia> registrosObtenidos = f.listarFarmacias();   
        cargarDatos(registrosObtenidos);
    }
    
    private void buscarFarmacia(String id)
    {
        Farmacia f = new Farmacia(ventana.getTxtNit().getText(), ventana.getTxtNombre().getText(), ventana.getTxtDireccion().getText(), ventana.getTxtTelefono().getText());
        Farmacia registroObtenido = f.getFarmacia(id);
        if(registroObtenido!=null)
        {
            cargarCampos(registroObtenido);
            ventana.getTxtNit().setEnabled(false);
        }
        else
            JOptionPane.showMessageDialog(ventana, "No Encontrado", "No se ha encontrado el registro con el criterio de busqueda seleccionado.", JOptionPane.WARNING_MESSAGE);
    }
    
    private void buscarProducto() {
        //System.out.println(ventana.getTxtValorBaseProducto().getText().getClass());
        double valorBaseDouble = 0;
        double temperaturaDouble = 0;
        //System.out.println(ventana.getTxtValorBaseProducto().getText());
        if ((ventana.getTxtValorBaseProducto().getText().isBlank()) && (ventana.getTxtTemperaturaProducto().getText().isBlank()) ) {            
            valorBaseDouble = 0;
            temperaturaDouble = 0;
        }
        
        else {
            valorBaseDouble = Double.parseDouble(ventana.getTxtValorBaseProducto().getText());
            temperaturaDouble = Double.parseDouble(ventana.getTxtTemperaturaProducto().getText());
        }
        Producto p = new Producto(ventana.getTxtNombreProducto().getText(), ventana.getTxtIdProducto().getText(), temperaturaDouble, valorBaseDouble);        
        List<Producto> registrosObtenidosProductos = p.listarProductos();
        cargarDatosProductos(registrosObtenidosProductos);           
    }
    
    private void buscarProducto(String id) {
        double valorBaseDouble = 0;
        double temperaturaDouble = 0;
        if ((ventana.getTxtValorBaseProducto().getText().isBlank()) && (ventana.getTxtTemperaturaProducto().getText().isBlank()) ) {            
            valorBaseDouble = 0;
            temperaturaDouble = 0;
        }
        
        else {
            valorBaseDouble = Double.parseDouble(ventana.getTxtValorBaseProducto().getText());
            temperaturaDouble = Double.parseDouble(ventana.getTxtTemperaturaProducto().getText());
        }
        Producto p = new Producto(ventana.getTxtNombreProducto().getText(), ventana.getTxtIdProducto().getText(), temperaturaDouble, valorBaseDouble);
        Producto registroObtenidoProductos = p.getProducto(id);
        
        if(registroObtenidoProductos!=null)
        {
            cargarCamposProductos(registroObtenidoProductos);
            ventana.getTxtIdProducto().setEnabled(false);
        }
        else
            JOptionPane.showMessageDialog(ventana, "No Encontrado", "No se ha encontrado el registro con el criterio de busqueda seleccionado.", JOptionPane.WARNING_MESSAGE);
        
    }
    
    private void nuevaFarmacia()
    {
        ventana.getTxtNit().setEnabled(true);
        ventana.getTxtNit().setText("");
        ventana.getTxtNombre().setText("");
        ventana.getTxtDireccion().setText("");
        ventana.getTxtTelefono().setText("");
    }
    
    private void nuevoProducto() {
        ventana.getTxtIdProducto().setEnabled(true);
        ventana.getTxtIdProducto().setText("");
        ventana.getTxtNombreProducto().setText("");
        ventana.getTxtTemperaturaProducto().setText("");
        ventana.getTxtValorBaseProducto().setText("");
    }
    
    private boolean validarRegistro()
    {
        boolean validado = false;
        
        if(
                !ventana.getTxtNit().getText().isEmpty() &
                !ventana.getTxtNombre().getText().isEmpty() &
                !ventana.getTxtDireccion().getText().isEmpty() &
                !ventana.getTxtTelefono().getText().isEmpty()
                )
            validado = true;
        else
            JOptionPane.showMessageDialog(ventana, "Digite los campos requeridos.", "Campos vacios",  JOptionPane.INFORMATION_MESSAGE);
            
        return validado;
    }
    
    private boolean validarRegistroProducto() {
        boolean validado = false;
        if (ventana.getTxtIdProducto().getText().isEmpty() && 
            ventana.getTxtNombre().getText().isEmpty() &&
            ventana.getTxtTemperaturaProducto().getText().isEmpty() &&
            ventana.getTxtValorBaseProducto().getText().isEmpty()) 
        {
          JOptionPane.showMessageDialog(ventana, "Digite los campos requeridos.", "Campos vacios",  JOptionPane.INFORMATION_MESSAGE);           
        }
        else {
            validado = true;
        }
        return validado;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(ventana.getTblFarmacias())) {
            String nit = ventana.getTblFarmacias().getModel().getValueAt(ventana.getTblFarmacias().getSelectedRow(), 0).toString();
            ventana.getTxtNit().setText(nit);
            buscarFarmacia(nit);
        }
        
        else if (e.getSource().equals(ventana.getTblProductos())) {
            String id = ventana.getTblProductos().getModel().getValueAt(ventana.getTblProductos().getSelectedRow(), 0).toString();
            ventana.getTxtIdProducto().setText(id);
            buscarProducto(id);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //System.out.println(ventana.getTplTabMenu().getSelectedIndex());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(ventana.getTblFarmacias())) {
            String nit = ventana.getTblFarmacias().getModel().getValueAt(ventana.getTblFarmacias().getSelectedRow(), 0).toString();
            ventana.getTxtNit().setText(nit);
            buscarFarmacia(nit);
        }
        
        else if (e.getSource().equals(ventana.getTblProductos())) {
            String id = ventana.getTblProductos().getModel().getValueAt(ventana.getTblProductos().getSelectedRow(), 0).toString();
            ventana.getTxtIdProducto().setText(id);
            buscarProducto(id);
        }
    }
}
