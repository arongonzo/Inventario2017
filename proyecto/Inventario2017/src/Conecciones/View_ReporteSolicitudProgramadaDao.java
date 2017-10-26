
package Conecciones;

import Entidades.View_ReporteSolicitudProgramada;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.JButton;

public class View_ReporteSolicitudProgramadaDao {
    
    Coneccion conexion = new Coneccion();

    public List<View_ReporteSolicitudProgramada> listado() {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<View_ReporteSolicitudProgramada> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_ReporteSolicitudProgramadaAnual}");
            
            rs = cstm.executeQuery();
            View_ReporteSolicitudProgramada Prd = null;
            while (rs.next()) {
                
                Prd = new View_ReporteSolicitudProgramada();
                Prd.setIdProgramada(rs.getInt("id_programada"));
                Prd.setZonal(rs.getString("nombre_zonal"));
                Prd.setSistema(rs.getString("nombre_sistema"));
                Prd.setUnidad(rs.getString("nombre_unidad"));
                
                Prd.setpista(rs.getString("pista"));
                Prd.setmarca(rs.getString("marca"));
                Prd.setmodelo(rs.getString("modelo"));
                Prd.setfecha(rs.getDate("fecha_solicitud"));
                
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
