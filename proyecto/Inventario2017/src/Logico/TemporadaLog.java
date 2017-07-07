package Logico;

import Conecciones.TemporadaDao;
import Entidades.Temporada;
import java.util.List;

public class TemporadaLog {
    
    TemporadaDao temporada = new TemporadaDao();

    public boolean AgregarTemporada(Temporada Tem) {
        return temporada.AgregarTemporada(Tem);
    }

    public boolean UpdateTemporada(Temporada Tem) {
        return temporada.UpdateTemporada(Tem);
    }

    public boolean DeleteTemporada(Temporada Tem) {
        return temporada.DeleteTemporada(Tem);
    }

    public List<Temporada> listado() {
      return temporada.listado();
    }
    
}
