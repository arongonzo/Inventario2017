
package Entidades;


public class Prioridad {
    int idprioridad;
    String nombre_prioridad;
    
    
    public Prioridad() {
    }

    public Prioridad(
        int id_prioridad, String nombre_prioridad) 
    {
        this.idprioridad = id_prioridad;
        this.nombre_prioridad = nombre_prioridad;
    }

    public int getIdPrioridad() {
        return idprioridad;
    }
    
    public void setIdPriodidad(int id_prioridad) {
        this.idprioridad = id_prioridad;
    }

    public String getNombre_Prioridad() {
        return nombre_prioridad;
    }

    public void setNombre_prioridad(String nombre_prioridad) {
        this.nombre_prioridad = nombre_prioridad;
    }
    
}
