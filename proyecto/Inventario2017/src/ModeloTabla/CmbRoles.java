package ModeloTabla;

public class CmbRoles {
 
    String id_rol;
    String nombre_rol;
 
    public CmbRoles(String nombre_rol, String id_rol) {
        this.nombre_rol = nombre_rol;
        this.id_rol = id_rol;
    }
 
    public String getID() {
        return id_rol;
    }
 
    public String toString() {
        return nombre_rol;
    }
}