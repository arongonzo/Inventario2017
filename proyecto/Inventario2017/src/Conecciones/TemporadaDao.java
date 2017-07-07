package Conecciones;

import Entidades.Temporada;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TemporadaDao {
    Coneccion conexion = new Coneccion();

    public boolean AgregarTemporada(Temporada Tem) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_agregartemporada(?, ?)}");
            cstm.setString(1, Tem.getNombreTemporada());
            cstm.setInt(2, Tem.getActiva());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateTemporada(Temporada Tem) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_actualizartemporada(?,?,?)}");
            cstm.setInt(1, Tem.getIdTemporada());
            cstm.setString(2, Tem.getNombreTemporada());
            cstm.setInt(3, Tem.getActiva());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteTemporada(Temporada Tem) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_eliminartemporada(?)}");
            cstm.setInt(1, Tem.getIdTemporada());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<Temporada> listado() {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<Temporada> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listartemporada}");
            rs = cstm.executeQuery();
            Temporada Tem = null;
            while (rs.next()) {
                Tem = new Temporada();
                Tem.setIdTemporada(rs.getInt("id_temporada"));
                Tem.setNombreTemporada(rs.getString("nombre_temporada"));       
                Tem.setActiva(rs.getInt("activa")); 
                Tem.setActivaTexto(rs.getString("activatexto")); 
                lista.add(Tem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar2(cstm, rs);
        }
        return lista;
    }
}
