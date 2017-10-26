package Logico;

import Conecciones.View_ReporteSolicitudProgramadaAnualDao;
import Entidades.View_ReporteSolicitudProgramadaAnual;
import java.util.List;

public class View_ReporteSolicitudProgramadaAnualLog {
    View_ReporteSolicitudProgramadaAnualDao programada = new View_ReporteSolicitudProgramadaAnualDao();

    public int AgregarProgramada(View_ReporteSolicitudProgramadaAnual Pgr) {
        return programada.AgregarProgramada(Pgr);
    }

    public boolean UpdateProgramada(View_ReporteSolicitudProgramadaAnual Pgr) {
        return programada.UpdateProgramada(Pgr);
    }

    public boolean DeleteProgramada(View_ReporteSolicitudProgramadaAnual Pgr) {
        return programada.DeleteProgramada(Pgr);
    }

    public List<View_ReporteSolicitudProgramadaAnual> listado(int id_sistema, int id_unidad, String fecha) {
      return programada.listado(id_sistema, id_unidad, fecha);
    }
}
