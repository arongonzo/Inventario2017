package Logico;

import Conecciones.SistemaDao;
import Entidades.Sistema;
import java.util.List;

public class SistemaLog {
    SistemaDao sistemas = new SistemaDao();

    public boolean AgregarSistema(Sistema Sis) {
        return sistemas.AgregarSistema(Sis);
    }

    public boolean UpdateSistema(Sistema Sis) {
        return sistemas.UpdateSistema(Sis);
    }

    public boolean DeleteSistema(Sistema Sis) {
        return sistemas.DeleteSistema(Sis);
    }

    public List<Sistema> listado(String filtro) {
      return sistemas.listado(filtro);
    }
}
