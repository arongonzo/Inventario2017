
package Conecciones;

import Entidades.Unidad;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UnidadDao {
    
    Coneccion conexion = new Coneccion();

    public boolean AgregarUnidad(Unidad und) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_agregarunidad(?,?)}");
            cstm.setInt(1, und.getIdzonal());
            cstm.setString(2, und.getNombre());
                             
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateUnidad(Unidad und) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_actualizarunidad(?,?,?)}");
            cstm.setInt(1, und.getIdunidad());
            cstm.setInt(2, und.getIdunidad());
            cstm.setString(3, und.getNombre());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteUnidad(Unidad und) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_eliminarunidad(?)}");
            cstm.setInt(1, und.getIdunidad());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<Unidad> listado(String filtro) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<Unidad> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarunidad(?)}");
            cstm.setString(1, filtro);
            rs = cstm.executeQuery();
            Unidad Und = null;
            while (rs.next()) {
                
                Und = new Unidad();
                Und.setIdUnidad(rs.getInt("id_unidad"));
                Und.setIdzonal(rs.getInt("id_zonal"));     
                Und.setNombreZonal(rs.getString("nombre_zonal"));       
                Und.setNombre(rs.getString("nombre_unidad"));
                
                lista.add(Und);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar2(cstm, rs);
        }
        return lista;
    }
    
}
