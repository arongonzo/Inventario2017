
package ModeloTabla;

import Entidades.Unidad;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaUnidad  extends AbstractTableModel {
    
    String[] columnas = {"Zona", "Unidad"};
    public List<Unidad> unidades = new ArrayList<>();

    public ModeloTablaUnidad(List<Unidad> unidades) {
        this.unidades = unidades;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return unidades.size();
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
                resp = unidades.get(rowIndex).getNombreZonal();
                break;
            case 1:    
                resp = unidades.get(rowIndex).getNombre();
                break;
        }
        
        return resp;
    }

    public Unidad DameUnidad(int fila) {
        return unidades.get(fila);
    }
    
}
