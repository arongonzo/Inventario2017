package Logico;

import Conecciones.DespachoDetalleDao;
import Entidades.DespachoDetalle;
import java.util.List;

public class DespachoDetalleLog {
    
    DespachoDetalleDao despachodetalle = new DespachoDetalleDao();

    public boolean AgregarDespachoDetalle(DespachoDetalle des) {
        return despachodetalle.AgregarDespachoDetalle(des);
    }

    public boolean UpdateDespachoDetalle(DespachoDetalle des) {
        return despachodetalle.UpdateDespachoDetalle(des);
    }

    public boolean DeleteDespachoDetalle(DespachoDetalle des) {
        return despachodetalle.DeleteDespachoDetalle(des);
    }

    public List<DespachoDetalle> listado(int id_Despacho) {
      return despachodetalle.listado(id_Despacho);
    }
    
}
