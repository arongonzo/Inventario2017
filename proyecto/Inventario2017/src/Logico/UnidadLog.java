
package Logico;

import Conecciones.UnidadDao;
import Entidades.Unidad;
import java.util.List;

public class UnidadLog {
    UnidadDao unidades = new UnidadDao();

    public boolean AgregarUnidad(Unidad Und) {
        return unidades.AgregarUnidad(Und);
    }

    public boolean UpdateUnidad(Unidad Und) {
        return unidades.UpdateUnidad(Und);
    }

    public boolean DeleteUnidad(Unidad Und) {
        return unidades.DeleteUnidad(Und);
    }

    public List<Unidad> listado(String filtro) {
      return unidades.listado(filtro);
    }
}
