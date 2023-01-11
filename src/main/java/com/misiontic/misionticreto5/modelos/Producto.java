/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.misiontic.misionticreto5.modelos;

import com.misiontic.misionticreto5.clases.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Cifuentes
 */
public class Producto {
    private String nombre;
    private String id;
    private double temperatura;
    private double valorBase;

    public Producto() {
    }

    public Producto(String nombre, String id, double temperatura, double valorBase) {
        this.nombre = nombre;
        this.id = id;
        this.temperatura = temperatura;
        this.valorBase = valorBase;        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }
    
    public Producto getProducto(String idProducto) {
        ConexionBD conexionProducto = new ConexionBD();
        String sqlQuery = "SELECT * FROM Productos WHERE id='" + idProducto + "';";
        try {
            ResultSet rsProducto = conexionProducto.consultarBD(sqlQuery);
            if (rsProducto.next()) {
                this.id = rsProducto.getString("id");
                this.nombre = rsProducto.getString("nombre");
                this.temperatura = Double.parseDouble(rsProducto.getString("temperatura"));
                this.valorBase = Double.parseDouble(rsProducto.getString("valorBase"));                
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            conexionProducto.cerrarConexion();
        }
        return this;
    }
    
    public List<Producto> listarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        ConexionBD conexionProducto = new ConexionBD();
        String sqlQuery = "SELECT * FROM Productos;";
        try {
            ResultSet rsProducto = conexionProducto.consultarBD(sqlQuery);
            Producto p;
            while (rsProducto.next()) {                
                p = new Producto();
                p.setId(rsProducto.getString("id"));
                p.setNombre(rsProducto.getString("nombre"));
                p.setTemperatura(Double.parseDouble(rsProducto.getString("temperatura")));
                p.setValorBase(Double.parseDouble(rsProducto.getString("valorBase")));
                listaProductos.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            conexionProducto.cerrarConexion();
        }
        return listaProductos;
    }
    
    public boolean guardarProducto() {
        ConexionBD conexionProducto = new ConexionBD();
        String sqlQuery = "INSERT INTO productos(id,nombre,temperatura,valorBase)" + 
                " VALUES('" + this.id + "', '" + this.nombre + "', '" + this.temperatura + "', '" + this.valorBase +"');";
        if (conexionProducto.setAutoCommitBD(false)) {
            if (conexionProducto.insertarBD(sqlQuery)) {
                conexionProducto.commitBD();
                conexionProducto.cerrarConexion();
                return true;
            }
            
            else {
                conexionProducto.rollbackBD();
                conexionProducto.cerrarConexion();
                return false;
            }            
        }
        else {
            conexionProducto.cerrarConexion();
            return false;
        }
    }
    
    public boolean actualizarProducto() {
        ConexionBD conexionProducto = new ConexionBD();
        String sqlQuery = "UPDATE productos SET nombre='"
                + this.nombre + "', temperatura='" + this.temperatura
                + "', valorBase='" + this.valorBase + "' WHERE id='" + this.id + "';";
        if (conexionProducto.setAutoCommitBD(false)) {
            if (conexionProducto.insertarBD(sqlQuery)) {
                conexionProducto.commitBD();
                conexionProducto.cerrarConexion();
                return true;
            }
            
            else {
                conexionProducto.rollbackBD();
                conexionProducto.cerrarConexion();
                return false;
            }            
        }
        else {
            conexionProducto.cerrarConexion();
            return false;
        }
    }
    
    public boolean eliminarProducto() {
        ConexionBD conexionProducto = new ConexionBD();
        String sqlQuery = "DELETE FROM productos WHERE id=" + this.id + ";";
        
        if (conexionProducto.setAutoCommitBD(false)) {
            if (conexionProducto.insertarBD(sqlQuery)) {
                conexionProducto.commitBD();
                conexionProducto.cerrarConexion();
                return true;
            }
            
            else {
                conexionProducto.rollbackBD();
                conexionProducto.cerrarConexion();
                return false;
            }            
        }
        else {
            conexionProducto.cerrarConexion();
            return false;
        }
    }
        
    @Override
    public String toString() {
        return this.getClass().getName() + "{" + "nombre=" + nombre + ", id=" + id + ", temperatura=" + temperatura + ", valorBase=" + valorBase + '}';
    }   
}
