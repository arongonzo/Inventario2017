package Conecciones;

import Entidades.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    
    Coneccion conexion = new Coneccion();

    public boolean AgregarUsuario(Usuario Usr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_agregarUsuario(?,?,?,?,?)}");
            cstm.setString(1, Usr.getUserName());
            cstm.setString(2, Usr.getClave());
            cstm.setString(3, Usr.getNombre());
            cstm.setString(4, Usr.getApellido());
            cstm.setInt(5, Usr.getid_rol());            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateUsuario(Usuario Usr) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_updateUsuario(?,?,?,?,?,?)}");
            cstm.setInt(1, Usr.getIdusuario());
            cstm.setString(2, Usr.getUserName());
            cstm.setString(3, Usr.getClave());
            cstm.setString(4, Usr.getNombre());
            cstm.setString(5, Usr.getApellido());
            cstm.setInt(6, Usr.getid_rol());   
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteUsuario(Usuario per) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_DeleteUsuario(?)}");
            cstm.setInt(1, per.getIdusuario());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<Usuario> listado(String filtro) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<Usuario> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_ListarUsuario(?)}");
            cstm.setString(1, filtro);
            rs = cstm.executeQuery();
            Usuario Usr = null;
            while (rs.next()) {
                Usr = new Usuario();
                Usr.setIdUsuario(rs.getInt("id_usuario"));
                Usr.setUserName(rs.getString("username"));       
                Usr.setClave(rs.getString("clave"));
                Usr.setNombre(rs.getString("nombre"));
                Usr.setApellido(rs.getString("apellido"));
                Usr.setid_Rol(rs.getInt("id_rol"));
                Usr.setRolname(rs.getString("Nombre_rol"));
                lista.add(Usr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar2(cstm, rs);
        }
        return lista;
    }
}
