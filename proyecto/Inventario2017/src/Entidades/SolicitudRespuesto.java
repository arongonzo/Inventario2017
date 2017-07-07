
package Entidades;


public class SolicitudRespuesto {
    int id_repuesto;
    int id_sistema;
    int id_unidad;
    
    String pista;
    String marca;
    String modelo;
    String fecha;
    
    int estado;
    
    public SolicitudRespuesto() {
    }

    public SolicitudRespuesto(int id_repuesto, 
            int id_sistema, 
            int id_unidad, 
            String pista,
            String marca,
            String modelo,
            String fecha,
            int estado) 
    {
        this.id_repuesto = id_repuesto;
        this.id_sistema = id_sistema;
        this.id_unidad = id_unidad;
        this.pista = pista;
        this.marca = marca;
        this.modelo = modelo;
        this.fecha = fecha;
        this.estado = estado;
    }

    
    public int getIdRespuesto() {
        return id_repuesto;
    }
    
    public void setIdRespuesto(int id_repuesto) {
        this.id_repuesto = id_repuesto;
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
    
    public String getfecha() {
        return fecha;
    }

    public void setfecha(String fecha) {
        this.fecha = fecha;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
