
package Entidades;


public class SolicitudProgramadaDetalle {
    int id_detalleprogramada;
    int id_programada;
    int id_producto;
    int cantidad;
    
    String nsn;
    String producto;
    String descripcion;
    String numeroparte;
    int cantidaddisponible;
    
    public SolicitudProgramadaDetalle() {
    }

    public SolicitudProgramadaDetalle(int id_detalleprogramada,
        int id_programada,
        int id_producto,
        int cantidad) 
    {
        this.id_detalleprogramada = id_detalleprogramada;
        this.id_programada = id_programada;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        
    }

    public int getIdProgramadaDetalle() {
        return id_detalleprogramada;
    }
    
    public void setIdProgramadaDetalle(int id_detalleprogramada) {
        this.id_detalleprogramada = id_detalleprogramada;
    }
    
    public int getIdProgramada() {
        return id_programada;
    }
    
    public void setIdProgramada(int id_programada) {
        this.id_programada = id_programada;
    }

    public int getIdProducto() {
        return id_producto;
    }

    public void setIdProducto(int id_producto) {
        this.id_producto = id_producto;
    }   
 
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getNsn() {
        return nsn;
    }

    public void setNsn(String nsn) {
        this.nsn = nsn;
    }

    public String getProduto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getNumeroparte() {
        return numeroparte;
    }

    public void setNumeroparte(String numeroparte) {
        this.numeroparte = numeroparte;
    }
    
    public int getCantidadDisponible() {
        return cantidaddisponible;
    }

    public void setCantidadDisponible(int cantidaddisponible) {
        this.cantidaddisponible = cantidaddisponible;
    }
    
}
