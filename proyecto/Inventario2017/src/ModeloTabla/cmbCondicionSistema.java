
package ModeloTabla;

public class cmbCondicionSistema {
    String id_sistema;
    String nombre_sistema;
    
    public cmbCondicionSistema(String nombre_sistema, String id_sistema) {
        this.nombre_sistema = nombre_sistema;
        this.id_sistema = id_sistema;
    }
 
    public String getID() {
        return id_sistema;
    }
 
    public String toString() {
        return nombre_sistema;
    }
}
