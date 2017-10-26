package ModeloTabla;

import Entidades.SolicitudProgramadaDetalle;
import Entidades.View_ReporteSolicitudProgramada;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaView_ReporteDetalleSolicitudAnual  extends AbstractTableModel{
    String[] columnas = {"N.S.N", "Producto","Descripcion", "Nº Parte", "Cantidad solicitda"};
    public List<SolicitudProgramadaDetalle> ProgramadaDetalles = new ArrayList<>();

    public ModeloTablaView_ReporteDetalleSolicitudAnual(List<SolicitudProgramadaDetalle> ProgramadaDetalles) {
        this.ProgramadaDetalles = ProgramadaDetalles;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return ProgramadaDetalles.size();
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
                resp = ProgramadaDetalles.get(rowIndex).getNsn();
                break;
            case 1:    
                resp = ProgramadaDetalles.get(rowIndex).getProduto();
                break;
            case 2:    
                resp = ProgramadaDetalles.get(rowIndex).getDescripcion();
                break;
            case 3:    
                resp = ProgramadaDetalles.get(rowIndex).getNumeroparte();
                break;
            case 4:    
                resp = ProgramadaDetalles.get(rowIndex).getCantidad();
                break;
            case 5:    
                resp = ProgramadaDetalles.get(rowIndex).getCantidadDisponible();
                break;
        }
        
        return resp;
    }

    public SolicitudProgramadaDetalle DameProgramadaDetalle(int fila) {
        return ProgramadaDetalles.get(fila);
    } 
}
