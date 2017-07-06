package Conecciones;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;

public class Coneccion {
    
    static Connection con = null;
    
    public static Connection getConection()
    {
        String url = "jdbc:sqlserver://168.232.165.151;databaseName=DB_Inventario2017";
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
        } 
        catch (ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,"No se pudo establecer la conexion... revisar driver " + e.getMessage() 
                    , "Error de Conecxion", JOptionPane.ERROR_MESSAGE ); 
        }
        try
        {
            con = DriverManager.getConnection(url, "sa","7XHTatPlhu24L89O0g");
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error " + e.getMessage() 
                    , "Error de Conecxion", JOptionPane.ERROR_MESSAGE ); 
        }
        return con;
    }
    
    public static void Cerrar1(Connection con, CallableStatement cstm) {
        try {
            if (con != null) {
                con.close();
            }
            if (cstm != null) {
                cstm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Cerrar2(CallableStatement cstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (cstm != null) {
                cstm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ResultSet consulta(String consulta)
    {
        Connection con = getConection();
        Statement declare;
        try
        {
            declare = con.createStatement();
            ResultSet respuesta = declare.executeQuery(consulta);
            return respuesta;
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error " + e.getMessage() 
                    , "Error de Conecxion", JOptionPane.ERROR_MESSAGE );
                    
        }
        return null;
    }
    
    
}   
