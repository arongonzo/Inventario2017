package Entidades;

public class SolicitudRepuestoDetalle {
    int id_detalleRepuesto;
    int id_solicitudRepuesto;
    int id_producto;
    int cantidad;
    
    String nsn;
    String producto;
    String descripcion;
    String numeroparte;
    int cantidaddisponible;
    
    public SolicitudRepuestoDetalle() {
    }

    public SolicitudRepuestoDetalle(int id_detalleRepuesto,
        int id_soliciudRepuesto,
        int id_producto,
        int cantidad) 
    {
        this.id_detalleRepuesto = id_detalleRepuesto;
        this.id_solicitudRepuesto = id_soliciudRepuesto;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    public int getIdRepuestoDetalle() {
        return id_detalleRepuesto;
    }
    
    public void setIdRepuestoDetalle(int id_repuestodetalle) {
        this.id_detalleRepuesto = id_repuestodetalle;
    }
    
    public int getIdSolicitudRepuesto() {
        return id_solicitudRepuesto;
    }
    
    public void setIdSolicitudRepuesto(int id_solicitudRepuesto) {
        this.id_solicitudRepuesto = id_solicitudRepuesto;
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
