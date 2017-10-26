
package Entidades;

import java.sql.Date;
import javax.swing.JButton;

public class View_ReporteDespacho {
    int id_despacho;
    int id_temporada;
    String id_comercial;
    int id_usuario;
    int id_unidad;
    int id_sistema;
    String usuario;
    String zonal;
    String unidad;
    String sistema;
    Date fecha_creacion;
    int id_prioridad;
    String prioridad;
    String nombre_recibe;
    String correo_recibe;
    String Anexo_recibe;
    
    int estado;
    JButton btn;
    
    public View_ReporteDespacho() {
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
    
    public String getZonal() {
        return zonal;
    }
    public void setZonal(String zonal) {
        this.zonal = zonal;
    }
    
    public String getUnidad() {
        return unidad;
    }
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getIdUsuario() {
        return id_usuario;
    }
    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
    
    public String getSistema() {
        return sistema;
    }
    public void setSistema(String id_sistema) {
        this.sistema = sistema;
    }
    
    
    public int getIdPrioridad() {
        return id_prioridad;
    }
    public void setIdPrioridad(int id_prioridad) {
        this.id_prioridad = id_prioridad;
    }
    
    public String getPrioridad() {
        return prioridad;
    }
    public void setIdPrioridad(String prioridad) {
        this.prioridad = prioridad;
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
