package ModeloTabla;

import Entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaUsuario extends AbstractTableModel {
    
    String[] columnas = {"Usuario", "Rol", "Nombre", "Apellido"};
    public List<Usuario> usuarios = new ArrayList<>();

    public ModeloTablaUsuario(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
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
                resp = usuarios.get(rowIndex).getUserName();
                break;
            case 1:    
                resp = usuarios.get(rowIndex).getRolname();
                break;
            case 2:    
                resp = usuarios.get(rowIndex).getNombre();
                break;
            case 3:    
                resp = usuarios.get(rowIndex).getApellido();
                break;
        }
        
        return resp;
    }

    public Usuario DameUsuario(int fila) {
        return usuarios.get(fila);
    }
    
}
