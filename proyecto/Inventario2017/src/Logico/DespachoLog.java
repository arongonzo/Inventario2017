
package Logico;

import Conecciones.DespachoDao;
import Entidades.Despacho;
import java.util.List;

public class DespachoLog {
    
    DespachoDao repuesto = new DespachoDao();

    public int AgregarRepuesto(Despacho Srp) {
        return repuesto.AgregarDespacho(Srp);
    }

    public boolean UpdateRepuesto(Despacho Srp) {
        return repuesto.UpdateDespacho(Srp);
    }

    public boolean DeleteRepuesto(Despacho Srp) {
        return repuesto.DeleteDespacho(Srp);
    }

    public List<Despacho> listado(int id_sistema, int id_unidad, String fecha) {
      return repuesto.listado(id_sistema, id_unidad, fecha);
    }
    
}
