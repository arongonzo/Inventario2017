package Conecciones;

import Entidades.View_ReporteInicioTemporada;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.JButton;

public class View_ReporteInicioTemporadaDao {
    
    Coneccion conexion = new Coneccion();

    public List<View_ReporteInicioTemporada> listado() {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<View_ReporteInicioTemporada> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_ReporteProductoInicioTemporada}");
            
            rs = cstm.executeQuery();
            View_ReporteInicioTemporada Prd = null;
            while (rs.next()) {
                
                Prd = new View_ReporteInicioTemporada();
                
                Prd.settemporada(rs.getString("temporada"));
                Prd.setid_producto(rs.getInt("id_producto"));
                Prd.setnsn_producto(rs.getString("nsn_producto"));
                Prd.setnombre(rs.getString("nombre"));
                Prd.setcantidad(rs.getInt("cantidad"));
                Prd.setdescripcion_producto(rs.getString("descripcion_producto"));
                Prd.setn_parte(rs.getString("n_parte"));
                Prd.setvalor_unitario(rs.getInt("valor_unitario"));
                Prd.setubicacion(rs.getString("ubicacion"));
                Prd.setEstado(rs.getInt("estado"));
                        
                JButton btn1 = new JButton("Generar Excel");
                btn1.setName("btnModificar");
                
                Prd.setbtn(btn1);
                
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
