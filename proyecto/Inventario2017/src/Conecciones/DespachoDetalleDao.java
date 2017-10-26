
package Conecciones;

import Entidades.DespachoDetalle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DespachoDetalleDao {
    
    Coneccion conexion = new Coneccion();

    public boolean AgregarDespachoDetalle(DespachoDetalle Srd) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_AgregarDespachoDetalle(?,?,?)}");
            cstm.setInt(1, Srd.getIdDespacho());
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

    public boolean UpdateDespachoDetalle(DespachoDetalle Srd) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_ActualizarDespachoDetalle(?,?,?,?,?)}");
            cstm.setInt(1, Srd.getIdDetalleDespacho());
            cstm.setInt(2, Srd.getIdDespacho());
            cstm.setInt(3, Srd.getIdProducto());
            cstm.setInt(4, Srd.getCantidad());
            cstm.setInt(5, Srd.getPrecio());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteDespachoDetalle(DespachoDetalle Pgr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_DeleteDespachoDetalle(?)}");
            cstm.setInt(1, Pgr.getIdDetalleDespacho());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<DespachoDetalle> listado(int id_Despacho) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<DespachoDetalle> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
                cstm = con.prepareCall("{Call pa_ListarDespachoDetalle(?)}");
            cstm.setInt(1, id_Despacho);
            
            
            rs = cstm.executeQuery();
            DespachoDetalle Srd = null;
            while (rs.next()) {
                
                Srd = new DespachoDetalle();
                
                Srd.setIdDetalleDespacho(rs.getInt("id_despachodetalle"));
                Srd.setIdDespacho(rs.getInt("id_despacho"));
                Srd.setIdProducto(rs.getInt("id_producto"));
                Srd.setCantidad(rs.getInt("cantidad"));
                Srd.setPrecio(rs.getInt("precio"));    
                
                Srd.setNsn(rs.getString("nsn_producto"));
                Srd.setProducto(rs.getString("nombre"));
                Srd.setDescripcion(rs.getString("descripcion_producto"));
                Srd.setNumeroparte(rs.getString("n_parte"));
                Srd.setCantidadDisponible(rs.getInt("solicitada"));
                Srd.setCantidadDespachada(rs.getInt("entregada"));
                
                
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
