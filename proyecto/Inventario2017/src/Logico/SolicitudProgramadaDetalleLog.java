
package Logico;

import Conecciones.SolicitudProgramadaDetalleDao;
import Entidades.SolicitudProgramadaDetalle;
import java.util.List;

public class SolicitudProgramadaDetalleLog {
    SolicitudProgramadaDetalleDao programada = new SolicitudProgramadaDetalleDao();

    public boolean AgregarProgramada(SolicitudProgramadaDetalle Pgr) {
        return programada.AgregarProgramadaDetalle(Pgr);
    }

    public boolean UpdateProgramada(SolicitudProgramadaDetalle Pgr) {
        return programada.UpdateProgramadaDetalle(Pgr);
    }

    public boolean DeleteProgramada(SolicitudProgramadaDetalle Pgr) {
        return programada.DeleteProgramadaDetalle(Pgr);
    }

    public List<SolicitudProgramadaDetalle> listado(int id_programada) {
      return programada.listado(id_programada);
    } 
}
