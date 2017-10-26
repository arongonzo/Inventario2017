
package Entidades;

import java.sql.Date;
import javax.swing.JButton;

public class View_ReporteSolicitudRepuesto {
    int id_solicitudRepuesto;
    int id_temporada;
    String id_comercial;
    String zonal;
    String unidad;
    int id_usuario;
    Date fecha_creacion;
    String nombre_tecnico;
    String correo_electronico;
    String Anexo;
    String sistema;
    String marca_equipo;
    int id_condicionsistema;
    String modelo_equipo;
    String numero_Serie;
    int id_fallacomponente;
    String fallacomponente;
    String sintoma_falla;
    String solicutus_orden_trabajo;
    Date fecha_solicitud;
    int id_prioridad;
    String DetallePrioridad;
    int estado;
    JButton btn;
    
    public View_ReporteSolicitudRepuesto() {
    }
        
    public int getIdSolicitudRepuesto() {
        return id_solicitudRepuesto;
    }
    
    public void setIdSolicitudRepuesto(int id_solicitudRepuesto) {
        this.id_solicitudRepuesto = id_solicitudRepuesto;
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
    
    public Date getFechaCreacion() {
        return fecha_creacion;
    }
    public void setFechaCreacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    
    public String getNombreTecnico() {
        return nombre_tecnico;
    }
    public void setNombreTecnico(String nombre_tecnico) {
        this.nombre_tecnico = nombre_tecnico;
    }
    
    public String getCorreoElectronico() {
        return correo_electronico;
    }
    public void setCorreoElectronico(String nombre_tecnico) {
        this.correo_electronico = correo_electronico;
    }
    
    public String getAnexo() {
        return Anexo;
    }
    public void setAnexo(String Anexo) {
        this.Anexo = Anexo;
    }
    
    public String getSistema() {
        return sistema;
    }
    public void setSistema(String sistema) {
        this.sistema = sistema;
    }
            
    public String getMarcaEquipo() {
        return marca_equipo;
    }
    public void setMarcaEquipo(String marca_equipo) {
        this.marca_equipo = marca_equipo;
    }
    
    public int getIdCondicionSistema() {
        return id_condicionsistema;
    }
    public void setIdCondicionSistema(int id_condicionsistema) {
        this.id_condicionsistema = id_condicionsistema;
    }
    
    public String getModeloEquipo() {
        return modelo_equipo;
    }
    public void setModeloEquipo(String modelo_equipo) {
        this.modelo_equipo = modelo_equipo;
    }
    
    public String getNumeroSerie() {
        return numero_Serie;
    }
    public void setNumeroSerie(String numero_Serie) {
        this.numero_Serie = numero_Serie;
    }
    
    public int getIdFallaComponente() {
        return id_fallacomponente;
    }
    public void setIdFallaComponente(int id_fallacomponente) {
        this.id_fallacomponente = id_fallacomponente;
    }
 
    public String getFallaComponente() {
        return fallacomponente;
    }
    
    public void setFallaComponente(String fallacomponente) {
        this.fallacomponente = fallacomponente;
    }
    
    public String getSintomaFalla() {
        return sintoma_falla;
    }
    public void setSintomaFalla(String sintoma_falla) {
        this.sintoma_falla = sintoma_falla;
    }
    
    public String getSolicutusOrdenTrabajo() {
        return solicutus_orden_trabajo;
    }
    public void setSolicutusOrdenTrabajo(String solicutus_orden_trabajo) {
        this.solicutus_orden_trabajo = solicutus_orden_trabajo;
    }
    
    public Date getFechaSolicitud() {
        return fecha_solicitud;
    }
    public void setFechaSolicitud(Date fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }
    
    public int getIdPrioridad() {
        return id_prioridad;
    }
    public void setIdPrioridad(int id_prioridad) {
        this.id_prioridad = id_prioridad;
    }
    
    public String getDetallePrioridad() {
        return DetallePrioridad;
    }
    public void setDetallePrioridad(String DetallePrioridad) {
        this.DetallePrioridad = DetallePrioridad;
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
