package com.misiontic.misionticreto5.clases;


import java.sql.*;
import java.util.logging.*;
import org.sqlite.JDBC;

public class ConexionBD {
// Configuracion de la conexion a la base de datos
    
    // Se debe cambiar el url según la ubicación de la base de datos
    private String url = "C:\\Reto5.db";
    public Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
//Constructor sin parmetros

    public ConexionBD() {
        try {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:" + url);
                if (con != null) {
                    System.out.println("Conectado");
                }
            } catch (SQLException ex) {
                System.err.println("No se ha podido conectar a la base de datos\n" + ex.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println("El JAR no está correctamente agregado\n"
                  + e.getMessage());
            }
    }
//Retornar la conexión

    public Connection getConnection() {
        return con;
    }
//Cerrar la conexin

    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
// Mtodo que devuelve un ResultSet de una consulta (tratamiento de SELECT)

    public ResultSet consultarBD(String sentencia) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sentencia);
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }
// Metodo que realiza un INSERT y devuelve TRUE si la operacin fue existosa

    public boolean createBD() {
        try {
            String sentencia = "CREATE TABLE IF NOT EXISTS 'Farmacias' (\n"+
                                    "'nit'	TEXT NOT NULL,\n"+
                                    "'nombre'	TEXT NOT NULL,"+
                                    "'direccion'	TEXT NOT NULL,\n"+
                                    "'telefono'	TEXT NOT NULL,\n"+
                                    "PRIMARY KEY('nit')\n"+");\n"+
                                "CREATE TABLE IF NOT EXISTS 'Productos' (\n"+
                                    "'nombre'	TEXT NOT NULL,\n"+
                                    "'id'	INTEGER NOT NULL,"+
                                    "'temperatura'	REAL NOT NULL,\n"+
                                    "'valorBase'	REAL NOT NULL,\n"+
                                    "PRIMARY KEY('id')\n"+");\n";
            stmt = con.createStatement();
            stmt.execute(sentencia);
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }    
    
    public boolean insertarBD(String sentencia) {
        try {
            stmt = con.createStatement();
            stmt.execute(sentencia);
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }

    public boolean borrarBD(String sentencia) {
        try {
            stmt = con.createStatement();
            stmt.execute(sentencia);
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }
// Mtodo que realiza una operacin como UPDATE, DELETE, CREATE TABLE, entre otras
// y devuelve TRUE si la operacin fue existosa

    public boolean actualizarBD(String sentencia) {
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sentencia);
        } catch (SQLException | RuntimeException sqlex) {
            System.out.println("ERROR RUTINA: " + sqlex);
            return false;
        }
        return true;
    }

    public boolean setAutoCommitBD(boolean parametro) {
        try {
            con.setAutoCommit(parametro);
        } catch (SQLException sqlex) {
            System.out.println("Error al configurar el autoCommit " + sqlex.getMessage());
            return false;
        }
        return true;
    }

    public void cerrarConexion() {
        closeConnection(con);
    }

    public boolean commitBD() {
        try {
            con.commit();
            return true;
        } catch (SQLException sqlex) {
            System.out.println("Error al hacer commit " + sqlex.getMessage());
            return false;
        }
    }

    public boolean rollbackBD() {
        try {
            con.rollback();
            return true;
        } catch (SQLException sqlex) {
            System.out.println("Error al hacer rollback " + sqlex.getMessage());
            return false;
        }
    }
    
//    public static void main(String[] args) {
//        ConexionBD c= new ConexionBD();        
//    }
}
