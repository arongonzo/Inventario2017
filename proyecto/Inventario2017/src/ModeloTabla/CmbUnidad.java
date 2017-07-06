
package ModeloTabla;


public class CmbUnidad {
    String id_unidad;
    String nombre_unidad;
    
    public CmbUnidad(String nombre_unidad, String id_unidad) {
        this.nombre_unidad = nombre_unidad;
        this.id_unidad = id_unidad;
    }
 
    public String getID() {
        return id_unidad;
    }
 
    public String toString() {
        return nombre_unidad;
    }
}
