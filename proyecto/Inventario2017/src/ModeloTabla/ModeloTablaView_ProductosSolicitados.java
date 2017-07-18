
package ModeloTabla;

import Entidades.View_ProductosSolicitados;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaView_ProductosSolicitados extends AbstractTableModel {
    String[] columnas = {"N.S.N", "Producto","Descripcion", "Nº Parte", "Cantidad Programada"};
    public List<View_ProductosSolicitados> view = new ArrayList<>();

    public ModeloTablaView_ProductosSolicitados(List<View_ProductosSolicitados> viewProducto) {
        this.view = viewProducto;
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
                resp = view.get(rowIndex).getNsn();
                break;
            case 1:    
                resp = view.get(rowIndex).getProduto();
                break;
            case 2:    
                resp = view.get(rowIndex).getDescripcion();
                break;
            case 3:    
                resp = view.get(rowIndex).getNumeroparte();
                break;
            case 4:    
                resp = view.get(rowIndex).getCantidad();
                break;
            
        }
        
        return resp;
    }

    public View_ProductosSolicitados DameProgramadaDetalle(int fila) {
        return view.get(fila);
    } 
}
