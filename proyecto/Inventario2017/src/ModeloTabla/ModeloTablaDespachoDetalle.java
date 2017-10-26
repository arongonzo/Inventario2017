
package ModeloTabla;

import Entidades.DespachoDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaDespachoDetalle  extends AbstractTableModel {
    
    String[] columnas = {"N.S.N", "Producto","Descripcion", "Nº Parte", "Cantidad a Despachar", "Saldo en bodega", "Cantidad entregada"};
    public List<DespachoDetalle> despachodetalle = new ArrayList<>();

    public ModeloTablaDespachoDetalle(List<DespachoDetalle> DespachoDetalles) {
        this.despachodetalle = DespachoDetalles;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return despachodetalle.size();
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
                resp = despachodetalle.get(rowIndex).getNsn();
                break;
            case 1:    
                resp = despachodetalle.get(rowIndex).getProduto();
                break;
            case 2:    
                resp = despachodetalle.get(rowIndex).getDescripcion();
                break;
            case 3:    
                resp = despachodetalle.get(rowIndex).getNumeroparte();
                break;
            case 4:    
                resp = despachodetalle.get(rowIndex).getCantidad();
                break;
             case 5:    
                resp = despachodetalle.get(rowIndex).getCantidadDisponible();
                break;
             case 6:    
                resp = despachodetalle.get(rowIndex).getCantidadDespachada();
                break;
    
        }
        
        return resp;
    }

    public DespachoDetalle DameDespachoDetalle(int fila) {
        return despachodetalle.get(fila);
    }
    
}
