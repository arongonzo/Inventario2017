
package Conecciones;

import Entidades.Zonal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ZonalDao {
    Coneccion conexion = new Coneccion();

    public boolean AgregarZonal(Zonal Znl) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_agregarzonal(?,?)}");
            cstm.setString(1, Znl.getNombre_Zonal());
            cstm.setString(2, Znl.getDescripcion_Zonal());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateZonal(Zonal Znl) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_actualizarzonal(?,?,?)}");
            cstm.setInt(1, Znl.getIdzonal());
            cstm.setString(2, Znl.getNombre_Zonal());
            cstm.setString(3, Znl.getDescripcion_Zonal());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteZonal(Zonal Znl) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_eliminarzonal(?)}");
            cstm.setInt(1, Znl.getIdzonal());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<Zonal> listado(String filtro) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<Zonal> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarzonal(?)}");
            cstm.setString(1, filtro);
            rs = cstm.executeQuery();
            Zonal Znl = null;
            while (rs.next()) {
                Znl = new Zonal();
                Znl.setIdzonal(rs.getInt("id_zonal"));
                Znl.setZonal(rs.getString("nombre_zonal"));       
                Znl.setDescripcion_Zonal(rs.getString("descripcion_zonal")); 
                lista.add(Znl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar2(cstm, rs);
        }
        return lista;
    }
}
