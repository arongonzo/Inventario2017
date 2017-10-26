package Conecciones;

import Entidades.View_ReporteSolicitudProgramadaAnual;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class View_ReporteSolicitudProgramadaAnualDao {
    Coneccion conexion = new Coneccion();

    public int AgregarProgramada(View_ReporteSolicitudProgramadaAnual Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        int myIdentVal = -1; //to store the @@IDENTITY
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            
            ResultSet rs = null;
            List<View_ReporteSolicitudProgramadaAnual> lista = null;
            
            cstm = con.prepareCall("{Call pa_AgregarSolicitudProgramada(?,?,?,?,?,?,?,?,?)}");
            cstm.setInt(1, Pgr.getIdProgramada());
            cstm.setInt(2, Pgr.getIdSistema());
            cstm.setInt(3, Pgr.getIdUnidad());
            cstm.setInt(4, Pgr.getIdUsuario());
            cstm.setString(5, Pgr.getpista());
            cstm.setString(6, Pgr.getmarca());
            cstm.setString(7, Pgr.getmodelo());
            cstm.setDate(8, Pgr.getfecha());
            cstm.setInt(9, Pgr.getEstado());
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

    public boolean UpdateProgramada(View_ReporteSolicitudProgramadaAnual Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_ActualizarSolicitudProgramada(?,?,?,?,?,?,?,?,?)}");
            cstm.setInt(1, Pgr.getIdProgramada());
            cstm.setInt(2, Pgr.getIdSistema());
            cstm.setInt(3, Pgr.getIdUnidad());
            cstm.setInt(4, Pgr.getIdUsuario());
            cstm.setString(5, Pgr.getpista());
            cstm.setString(6, Pgr.getmarca());
            cstm.setString(7, Pgr.getmodelo());
            cstm.setDate(8, Pgr.getfecha());
            cstm.setInt(9, Pgr.getEstado());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
            return resp;
    }

    public boolean DeleteProgramada(View_ReporteSolicitudProgramadaAnual Pgr) {
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

    public List<View_ReporteSolicitudProgramadaAnual> listado(int id_sistema, int id_unidad,String fecha) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<View_ReporteSolicitudProgramadaAnual> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarproducto(?,?,?,?)}");
            cstm.setInt(1, id_sistema);
            cstm.setInt(2, id_unidad);
            
            rs = cstm.executeQuery();
            View_ReporteSolicitudProgramadaAnual Prd = null;
            while (rs.next()) {
                
                Prd = new View_ReporteSolicitudProgramadaAnual();
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
