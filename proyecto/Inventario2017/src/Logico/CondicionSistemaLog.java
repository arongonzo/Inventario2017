package Logico;

import Conecciones.CondicionSistemaDao;
import Entidades.CondicionSistema;
import java.util.List;

public class CondicionSistemaLog {
    CondicionSistemaDao sistemas = new CondicionSistemaDao();

    public boolean AgregarSistema(CondicionSistema Sis) {
        return sistemas.AgregarCondicionSistema(Sis);
    }

    public boolean UpdateSistema(CondicionSistema Sis) {
        return sistemas.UpdateCondicionSistema(Sis);
    }

    public boolean DeleteSistema(CondicionSistema Sis) {
        return sistemas.DeleteCondicionSistema(Sis);
    }

    public List<CondicionSistema> listado(String filtro) {
      return sistemas.listado(filtro);
    }
}
