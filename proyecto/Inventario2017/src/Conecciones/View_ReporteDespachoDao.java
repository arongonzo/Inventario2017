package Conecciones;

import Entidades.View_ReporteDespacho;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.JButton;

public class View_ReporteDespachoDao {
    Coneccion conexion = new Coneccion();

    public List<View_ReporteDespacho> listado() {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<View_ReporteDespacho> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_ReporteDespacho}");
            
            rs = cstm.executeQuery();
            View_ReporteDespacho Prd = null;
            while (rs.next()) {
                
                Prd = new View_ReporteDespacho();
                Prd.setIdDespacho(rs.getInt("id_despacho"));
                
                Prd.setIdComercial(rs.getString("id_comercial"));
                
                Prd.setZonal(rs.getString("nombre_zonal"));
                Prd.setSistema(rs.getString("nombre_sistema"));
                Prd.setUnidad(rs.getString("nombre_unidad"));
                Prd.setFechaCreacion(rs.getDate("fecha"));
                
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
