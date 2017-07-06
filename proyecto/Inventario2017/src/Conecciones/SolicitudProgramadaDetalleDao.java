
package Conecciones;

import Entidades.SolicitudProgramadaDetalle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SolicitudProgramadaDetalleDao {
    
    Coneccion conexion = new Coneccion();

    public boolean AgregarProgramadaDetalle(SolicitudProgramadaDetalle Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_AgregarSolicitudProgramadaDetalle(?,?,?)}");
            cstm.setInt(1, Pgr.getIdProgramada());
            cstm.setInt(2, Pgr.getIdProducto());
            cstm.setInt(3, Pgr.getCantidad());
            
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateProgramadaDetalle(SolicitudProgramadaDetalle Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_ActualizarSolicitudProgramaDetalle(?,?,?,?)}");
            cstm.setInt(1, Pgr.getIdProgramadaDetalle());
            cstm.setInt(2, Pgr.getIdProgramada());
            cstm.setInt(3, Pgr.getIdProducto());
            cstm.setInt(4, Pgr.getCantidad());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteProgramadaDetalle(SolicitudProgramadaDetalle Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_DeleteSolicitudProgramadaDetalle(?)}");
            cstm.setInt(1, Pgr.getIdProgramadaDetalle());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<SolicitudProgramadaDetalle> listado(int id_programada) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<SolicitudProgramadaDetalle> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_ListarSolicitudProgramadaDetalle(?)}");
            cstm.setInt(1, id_programada);
            
            
            rs = cstm.executeQuery();
            SolicitudProgramadaDetalle Pgr = null;
            while (rs.next()) {
                
                Pgr = new SolicitudProgramadaDetalle();
                
                Pgr.setIdProgramadaDetalle(rs.getInt("id_detalleprogramada"));
                Pgr.setIdProgramada(rs.getInt("id_programada"));
                Pgr.setIdProducto(rs.getInt("id_producto"));
                Pgr.setCantidad(rs.getInt("cantidad"));

                Pgr.setNsn(rs.getString("nsn_producto"));
                Pgr.setProducto(rs.getString("nombre"));
                Pgr.setDescripcion(rs.getString("descripcion_producto"));
                Pgr.setNumeroparte(rs.getString("n_parte"));
                Pgr.setCantidadDisponible(rs.getInt("disponible"));
                
                
                lista.add(Pgr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar2(cstm, rs);
        }
        return lista;
    }
}
