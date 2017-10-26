package Conecciones;

import Entidades.Producto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {
    
    Coneccion conexion = new Coneccion();

    public boolean AgregarProducto(Producto Prd) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true; 
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_agregarproducto(?,?,?,?,?,?,?,?)}");
            cstm.setString(1, Prd.getNsn());
            cstm.setString(2, Prd.getNombre());
            cstm.setInt(3, Prd.getCantidad());
            cstm.setString(4, Prd.getDescripcion());
            cstm.setString(5, Prd.getN_parte());
            cstm.setDouble(6, Prd.getValor_unitario());            
            cstm.setString(7, Prd.getUbicacion());
            cstm.setString(8, Prd.getUbicacion_detalle());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean UpdateProducto(Producto Prd) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_actualizarproducto(?,?,?,?,?,?,?,?,?)}");
            cstm.setInt(1, Prd.getIdproducto());
            cstm.setString(2, Prd.getNsn());
            cstm.setString(3, Prd.getNombre());
            cstm.setInt(4, Prd.getCantidad());
            cstm.setString(5, Prd.getDescripcion());
            cstm.setString(6, Prd.getN_parte());
            cstm.setDouble(7, Prd.getValor_unitario());            
            cstm.setString(8, Prd.getUbicacion());
            cstm.setString(9, Prd.getUbicacion_detalle());
            
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public boolean DeleteProducto(Producto Prd) {
        Connection con = null;
        CallableStatement cstm = null;
        boolean resp = true;
        try {
            con = conexion.getConection();
            con.setAutoCommit(false);
            cstm = con.prepareCall("{Call pa_DeleteProducto(?)}");
            cstm.setInt(1, Prd.getIdproducto());
            resp = cstm.execute();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrar1(con, cstm);
        }
        return resp;
    }

    public List<Producto> listado(int id_producto, String nsn, String nParte , String nombre) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<Producto> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_listarproducto(?,?,?,?)}");
            cstm.setInt(1, id_producto);
            cstm.setString(2, nsn);
            cstm.setString(3, nParte);
            cstm.setString(4, nombre);
            
            rs = cstm.executeQuery();
            Producto Prd = null;
            while (rs.next()) {
                
                Prd = new Producto();
                Prd.setIdproducto(rs.getInt("id_producto"));
                Prd.setNsn(rs.getString("nsn_producto"));
                Prd.setNombre(rs.getString("nombre"));
                Prd.setCantidad(rs.getInt("cantidad"));
                Prd.setDescripcion(rs.getString("descripcion_producto"));
                Prd.setN_parte(rs.getString("n_parte"));
                Prd.setValor_unitario(rs.getDouble("valor_unitario"));
                Prd.setUbicacion(rs.getString("ubicacion"));
                Prd.setUbicacion_detalle(rs.getString("ubicacion_detalle"));
                Prd.setAnio_ingreso(rs.getString("anio_Ingreso"));
                Prd.setAnio_salida(rs.getString("anio_salida"));
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
