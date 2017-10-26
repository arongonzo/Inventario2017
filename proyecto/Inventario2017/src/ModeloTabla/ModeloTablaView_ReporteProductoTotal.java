
package ModeloTabla;

import Entidades.View_ReporteProductoTotal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaView_ReporteProductoTotal extends AbstractTableModel {
        String[] columnas = {"codigo producto", "NSN Producto", "Nombre Producto", "Descripcion del Producto", "Total Solicitado", "Despachos realizados SSNV", "Saldo Inicio", "En Stock", "Stock Futuro al dia de hoy", "Elementos por llegar", "Requerimiento duro", "Stock de seguridad 10%", "Valor unitario en USD"};
    public List<View_ReporteProductoTotal> view = new ArrayList<>();

    public ModeloTablaView_ReporteProductoTotal(List<View_ReporteProductoTotal> viewProductototal) {
        this.view = viewProductototal;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return view.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object resp = null;
        switch(columnIndex){    
            case 0:    
                resp = view.get(rowIndex).getidproducto();
                break;
            case 1:    
                resp = view.get(rowIndex).getNsnproducto();
                break;
            case 2:    
                resp = view.get(rowIndex).getnombre();
                break;
            case 3:    
                resp = view.get(rowIndex).getdescripcion_producto();
                break;
            case 4:    
                resp = view.get(rowIndex).getTotalSolicitado();
                break;
            case 5:    
                resp = view.get(rowIndex).getDespachosrealizados();
                break;
            case 6:    
                resp = view.get(rowIndex).getSaldoinicio();
                break;
            case 7:    
                resp = view.get(rowIndex).getSaldostock();
                break;
            case 8:    
                resp = view.get(rowIndex).getStockFuturo();
                break;
            case 9:    
                resp = view.get(rowIndex).getelementosporllegar();
                break;
            case 10:    
                resp = view.get(rowIndex).getrequerimientoduro();
                break;
            case 11:    
                resp = view.get(rowIndex).getstockseguridad();
                break;
            case 12:    
                resp = view.get(rowIndex).getValorunitario();
                break;
                
        }
        
        return resp;
    }

    public View_ReporteProductoTotal DameProgramadaDetalle(int fila) {
        return view.get(fila);
    }
}
