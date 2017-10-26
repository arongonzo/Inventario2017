package ModeloTabla;

import Entidades.View_ReporteSolicitudProgramada;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaView_ReporteSolicitudProgramada extends AbstractTableModel {
    String[] columnas = {"id solicitud", "Zonal","Unidad", "Sistema", "Pista","Marca","Modelo","fecha","Generar"};
    public List<View_ReporteSolicitudProgramada> view = new ArrayList<>();

    public ModeloTablaView_ReporteSolicitudProgramada(List<View_ReporteSolicitudProgramada> viewProducto) {
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
                resp = view.get(rowIndex).getIdProgramada();
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
                resp = view.get(rowIndex).getpista();
                break;
            case 5:    
                resp = view.get(rowIndex).getmarca();
                break;
            case 6:    
                resp = view.get(rowIndex).getmodelo();
                break;
            case 7:    
                resp = view.get(rowIndex).getfecha();
                break;
            case 8:    
                resp = view.get(rowIndex).getbtn();
                break;
        }
        
        return resp;
    }

    public View_ReporteSolicitudProgramada DameProgramadaDetalle(int fila) {
        return view.get(fila);
    }
}
