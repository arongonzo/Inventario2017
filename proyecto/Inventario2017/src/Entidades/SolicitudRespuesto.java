
package Entidades;


public class SolicitudRespuesto {
    int id_solicitudRepuesto;
    int id_temporada;
    String id_comercial;
    int id_unidad;
    String fecha_creacion;
    String nombre_tecnico;
    String correo_electronico;
    String Anexo;
    int id_sistema;
    String marca_equipo;
    int id_condicionsistema;
    String modelo_equipo;
    String numero_Serie;
    int id_fallacomponente;
    String sintoma_falla;
    String solicutus_orden_trabajo;
    String fecha_solicitud;
    int id_prioridad;
    int id_prioridad_normal;
    int id_propiedad_urgente;
    int estado;
	
    
    public SolicitudRespuesto() {
    }

    public SolicitudRespuesto(int id_solicitudRepuesto,
    int id_temporada,
    String id_comercial,
    int id_unidad,
    String fecha_creacion,
    String nombre_tecnico,
    String correo_electronico,
    String Anexo,
    int id_sistema,
    String marca_equipo,
    int id_condicionsistema,
    String modelo_equipo,
    String numero_Serie,
    int id_fallacomponente,
    String sintoma_falla,
    String solicutus_orden_trabajo,
    String fecha_solicitud,
    int id_prioridad,
    int id_prioridad_normal,
    int id_propiedad_urgente,
    int estado) 
    {
        this.id_solicitudRepuesto = id_solicitudRepuesto;
        this.id_temporada = id_temporada;
        this.id_comercial = id_comercial;
        this.id_unidad = id_unidad;
        this.fecha_creacion = fecha_creacion;
        this.nombre_tecnico = nombre_tecnico;
        this.correo_electronico = correo_electronico;
        this.Anexo = Anexo;
        this.id_sistema = id_sistema;
        this.marca_equipo = marca_equipo;
        this.id_condicionsistema = id_condicionsistema;
        this.modelo_equipo = modelo_equipo;
        this.numero_Serie  = numero_Serie;
        this.id_fallacomponente = id_fallacomponente;
        this.sintoma_falla = sintoma_falla;
        this.solicutus_orden_trabajo = solicutus_orden_trabajo;
        this.fecha_solicitud = fecha_solicitud;
        this.id_prioridad = id_prioridad;
        this.id_prioridad_normal = id_prioridad_normal;
        this.id_propiedad_urgente = id_propiedad_urgente;
        this.estado = estado;
    }

    
    public int getIdSolicitudRepuesto() {
        return id_solicitudRepuesto;
    }
    
    public void setIdSolicitudRepuesto(int id_solicitudRepuesto) {
        this.id_solicitudRepuesto = id_solicitudRepuesto;
    }

    
}
