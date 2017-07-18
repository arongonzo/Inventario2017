package Conecciones;

import Entidades.SolicitudRepuestoDetalle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SolicitudRepuestoDetalleDao {
    
    Coneccion conexion = new Coneccion();

    public boolean AgregarRepuestoDetalle(SolicitudRepuestoDetalle Srd) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_AgregarSolicitudRepuestoDetalle(?,?,?)}");
            cstm.setInt(1, Srd.getIdSolicitudRepuesto());
            cstm.setInt(2, Srd.getIdProducto());
            cstm.setInt(3, Srd.getCantidad());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateRepuestoDetalle(SolicitudRepuestoDetalle Srd) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_ActualizarSolicitudRepuestoDetalle(?,?,?,?)}");
            cstm.setInt(1, Srd.getIdRepuestoDetalle());
            cstm.setInt(2, Srd.getIdSolicitudRepuesto());
            cstm.setInt(3, Srd.getIdProducto());
            cstm.setInt(4, Srd.getCantidad());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteRepuestoDetalle(SolicitudRepuestoDetalle Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_DeleteSolicitudRepuestoDetalle(?)}");
            cstm.setInt(1, Pgr.getIdRepuestoDetalle());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<SolicitudRepuestoDetalle> listado(int id_SolicitudRepuesto) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<SolicitudRepuestoDetalle> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
                cstm = con.prepareCall("{Call pa_ListarSolicitudRepuestoDetalle(?)}");
            cstm.setInt(1, id_SolicitudRepuesto);
            
            
            rs = cstm.executeQuery();
            SolicitudRepuestoDetalle Srd = null;
            while (rs.next()) {
                
                Srd = new SolicitudRepuestoDetalle();
                
                Srd.setIdRepuestoDetalle(rs.getInt("id_detallerepuesto"));
                Srd.setIdSolicitudRepuesto(rs.getInt("id_solicitudrespuesto"));
                Srd.setIdProducto(rs.getInt("id_producto"));
                Srd.setCantidad(rs.getInt("cantidad"));

                Srd.setNsn(rs.getString("nsn_producto"));
                Srd.setProducto(rs.getString("nombre"));
                Srd.setDescripcion(rs.getString("descripcion_producto"));
                Srd.setNumeroparte(rs.getString("n_parte"));
                Srd.setCantidadDisponible(rs.getInt("disponible"));
                
                
                lista.add(Srd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar2(cstm, rs);
        }
        return lista;
    }
    
}
