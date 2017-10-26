
package Logico;

import Conecciones.View_ReporteProductoTotalDao;
import Entidades.View_ReporteProductoTotal;
import java.util.List;

public class View_ReporteProductoTotalLog {
    View_ReporteProductoTotalDao view = new View_ReporteProductoTotalDao();
    
    public List<View_ReporteProductoTotal> listado() {
      return view.listado();
    } 
}
