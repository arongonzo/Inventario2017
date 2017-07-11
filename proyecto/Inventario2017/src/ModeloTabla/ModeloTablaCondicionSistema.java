package ModeloTabla;


import Entidades.CondicionSistema;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaCondicionSistema extends AbstractTableModel {
    
    String[] columnas = {"Condicion Sistema", "Descripción"};
    public List<CondicionSistema> sistemas = new ArrayList<>();

    public ModeloTablaCondicionSistema(List<CondicionSistema> sistemas) {
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
                resp = sistemas.get(rowIndex).getNombreCondicionSistema();
                break;
            case 1:    
                resp = sistemas.get(rowIndex).getDescripcionCondicionSistema();
                break;
                
        }
        return resp;
    }

    public CondicionSistema DameSistema(int fila) {
        return sistemas.get(fila);
    }
    
}
