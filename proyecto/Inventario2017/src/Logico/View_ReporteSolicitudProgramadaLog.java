package Logico;

import Conecciones.View_ReporteSolicitudProgramadaDao;
import Entidades.View_ReporteSolicitudProgramada;
import java.util.List;

public class View_ReporteSolicitudProgramadaLog {
    View_ReporteSolicitudProgramadaDao view = new View_ReporteSolicitudProgramadaDao();
    
    public List<View_ReporteSolicitudProgramada> listado() {
      return view.listado();
    } 
}
