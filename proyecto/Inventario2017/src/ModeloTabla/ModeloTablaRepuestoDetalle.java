package ModeloTabla;

import Entidades.SolicitudProgramadaDetalle;
import Entidades.SolicitudRepuestoDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaRepuestoDetalle  extends AbstractTableModel {
    
    String[] columnas = {"N.S.N", "Producto","Descripcion", "Nº Parte", "Cantidad solicitda"};
    public List<SolicitudRepuestoDetalle> RepuestoDetalles = new ArrayList<>();

    public ModeloTablaRepuestoDetalle(List<SolicitudRepuestoDetalle> ProgramadaDetalles) {
        this.RepuestoDetalles = ProgramadaDetalles;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return RepuestoDetalles.size();
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
                resp = RepuestoDetalles.get(rowIndex).getNsn();
                break;
            case 1:    
                resp = RepuestoDetalles.get(rowIndex).getProduto();
                break;
            case 2:    
                resp = RepuestoDetalles.get(rowIndex).getDescripcion();
                break;
            case 3:    
                resp = RepuestoDetalles.get(rowIndex).getNumeroparte();
                break;
            case 4:    
                resp = RepuestoDetalles.get(rowIndex).getCantidad();
                break;
            case 5:    
                resp = RepuestoDetalles.get(rowIndex).getCantidadDisponible();
                break;
    
        }
        
        return resp;
    }

    public SolicitudRepuestoDetalle DameRepuestoDetalle(int fila) {
        return RepuestoDetalles.get(fila);
    } 
    
}
