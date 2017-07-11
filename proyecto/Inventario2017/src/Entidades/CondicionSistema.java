
package Entidades;

    

public class CondicionSistema {
    int id;
    String nombre;
    String descripcion;
    
    public CondicionSistema() {
    }

    public CondicionSistema(
            int id_, 
            String nombre_, 
            String descripcion_) 
    {
        this.id = id_;
        this.nombre = nombre_;
        this.descripcion = descripcion_;
    }

    public int getIdCondicionSistema() {
        return id;
    }
    
    public void setIdCondicionSistema(int id_) {
        this.id = id_;
    }

    public String getNombreCondicionSistema() {
        return nombre;
    }

    public void setNombreCondicionSistema(String nombre_) {
        this.nombre = nombre_;
    }
    public String getDescripcionCondicionSistema() {
        return descripcion;
    }

    public void setDescripcionCondicionSistema(String descripcion_) {
        this.descripcion = descripcion_;
    }
}
