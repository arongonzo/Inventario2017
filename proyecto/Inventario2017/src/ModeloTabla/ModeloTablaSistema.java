
package ModeloTabla;

import Entidades.Sistema;
import Entidades.Zonal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaSistema extends AbstractTableModel {
    String[] columnas = {"Sistema", "Descripción"};
    public List<Sistema> sistemas = new ArrayList<>();

    public ModeloTablaSistema(List<Sistema> sistemas) {
        this.sistemas = sistemas;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return sistemas.size();
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
                resp = sistemas.get(rowIndex).getNombre_Sistema();
                break;
            case 1:    
                resp = sistemas.get(rowIndex).getDescripcion_Sistema();
                break;
                
        }
        return resp;
    }

    public Sistema DameSistema(int fila) {
        return sistemas.get(fila);
    }
}
