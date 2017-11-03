package ModeloTabla;

import Entidades.View_ReporteInicioTemporada;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaView_ReporteInicioTemporada  extends AbstractTableModel {
    String[] columnas = {"Año", "codigo", "N.S.N", "Repuesto", "Descripcion", "Nº Parte", "Saldo inicial", "Valor Initario"};
    public List<View_ReporteInicioTemporada> ViewReporteInicioTemporada = new ArrayList<>();

    public ModeloTablaView_ReporteInicioTemporada(List<View_ReporteInicioTemporada> View_ReporteInicioTemporada) {
        this.ViewReporteInicioTemporada = View_ReporteInicioTemporada;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return ViewReporteInicioTemporada.size();
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
                resp = ViewReporteInicioTemporada.get(rowIndex).gettemporada();
                break;
            case 1:    
                resp = ViewReporteInicioTemporada.get(rowIndex).getid_producto();
                break;
            case 2:    
                resp = ViewReporteInicioTemporada.get(rowIndex).getnsn_producto();
                break;
            case 3:    
                resp = ViewReporteInicioTemporada.get(rowIndex).getnombre();
                break;
            case 4:    
                resp = ViewReporteInicioTemporada.get(rowIndex).getdescripcion_producto();
                break;
            case 5:    
                resp = ViewReporteInicioTemporada.get(rowIndex).getn_parte();
                break;
            case 6:    
                resp = ViewReporteInicioTemporada.get(rowIndex).getcantidad();
                break;
            case 7:    
                resp = ViewReporteInicioTemporada.get(rowIndex).getvalor_unitario();
                break;
        }
        
        return resp;
    }

    public View_ReporteInicioTemporada Dame_ReporteInicioTemporada(int fila) {
        return ViewReporteInicioTemporada.get(fila);
    }
    
}
