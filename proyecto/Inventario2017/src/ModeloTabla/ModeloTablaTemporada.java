
package ModeloTabla;

import Entidades.Temporada;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaTemporada extends AbstractTableModel {
    String[] columnas = {"Temporada", "Activa"};
    public List<Temporada> Temporada = new ArrayList<>();

    public ModeloTablaTemporada(List<Temporada> temporadas) {
        this.Temporada = temporadas;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return Temporada.size();
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
                resp = Temporada.get(rowIndex).getNombreTemporada();
                break;
            case 1:    
                resp = Temporada.get(rowIndex).getActivaTexto();
                break;
                
        }
        return resp;
    }

    public Temporada DameTemporada(int fila) {
        return Temporada.get(fila);
    }
}
