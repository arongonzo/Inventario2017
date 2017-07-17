package ModeloTabla;

import Entidades.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaView_Producto  extends AbstractTableModel {
    
    String[] columnas = {"N.S.N", "Producto", "Descripcion", "Nº Parte"};
    public List<Producto> productos = new ArrayList<>();

    public ModeloTablaView_Producto(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return productos.size();
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
                resp = productos.get(rowIndex).getNsn();
                break;
            case 1:    
                resp = productos.get(rowIndex).getNombre();
                break;
            case 2:    
                resp = productos.get(rowIndex).getDescripcion();
                break;
            case 3:    
                resp = productos.get(rowIndex).getN_parte();
                break;            
        }
        
        return resp;
    }

    public Producto DameProducto(int fila) {
        return productos.get(fila);
    }
    
}
