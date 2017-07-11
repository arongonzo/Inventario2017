
package Conecciones;

import Entidades.CondicionSistema;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CondicionSistemaDao {
    
    Coneccion conexion = new Coneccion();

    public boolean AgregarCondicionSistema(CondicionSistema sis) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_agregarcondicondicionsistema(?,?)}");
            cstm.setString(1, sis.getNombreCondicionSistema());
            cstm.setString(2, sis.getDescripcionCondicionSistema());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateCondicionSistema(CondicionSistema sis) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_actualizarCondicionsistema(?,?,?)}");
            cstm.setInt(1, sis.getIdCondicionSistema());
            cstm.setString(2, sis.getNombreCondicionSistema());
            cstm.setString(3, sis.getDescripcionCondicionSistema());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteCondicionSistema(CondicionSistema sis) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_eliminarcondicionsistema(?)}");
            cstm.setInt(1, sis.getIdCondicionSistema());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<CondicionSistema> listado(String filtro) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<CondicionSistema> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarcondicionsistema(?)}");
            cstm.setString(1, filtro);
            rs = cstm.executeQuery();
            CondicionSistema sis = null;
            while (rs.next()) {
                sis = new CondicionSistema();
                sis.setIdCondicionSistema(rs.getInt("id_condicionsistema"));
                sis.setNombreCondicionSistema(rs.getString("nombre_condicionsistema"));       
                sis.setDescripcionCondicionSistema(rs.getString("descripcion_condicionsistema")); 
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
