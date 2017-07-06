package Conecciones;

import Entidades.Sistema;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SistemaDao {
    Coneccion conexion = new Coneccion();

    public boolean AgregarSistema(Sistema sis) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_agregarsistema(?,?)}");
            cstm.setString(1, sis.getNombre_Sistema());
            cstm.setString(2, sis.getDescripcion_Sistema());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateSistema(Sistema sis) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_actualizarsistema(?,?,?)}");
            cstm.setInt(1, sis.getIdsistema());
            cstm.setString(2, sis.getNombre_Sistema());
            cstm.setString(3, sis.getDescripcion_Sistema());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteSistema(Sistema sis) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_eliminarsistema(?)}");
            cstm.setInt(1, sis.getIdsistema());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<Sistema> listado(String filtro) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<Sistema> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarsistema(?)}");
            cstm.setString(1, filtro);
            rs = cstm.executeQuery();
            Sistema sis = null;
            while (rs.next()) {
                sis = new Sistema();
                sis.setIdsistema(rs.getInt("id_sistema"));
                sis.setNombre_Sistema(rs.getString("nombre_sistema"));       
                sis.setDescripcion_Sistema(rs.getString("descripcion_sistema")); 
                lista.add(sis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar2(cstm, rs);
        }
        return lista;
    }
    
}
