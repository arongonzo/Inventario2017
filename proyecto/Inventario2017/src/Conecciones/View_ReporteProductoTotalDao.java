
package Conecciones;

import Entidades.View_ReporteProductoTotal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

public class View_ReporteProductoTotalDao {
   Coneccion conexion = new Coneccion();

    public List<View_ReporteProductoTotal> listado() {
        Connection con = null;
        CallableStatement cstm = null;
        ResultSet rs = null;
        List<View_ReporteProductoTotal> lista = null;
        try {
            lista = new ArrayList<>();
            con = conexion.getConection();
            cstm = con.prepareCall("{Call pa_reporteProductoCompleto}");
            
            rs = cstm.executeQuery();
            View_ReporteProductoTotal Prd = null;
            while (rs.next()) {
                
                Prd = new View_ReporteProductoTotal();
                Prd.setidproducto(rs.getInt("id_producto"));
                
                Prd.setNsnproducto(rs.getString("nsn_producto"));
                
                Prd.setnombre(rs.getString("nombre"));
                Prd.setdescripcion_producto(rs.getString("descripcion_producto"));
                Prd.setTotalSolicitado(rs.getInt("TotalSolicitado"));
                Prd.setDespachosrealizados(rs.getInt("Despachosrealizados"));
                Prd.setSaldoinicio(rs.getInt("Saldoinicio"));
                Prd.setSaldostock(rs.getInt("Saldostock"));
                Prd.setStockFuturo(rs.getInt("StockFuturo"));
                Prd.setelementosporllegar(rs.getInt("elementosporllegar"));
                Prd.setrequerimientoduro(rs.getInt("requerimientoduro"));
                Prd.setstockseguridad(rs.getInt("stockseguridad"));
                Prd.setValorunitario(rs.getDouble("valorunitario"));
                
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
