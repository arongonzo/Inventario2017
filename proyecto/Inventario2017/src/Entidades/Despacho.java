package Entidades;

import java.sql.Date;

public class Despacho {
    
    int id_despacho;
    int id_temporada;
    String id_comercial;
    
    int id_usuario;
    int id_unidad;
    int id_sistema;
    Date fecha_creacion;
    int id_prioridad;
    
    String nombre_recibe;
    String correo_recibe;
    String Anexo_recibe;
    
    int estado;
    
    public Despacho() {
    }
    
    public Despacho(int id_despacho,
            int id_usuario,
            Date fecha_creacion,
            int estado) 
    {
        this.id_despacho = id_despacho;
        this.id_usuario = id_usuario;
        this.fecha_creacion = fecha_creacion;
        this.estado = estado;
    }

    public Despacho(int id_despacho,
    int id_temporada,
    String id_comercial,
    int id_unidad,
    int id_usuario,
    Date fecha_creacion,
    String nombre_recibe,
    String correo_recibe,
    String Anexo_recibe,
    int id_sistema,
    int id_prioridad,
    int estado) 
    {
        this.id_despacho = id_despacho;
        this.id_temporada = id_temporada;
        this.id_comercial = id_comercial;
        this.id_unidad = id_unidad;
        this.id_usuario = id_usuario;
        this.fecha_creacion = fecha_creacion;
        this.nombre_recibe = nombre_recibe;
        this.correo_recibe = correo_recibe;
        this.Anexo_recibe = Anexo_recibe;
        this.id_sistema = id_sistema;
        this.id_prioridad = id_prioridad;
        this.estado = estado;
    }

    public Despacho(Date fecha_creacion)
    {
        this.fecha_creacion = fecha_creacion;
    }
    
    public int getIdDespacho() {
        return id_despacho;
    }
    
    public void setIdDespacho(int id_despacho) {
        this.id_despacho = id_despacho;
    }
    
    public int getIdTemporada() {
        return id_temporada;
    }
    public void setIdTemporada(int id_temporada) {
        this.id_temporada = id_temporada;
    }

    public String getIdComercial() {
        return id_comercial;
    }
    public void setIdComercial(String id_comercial) {
        this.id_comercial = id_comercial;
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
    
    public Date getFechaCreacion() {
        return fecha_creacion;
    }
    public void setFechaCreacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    
    public String getNombreRecibe() {
        return nombre_recibe;
    }
    public void setNombreRecibe(String nombre_recibe) {
        this.nombre_recibe = nombre_recibe;
    }
    
    public String getCorreoRecibe() {
        return correo_recibe;
    }
    public void setCorreoRecibe(String nombre_recibe) {
        this.correo_recibe = correo_recibe;
    }
    
    public String getAnexoRecibe() {
        return Anexo_recibe;
    }
    public void setAnexo(String Anexo_recibe) {
        this.Anexo_recibe = Anexo_recibe;
    }
    
    public int getIdSistema() {
        return id_sistema;
    }
    public void setIdSistema(int id_sistema) {
        this.id_sistema = id_sistema;
    }
    
    public int getIdPrioridad() {
        return id_prioridad;
    }
    public void setIdPrioridad(int id_prioridad) {
        this.id_prioridad = id_prioridad;
    }
    
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}
