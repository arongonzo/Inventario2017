
package Entidades;


public class Zonal {
    int idzonal;
    String nombre_zonal;
    String descripcion_zonal;
    
    public Zonal() {
    }

    public Zonal(
        int idzonal, String nombre_zonal, String descripcion_zonal) 
    {
        this.idzonal = idzonal;
        this.nombre_zonal = nombre_zonal;
        this.descripcion_zonal = descripcion_zonal;
    }

    public int getIdzonal() {
        return idzonal;
    }
    
    public void setIdzonal(int idzonal) {
        this.idzonal = idzonal;
    }

    public String getNombre_Zonal() {
        return nombre_zonal;
    }

    public void setZonal(String nombre_zonal) {
        this.nombre_zonal = nombre_zonal;
    }
    public String getDescripcion_Zonal() {
        return descripcion_zonal;
    }

    public void setDescripcion_Zonal(String descripcion_zonal) {
        this.descripcion_zonal = descripcion_zonal;
    }
    
}
