
package Entidades;

public class Sistema {

    int idsistema;
    String nombre_sistema;
    String descripcion_sistema;
    
    public Sistema() {
    }

    public Sistema(
            int idsistema, 
            String nombre_sistema, 
            String descripcion_sistema) 
    {
        this.idsistema = idsistema;
        this.nombre_sistema = nombre_sistema;
        this.descripcion_sistema = descripcion_sistema;
    }

    public int getIdsistema() {
        return idsistema;
    }
    
    public void setIdsistema(int idsistema) {
        this.idsistema = idsistema;
    }

    public String getNombre_Sistema() {
        return nombre_sistema;
    }

    public void setNombre_Sistema(String nombre_sistema) {
        this.nombre_sistema = nombre_sistema;
    }
    public String getDescripcion_Sistema() {
        return descripcion_sistema;
    }

    public void setDescripcion_Sistema(String descripcion_sistema) {
        this.descripcion_sistema = descripcion_sistema;
    }
    
}
