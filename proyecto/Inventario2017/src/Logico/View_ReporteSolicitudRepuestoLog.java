package Logico;

import Conecciones.View_ReporteSolicitudRepuestoDao;
import Entidades.View_ReporteSolicitudRepuesto;
import java.util.List;

public class View_ReporteSolicitudRepuestoLog {
    View_ReporteSolicitudRepuestoDao view = new View_ReporteSolicitudRepuestoDao();
    
    public List<View_ReporteSolicitudRepuesto> listado() {
      return view.listado();
    } 
}
