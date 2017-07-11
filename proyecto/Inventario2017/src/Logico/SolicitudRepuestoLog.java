package Logico;

import Conecciones.SolicitudRepuestoDao;
import Entidades.SolicitudRepuesto;
import java.util.List;

public class SolicitudRepuestoLog {
    
    SolicitudRepuestoDao repuesto = new SolicitudRepuestoDao();

    public int AgregarRepuesto(SolicitudRepuesto Srp) {
        return repuesto.AgregarRepuesto(Srp);
    }

    public boolean UpdateRepuesto(SolicitudRepuesto Srp) {
        return repuesto.UpdateRepuesto(Srp);
    }

    public boolean DeleteRepuesto(SolicitudRepuesto Srp) {
        return repuesto.DeleteRepuesto(Srp);
    }

    public List<SolicitudRepuesto> listado(int id_sistema, int id_unidad, String fecha) {
      return repuesto.listado(id_sistema, id_unidad, fecha);
    } 
    
}
