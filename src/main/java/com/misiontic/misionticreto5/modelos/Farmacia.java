/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class Farmacia {
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;

    public Farmacia(){
    }
    
    public Farmacia(String nit, String nombre, String direccion, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public Farmacia getFarmacia(String id) {
        ConexionBD conexion = new ConexionBD();
        String sql = "select * from farmacias WHERE nit="+id+";";
        try {
            ResultSet rs = conexion.consultarBD(sql);
            if (rs.next()) {
                this.nit = rs.getString("nit");
                this.nombre = rs.getString("nombre");
                this.direccion = rs.getString("direccion");
                this.telefono = rs.getString("telefono");
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return this;
    }

    public List<Farmacia> listarFarmacias() {
        List<Farmacia> listaFarmacias = new ArrayList<>();
        ConexionBD conexion = new ConexionBD();
        String sql = "select * from farmacias;";
        try {
            ResultSet rs = conexion.consultarBD(sql);
            Farmacia p;
            while (rs.next()) {
                p = new Farmacia();
                p.setNit(rs.getString("nit"));
                p.setNombre(rs.getString("nombre"));
                p.setDireccion(rs.getString("direccion"));
                p.setTelefono(rs.getString("telefono"));
                listaFarmacias.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return listaFarmacias;
    }

    public boolean guardarFarmacia() {
        ConexionBD conexion = new ConexionBD();
        String sql = "INSERT INTO farmacias(nit,nombre,direccion,telefono)"
                + "VALUES("+this.nit+",'" + this.nombre + "','" + this.direccion + "'," + this.telefono + ");";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.insertarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean actualizarFarmacia() {
        ConexionBD conexion = new ConexionBD();
        String sql = "UPDATE farmacias SET nombre='"
                + this.nombre + "',direccion='" + this.direccion
                + "',telefono='" + this.telefono + "' WHERE nit='" + this.nit + "';";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean eliminarFarmacia() {
        ConexionBD conexion = new ConexionBD();
        String sql = "DELETE FROM farmacias WHERE nit=" + this.nit + ";";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(sql)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }
    
}
