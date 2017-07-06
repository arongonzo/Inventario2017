package Conecciones;

import Entidades.SolicitudProgramada;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SolicitudProgramadaDao {
    
    Coneccion conexion = new Coneccion();

    public int AgregarProgramada(SolicitudProgramada Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        int myIdentVal = -1; //to store the @@IDENTITY
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            
            ResultSet rs = null;
            List<SolicitudProgramada> lista = null;
            
            cstm = con.prepareCall("{Call pa_AgregarSolicitudProgramada(?,?,?,?,?,?,?)}");
            cstm.setInt(1, Pgr.getIdSistema());
            cstm.setInt(2, Pgr.getIdUnidad());
            cstm.setString(3, Pgr.getpista());
            cstm.setString(4, Pgr.getmarca());
            cstm.setString(5, Pgr.getmodelo());
            cstm.setString(6, Pgr.getfecha());
            cstm.setInt(7, Pgr.getEstado());
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

    public boolean UpdateProgramada(SolicitudProgramada Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_ActualizarSolicitudProgramada(?,?,?,?,?,?,?,?)}");
            cstm.setInt(1, Pgr.getIdProgramada());    
            cstm.setInt(2, Pgr.getIdSistema());
            cstm.setInt(3, Pgr.getIdUnidad());
            cstm.setString(4, Pgr.getpista());
            cstm.setString(5, Pgr.getmarca());
            cstm.setString(6, Pgr.getmodelo());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteProgramada(SolicitudProgramada Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_DeleteSolicitudProgramada(?)}");
            cstm.setInt(1, Pgr.getIdProgramada());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<SolicitudProgramada> listado(int id_sistema, int id_unidad,String fecha) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<SolicitudProgramada> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarproducto(?,?,?,?)}");
            cstm.setInt(1, id_sistema);
            cstm.setInt(2, id_unidad);
            
            rs = cstm.executeQuery();
            SolicitudProgramada Prd = null;
            while (rs.next()) {
                
                Prd = new SolicitudProgramada();
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
    
}