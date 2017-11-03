package Logico;

import Conecciones.SolicitudRepuestoDetalleDao;
import Entidades.SolicitudRepuestoDetalle;
import java.util.List;

public class SolicitudRepuestoDetalleLog {
    
    SolicitudRepuestoDetalleDao repuesto = new SolicitudRepuestoDetalleDao();

    public boolean AgregarRepuesto(SolicitudRepuestoDetalle Srp) {
        return repuesto.AgregarRepuestoDetalle(Srp);
    }

    public boolean UpdateRepuesto(SolicitudRepuestoDetalle Srp) {
        return repuesto.UpdateRepuestoDetalle(Srp);
    }

    public boolean DeleteRepuesto(int Srp) {
        return repuesto.DeleteRepuestoDetalle(Srp);
    }

    public List<SolicitudRepuestoDetalle> listado(int id_SolicitudRepuesto) {
      return repuesto.listado(id_SolicitudRepuesto);
    }
    
}
