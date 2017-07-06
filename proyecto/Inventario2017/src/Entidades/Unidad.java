
package Entidades;


public class Unidad {
    int idunidad;
    int idzonal;
    String nombre;
    String nombre_zonal;
    
    public Unidad() {
    }

    public Unidad(int idunidad, int idzonal, String nombre) {
        this.idunidad = idunidad;
        this.idzonal = idzonal;
        this.nombre = nombre;
    }

    
    public int getIdunidad() {
        return idunidad;
    }
    
    public void setIdUnidad(int idunidad) {
        this.idunidad = idunidad;
    }

    public int getIdzonal() {
        return idzonal;
    }

    public void setIdzonal(int idzonal) {
        this.idzonal = idzonal;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreZonal() {
        return nombre_zonal;
    }

    public void setNombreZonal(String nombre_zonal) {
        this.nombre_zonal = nombre_zonal;
    }
    
}
