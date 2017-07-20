package Conecciones;

import Entidades.SolicitudRepuesto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SolicitudRepuestoDao {
    
    Coneccion conexion = new Coneccion();

    public int AgregarRepuesto(SolicitudRepuesto Srp) {
        Connection con = null;
        CallableStatement cstm = null;
        int myIdentVal = -1; //to store the @@IDENTITY
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            
            ResultSet rs = null;
            List<SolicitudRepuesto> lista = null;
            
            cstm = con.prepareCall("{Call pa_AgregarSolicitudRespuesto(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cstm.setInt(1, Srp.getIdSolicitudRepuesto());
            cstm.setInt(2, Srp.getIdTemporada());
            cstm.setString(3, Srp.getIdComercial());
            cstm.setInt(4, Srp.getIdUnidad());
            cstm.setInt(5, Srp.getIdUsuario());
            cstm.setDate(6, Srp.getFechaCreacion());
            cstm.setString(7, Srp.getNombreTecnico());
            cstm.setString(8, Srp.getCorreoElectronico());
            cstm.setString(9, Srp.getAnexo());
            cstm.setInt(10, Srp.getIdSistema());
            cstm.setString(11, Srp.getMarcaEquipo());
            cstm.setInt(12, Srp.getIdCondicionSistema());
            cstm.setString(13, Srp.getModeloEquipo());
            cstm.setString(14, Srp.getNumeroSerie());
            cstm.setInt(15, Srp.getIdFallaComponente());
            cstm.setString(16, Srp.getFallaComponente());
            cstm.setString(17, Srp.getSintomaFalla());
            cstm.setString(18, Srp.getSolicutusOrdenTrabajo());
            cstm.setDate(19, Srp.getFechaSolicitud());
            cstm.setInt(20, Srp.getIdPrioridad());
            cstm.setString(21, Srp.getDetallePrioridad());
            cstm.setInt(22, Srp.getEstado());
            
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

    public boolean UpdateRepuesto(SolicitudRepuesto Srp) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            
            cstm = con.prepareCall("{Call pa_AgregarSolicitudRespuesto(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cstm.setInt(1, Srp.getIdSolicitudRepuesto());
            cstm.setInt(2, Srp.getIdTemporada());
            cstm.setString(3, Srp.getIdComercial());
            cstm.setInt(4, Srp.getIdUnidad());
            cstm.setInt(5, Srp.getIdUsuario());
            cstm.setDate(6, Srp.getFechaCreacion());
            cstm.setString(7, Srp.getNombreTecnico());
            cstm.setString(8, Srp.getCorreoElectronico());
            cstm.setString(9, Srp.getAnexo());
            cstm.setInt(10, Srp.getIdSistema());
            cstm.setString(11, Srp.getMarcaEquipo());
            cstm.setInt(12, Srp.getIdCondicionSistema());
            cstm.setString(13, Srp.getModeloEquipo());
            cstm.setString(14, Srp.getNumeroSerie());
            cstm.setInt(15, Srp.getIdFallaComponente());
            cstm.setString(16, Srp.getFallaComponente());
            cstm.setString(17, Srp.getSintomaFalla());
            cstm.setString(18, Srp.getSolicutusOrdenTrabajo());
            cstm.setDate(19, Srp.getFechaSolicitud());
            cstm.setInt(20, Srp.getIdPrioridad());
            cstm.setString(21, Srp.getDetallePrioridad());
            cstm.setInt(22, Srp.getEstado());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteRepuesto(SolicitudRepuesto Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_DeleteSolicitudProgramada(?)}");
            cstm.setInt(1, Pgr.getIdSolicitudRepuesto());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<SolicitudRepuesto> listado(int id_sistema, int id_unidad,String fecha) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<SolicitudRepuesto> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarproducto(?,?,?,?)}");
            cstm.setInt(1, id_sistema);
            cstm.setInt(2, id_unidad);
            
            rs = cstm.executeQuery();
            SolicitudRepuesto Prd = null;
            while (rs.next()) {
                
                Prd = new SolicitudRepuesto();
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
