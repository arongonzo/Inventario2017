package Logico;


import Conecciones.SolicitudProgramadaDao;
import Entidades.SolicitudProgramada;
import java.util.List;

public class SolicitudProgramadaLog {
    SolicitudProgramadaDao programada = new SolicitudProgramadaDao();

    public int AgregarProgramada(SolicitudProgramada Pgr) {
        return programada.AgregarProgramada(Pgr);
    }

    public boolean UpdateProgramada(SolicitudProgramada Pgr) {
        return programada.UpdateProgramada(Pgr);
    }

    public boolean DeleteProgramada(SolicitudProgramada Pgr) {
        return programada.DeleteProgramada(Pgr);
    }

    public List<SolicitudProgramada> listado(int id_sistema, int id_unidad, String fecha) {
      return programada.listado(id_sistema, id_unidad, fecha);
    } 
}
