package Entidades;

import java.sql.Date;
import javax.swing.JButton;

public class View_ReporteSolicitudProgramada {
    
    int id_programada;
    String zonal;
    String sistema;
    String unidad;
    String pista;
    String marca;
    String modelo;
    Date fecha;
    int estado;
    JButton btn;
    
    public View_ReporteSolicitudProgramada() {
    }

    public int getIdProgramada() {
        return id_programada;
    }
    
    public void setIdProgramada(int id_programada) {
        this.id_programada = id_programada;
    }

    public String getZonal() {
        return zonal;
    }

    public void setZonal(String zonal) {
        this.zonal = zonal;
    }
    
    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }
 
    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
    public String getpista() {
        return pista;
    }

    public void setpista(String pista) {
        this.pista = pista;
    }

    public String getmarca() {
        return marca;
    }

    public void setmarca(String marca) {
        this.marca = marca;
    }

    public String getmodelo() {
        return modelo;
    }

    public void setmodelo(String modelo) {
        this.modelo = modelo;
    }
    
    public Date getfecha() {
        return fecha;
    }

    public void setfecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public JButton getbtn() {
        return btn;
    }

    public void setbtn(JButton btn) {
        this.btn = btn;
    }
    
}
