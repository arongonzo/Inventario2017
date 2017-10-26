package Logico;

import Conecciones.View_ReporteInicioTemporadaDao;
import Entidades.View_ReporteInicioTemporada;
import java.util.List;

public class View_ReporteInicioTemporadaLog {
    
    View_ReporteInicioTemporadaDao view = new View_ReporteInicioTemporadaDao();
    
    public List<View_ReporteInicioTemporada> listado() {
      return view.listado();
    } 
    
}
