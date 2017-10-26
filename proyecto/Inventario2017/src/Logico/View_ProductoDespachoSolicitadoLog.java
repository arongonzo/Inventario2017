package Logico;

import Conecciones.View_ProductoDespachoSolicitadoDao;
import Entidades.View_ProductoDespachoSolicitado;
import java.util.List;

public class View_ProductoDespachoSolicitadoLog {
    View_ProductoDespachoSolicitadoDao view = new View_ProductoDespachoSolicitadoDao();
    
    public List<View_ProductoDespachoSolicitado> listado(int id_sistema, int id_unidad,int id_producto,String nsn,String numeroparte,String producto) {
      return view.listado(id_sistema, id_unidad, id_producto, nsn, numeroparte, producto);
    } 
}
