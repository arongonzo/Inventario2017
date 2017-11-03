
package ModeloTabla;

import Entidades.View_ProductoDespachoSolicitado;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaView_ProductoDespachoSolicitado extends AbstractTableModel {
    String[] columnas = {"Numero solicitud","N.S.N", "Repuesto","Descripcion", "Nº Parte", "Cantidad a Despachar", "Saldo en bodega", "Cantidad entregada"};
    public List<View_ProductoDespachoSolicitado> view = new ArrayList<>();

    public ModeloTablaView_ProductoDespachoSolicitado(List<View_ProductoDespachoSolicitado> viewProducto) {
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
                resp = view.get(rowIndex).getIdComercial();
                break;
            case 1:    
                resp = view.get(rowIndex).getNsn();
                break;
            case 2:    
                resp = view.get(rowIndex).getProduto();
                break;
            case 3:    
                resp = view.get(rowIndex).getDescripcion();
                break;
            case 4:    
                resp = view.get(rowIndex).getNumeroparte();
                break;
            case 5:    
                resp = view.get(rowIndex).getCantidad();
                break;
            case 6:    
                resp = view.get(rowIndex).getCantidadDisponible();
                break;
            case 7:    
                resp = view.get(rowIndex).getCantidadEntregada();
                break;
        }
        
        return resp;
    }

    public View_ProductoDespachoSolicitado DameDespachoSolicitado(int fila) {
        return view.get(fila);
    } 
}
