package Logico;

import Conecciones.View_ProductoDespachoSolicitadoDao;
import Entidades.View_ProductoDespachoSolicitado;
import java.util.List;

public class View_ProductoDespachoSolicitadoLog {
    View_ProductoDespachoSolicitadoDao view = new View_ProductoDespachoSolicitadoDao();
    
    public List<View_ProductoDespachoSolicitado> listado(int id_sistema, int id_unidad) {
      return view.listado(id_sistema, id_unidad);
    } 
}
