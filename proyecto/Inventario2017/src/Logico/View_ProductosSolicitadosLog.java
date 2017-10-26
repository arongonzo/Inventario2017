
package Logico;

import Conecciones.View_ProductosSolicitadosDao;
import Entidades.View_ProductosSolicitados;
import java.util.List;

public class View_ProductosSolicitadosLog {
    View_ProductosSolicitadosDao view = new View_ProductosSolicitadosDao();
    
    public List<View_ProductosSolicitados> listado(int id_unidad, int id_sistema,int id_producto,String nsn,String numeroparte,String producto) {
      return view.listado(id_sistema, id_unidad, id_producto, nsn, numeroparte, producto);
    } 
    
}
