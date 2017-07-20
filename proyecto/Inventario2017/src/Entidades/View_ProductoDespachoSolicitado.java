
package Entidades;


public class View_ProductoDespachoSolicitado {
    int id_detallerepuesto;
    int id_solicitudRepuesto;
    int id_producto;
    int cantidad;
    
    String id_comercial;
    String nsn;
    String producto;
    String descripcion;
    String numeroparte;
    int cantidaddisponible;
    
    int id_sistema;
    int id_unidad;
    
    public View_ProductoDespachoSolicitado() {
    }

    public View_ProductoDespachoSolicitado(int id_ProductoSolicitado,
        int id_Solicitud,
        int id_producto,
        int cantidad,
        int id_sistema, 
        int id_unidad,
        String id_Comercial
        ) 
    {
        this.id_detallerepuesto = id_ProductoSolicitado;
        this.id_solicitudRepuesto = id_Solicitud;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.id_sistema = id_sistema;
        this.id_unidad = id_unidad;
        this.id_comercial = id_comercial;
    }
    
    public int getidProductoSolicitado() {
        return id_detallerepuesto;
    }
    
    public void setidProductoSolicitado(int id_detallerepuesto) {
        this.id_detallerepuesto = id_detallerepuesto;
    }
    
    public int getIdSolicitud() {
        return id_solicitudRepuesto;
    }
    
    public void setIdSolicitud(int id_Solicitud) {
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
    public int getIdSistema() {
        return id_sistema;
    }

    public void setIdSistema(int id_sistema) {
        this.id_sistema = id_sistema;
    }
 
    public int getIdUnidad() {
        return id_unidad;
    }

    public void setIdUnidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }
    
    public String getIdComercial() {
        return id_comercial;
    }

    public void setIdComercial(String id_comercial) {
        this.id_comercial = id_comercial;
    }
}
