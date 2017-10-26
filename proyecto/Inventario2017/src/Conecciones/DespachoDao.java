package Conecciones;

import Entidades.Despacho;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DespachoDao {
    
        Coneccion conexion = new Coneccion();

    public int AgregarDespacho(Despacho Srp) {
        Connection con = null;
        CallableStatement cstm = null;
        int myIdentVal = -1; //to store the @@IDENTITY
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            
            ResultSet rs = null;
            List<Despacho> lista = null;
            
            cstm = con.prepareCall("{Call pa_AgregarDespacho(?,?,?,?,?,?,?,?,?,?,?,?)}");
            cstm.setInt(1, Srp.getIdDespacho());
            cstm.setInt(2, Srp.getIdTemporada());
            cstm.setString(3, Srp.getIdComercial());
            cstm.setInt(4, Srp.getIdUsuario());
            cstm.setInt(5, Srp.getIdUnidad());
            cstm.setInt(6, Srp.getIdSistema());
            cstm.setDate(7, Srp.getFechaCreacion());
            cstm.setInt(8, Srp.getIdPrioridad());
            cstm.setString(9, Srp.getNombreRecibe());
            cstm.setString(10, Srp.getCorreoRecibe());
            cstm.setString(11, Srp.getAnexoRecibe());
            cstm.setInt(12, Srp.getEstado());
            
            resp = cstm.execute();
            
            int iUpdCount = cstm.getUpdateCount();
            boolean bMoreResults = true;
            
            while (bMoreResults || iUpdCount!=-1)
            {           
                rs = cstm.getResultSet();                   
                if (rs != null)
                {
                    rs.next();
                    myIdentVal = rs.getInt(1);
                }                   
                bMoreResults = cstm.getMoreResults();
                iUpdCount = cstm.getUpdateCount();
            }
            cstm.close();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return myIdentVal;
    }

    public boolean UpdateDespacho(Despacho Srp) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            
            cstm = con.prepareCall("{Call pa_AgregarDespacho(?,?,?,?,?,?,?,?,?,?,?,?)}");
            cstm.setInt(1, Srp.getIdDespacho());
            cstm.setInt(2, Srp.getIdTemporada());
            cstm.setString(3, Srp.getIdComercial());
            cstm.setInt(4, Srp.getIdUsuario());
            cstm.setInt(5, Srp.getIdUnidad());
            cstm.setInt(6, Srp.getIdSistema());
            cstm.setDate(7, Srp.getFechaCreacion());
            cstm.setInt(8, Srp.getIdPrioridad());
            cstm.setString(9, Srp.getNombreRecibe());
            cstm.setString(10, Srp.getCorreoRecibe());
            cstm.setString(11, Srp.getAnexoRecibe());
            cstm.setInt(12, Srp.getEstado());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteDespacho(Despacho Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_DeleteDespacho(?)}");
            cstm.setInt(1, Pgr.getIdDespacho());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<Despacho> listado(int id_sistema, int id_unidad,String fecha) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<Despacho> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarDespacho(?,?,?,?)}");
            cstm.setInt(1, id_sistema);
            cstm.setInt(2, id_unidad);
            
            rs = cstm.executeQuery();
            Despacho Prd = null;
            while (rs.next()) {
                
                Prd = new Despacho();
                Prd.setIdSistema(rs.getInt("id_sistema"));
                Prd.setEstado(rs.getInt("estado"));
                
                lista.add(Prd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar2(cstm, rs);
        }
        return lista;
    }
    
    public boolean DescontarProductos(int id_despacho) {
        Connection con = null;
        CallableStatement cstm = null;
        int myIdentVal = -1; //to store the @@IDENTITY
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            
            ResultSet rs = null;
            List<Despacho> lista = null;
            
            cstm = con.prepareCall("{Call pa_DespachoDescontarProducto(?)}");
            cstm.setInt(1, id_despacho);
            
            resp = cstm.execute();
            con.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }
    
}
