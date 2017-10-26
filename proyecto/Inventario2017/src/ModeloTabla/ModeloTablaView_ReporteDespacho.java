package ModeloTabla;

import Entidades.View_ReporteDespacho;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaView_ReporteDespacho  extends AbstractTableModel {
    String[] columnas = {"Numero Despacho", "Zonal", "Unidad", "Sistema", "Fecha", "Generar"};
    public List<View_ReporteDespacho> view = new ArrayList<>();

    public ModeloTablaView_ReporteDespacho(List<View_ReporteDespacho> viewDespacho) {
        this.view = viewDespacho;
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
                resp = view.get(rowIndex).getZonal();
                break;
            case 2:    
                resp = view.get(rowIndex).getUnidad();
                break;
            case 3:    
                resp = view.get(rowIndex).getSistema();
                break;
            case 4:    
                resp = view.get(rowIndex).getFechaCreacion();
                break;
            case 5:    
                resp = view.get(rowIndex).getbtn();
                break;
        }
        
        return resp;
    }

    public View_ReporteDespacho DameDespachoDetalle(int fila) {
        return view.get(fila);
    }
}
