package ModeloTabla;

import Entidades.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaProducto  extends AbstractTableModel {
    String[] columnas = {"Nº", "N.S.N", "Repuesto","Descripcion", "Nº Parte", "Saldo en bodega", "Valor $USD"};
    public List<Producto> productos = new ArrayList<>();

    public ModeloTablaProducto(List<Producto> productos) {
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
                resp = productos.get(rowIndex).getIdproducto();
                break;
            case 1:    
                resp = productos.get(rowIndex).getNsn();
                break;
            case 2:    
                resp = productos.get(rowIndex).getNombre();
                break;
            case 3:    
                resp = productos.get(rowIndex).getDescripcion();
                break;
            case 4:    
                resp = productos.get(rowIndex).getN_parte();
                break;
            case 5:    
                resp = productos.get(rowIndex).getCantidad();
                break;
            case 6:    
                resp = productos.get(rowIndex).getValor_unitario();
                break;
        }
        
        return resp;
    }

    public Producto DameProducto(int fila) {
        return productos.get(fila);
    }   
}
