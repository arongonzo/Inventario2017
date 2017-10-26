
package Conecciones;

import Entidades.View_ReporteSolicitudRepuesto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.JButton;

public class View_ReporteSolicitudRepuestoDao {
    
    Coneccion conexion = new Coneccion();

    public List<View_ReporteSolicitudRepuesto> listado() {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<View_ReporteSolicitudRepuesto> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_ReporteSolicitudRepuesto}");
            
            rs = cstm.executeQuery();
            View_ReporteSolicitudRepuesto Prd = null;
            while (rs.next()) {
                
                Prd = new View_ReporteSolicitudRepuesto();
                Prd.setIdSolicitudRepuesto(rs.getInt("id_solicitudRepuesto"));
                
                Prd.setIdComercial(rs.getString("id_comercial"));
                
                Prd.setZonal(rs.getString("nombre_zonal"));
                Prd.setSistema(rs.getString("nombre_sistema"));
                Prd.setUnidad(rs.getString("nombre_unidad"));
                Prd.setFechaCreacion(rs.getDate("fecha_creacion"));
                
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
