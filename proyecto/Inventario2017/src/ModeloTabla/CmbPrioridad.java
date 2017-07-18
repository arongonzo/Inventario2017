
package ModeloTabla;

public class CmbPrioridad {
    
    String id_prioridad;
    String nombre_prioridad;
    
    public CmbPrioridad(String nombre_prioridad, String id_prioridad) {
        this.nombre_prioridad = nombre_prioridad;
        this.id_prioridad = id_prioridad;
    }
 
    public String getID() {
        return id_prioridad;
    }
 
    public String toString() {
        return nombre_prioridad;
    }
}
