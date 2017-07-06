package ModeloTabla;


public class CmbZonal {
    String id_zona;
    String nombre_zona;
    
    public CmbZonal(String nombre_zona, String id_zona) {
        this.nombre_zona = nombre_zona;
        this.id_zona = id_zona;
    }
 
    public String getID() {
        return id_zona;
    }
 
    public String toString() {
        return nombre_zona;
    }
}
