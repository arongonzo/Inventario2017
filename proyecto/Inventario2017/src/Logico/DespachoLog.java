
package Logico;

import Conecciones.DespachoDao;
import Entidades.Despacho;
import java.util.List;

public class DespachoLog {
    
    DespachoDao cl_despacho = new DespachoDao();

    public int AgregarDespacho(Despacho Srp) {
        return cl_despacho.AgregarDespacho(Srp);
    }

    public boolean UpdateDespacho(Despacho Srp) {
        return cl_despacho.UpdateDespacho(Srp);
    }

    public boolean DeleteDespacho(Despacho Srp) {
        return cl_despacho.DeleteDespacho(Srp);
    }

    public List<Despacho> listado(int id_sistema, int id_unidad, String fecha) {
      return cl_despacho.listado(id_sistema, id_unidad, fecha);
    }
    
}
