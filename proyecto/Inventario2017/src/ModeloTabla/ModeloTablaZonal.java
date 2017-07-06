package ModeloTabla;

import Entidades.Zonal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaZonal extends AbstractTableModel {
    String[] columnas = {"Zonal", "Descripción"};
    public List<Zonal> zonales = new ArrayList<>();

    public ModeloTablaZonal(List<Zonal> zonales) {
        this.zonales = zonales;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return zonales.size();
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
                resp = zonales.get(rowIndex).getNombre_Zonal();
                break;
            case 1:    
                resp = zonales.get(rowIndex).getDescripcion_Zonal();
                break;
                
        }
        return resp;
    }

    public Zonal DameUsuario(int fila) {
        return zonales.get(fila);
    }
}
