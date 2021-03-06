package Entidades;

import java.sql.Date;

public class View_ReporteSolicitudProgramadaAnual {
    int id_programada;
    int id_sistema;
    int id_unidad;
    int id_usuario;
    String pista;
    String marca;
    String modelo;
    Date fecha;
    
    int estado;
    
    public View_ReporteSolicitudProgramadaAnual() {
    }

    public View_ReporteSolicitudProgramadaAnual(int id_programada, 
            int id_sistema, 
            int id_unidad, 
            int id_usuario,
            String pista,
            String marca,
            String modelo,
            Date fecha,
            int estado) 
    {
        this.id_programada = id_programada;
        this.id_sistema = id_sistema;
        this.id_unidad = id_unidad;
        this.id_usuario = id_usuario;
        this.pista = pista;
        this.marca = marca;
        this.modelo = modelo;
        this.fecha = fecha;
        this.estado = estado;
    }
    
    public View_ReporteSolicitudProgramadaAnual(int id_programada, 
            int Id_usuario, 
            Date fecha,
            int estado) {
        this.id_programada = id_programada;
        this.id_usuario = Id_usuario;
        this.fecha = fecha;
        this.estado = estado;
    }

    
    public int getIdProgramada() {
        return id_programada;
    }
    
    public void setIdProgramada(int id_programada) {
        this.id_programada = id_programada;
    }

    public int getIdSistema() {
        return id_sistema;
    }

    public void setIdSistema(int id_sistema) {
        this.id_sistema = id_sistema;
    }
 
    public int getIdUnidad() {
        return id_unidad;
    }

    public void setIdUnidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }
    
    public int getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
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
}
