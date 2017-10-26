package Logico;

import Conecciones.View_ReporteDespachoDao;
import Entidades.View_ReporteDespacho;
import java.util.List;

public class View_ReporteDespachoLog {
    View_ReporteDespachoDao view = new View_ReporteDespachoDao();
    
    public List<View_ReporteDespacho> listado() {
      return view.listado();
    } 
}
