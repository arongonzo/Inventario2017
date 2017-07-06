/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logico;

import Conecciones.ZonalDao;
import Entidades.Zonal;
import java.util.List;

public class ZonalLog {
    ZonalDao zonales = new ZonalDao();

    public boolean AgregarZonal(Zonal Znl) {
        return zonales.AgregarZonal(Znl);
    }

    public boolean UpdateZonal(Zonal Znl) {
        return zonales.UpdateZonal(Znl);
    }

    public boolean DeleteZonal(Zonal Znl) {
        return zonales.DeleteZonal(Znl);
    }

    public List<Zonal> listado(String filtro) {
      return zonales.listado(filtro);
    }
}
