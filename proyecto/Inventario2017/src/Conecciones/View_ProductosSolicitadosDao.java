package Conecciones;

import Entidades.View_ProductosSolicitados;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class View_ProductosSolicitadosDao {
    
    Coneccion conexion = new Coneccion();
    
    public List<View_ProductosSolicitados> listado(int id_unidad, int id_sistema,int id_producto,String nsn,String numeroparte,String producto ) {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<View_ProductosSolicitados> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_ListarSolicitudProgramada_productos(?,?,?,?,?,?)}");
            cstm.setInt(1, id_sistema);
            cstm.setInt(2, id_unidad);
            cstm.setInt(3, id_producto);
            cstm.setString(4, nsn);
            cstm.setString(5, numeroparte);
            cstm.setString(6, producto);
            
            rs = cstm.executeQuery();
            View_ProductosSolicitados Pgr = null;
            while (rs.next()) {
                
                Pgr = new View_ProductosSolicitados();
                
                Pgr.setidProductoSolicitado(rs.getInt("id_detalleprogramada"));
                Pgr.setIdSolicitud(rs.getInt("id_programada"));
                Pgr.setIdProducto(rs.getInt("id_producto"));
                Pgr.setCantidad(rs.getInt("cantidad"));

                Pgr.setNsn(rs.getString("nsn_producto"));
                Pgr.setProducto(rs.getString("nombre"));
                Pgr.setDescripcion(rs.getString("descripcion_producto"));
                Pgr.setNumeroparte(rs.getString("n_parte"));
                Pgr.setCantidadDisponible(rs.getInt("disponible"));
                
                Pgr.setIdSistema(rs.getInt("id_sistema"));
                Pgr.setIdUnidad(rs.getInt("id_unidad"));

                Pgr.setpista(rs.getString("pista"));
                Pgr.setmarca(rs.getString("marca"));
                Pgr.setmodelo(rs.getString("modelo"));
                Pgr.setfecha(rs.getString("fecha_solicitud"));
                
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
