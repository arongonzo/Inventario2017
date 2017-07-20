package Entidades;

public class DespachoDetalle {
    int id_detalledespacho;
    int id_despacho;
    int id_producto;
    int cantidad;
    int precio;
    
    String nsn;
    String producto;
    String descripcion;
    String numeroparte;
    int cantidaddisponible;
    
    public DespachoDetalle() {
    }

    public DespachoDetalle(int id_detalledespacho,
        int id_despacho,
        int id_producto,
        int cantidad,
        int precio) 
    {
        this.id_detalledespacho = id_detalledespacho;
        this.id_despacho = id_despacho;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getIdDetalleDespacho() {
        return id_detalledespacho;
    }
    
    public void setIdDetalleDespacho(int id_detalledespacho) {
        this.id_detalledespacho = id_detalledespacho;
    }
    
    public int getIdDespacho() {
        return id_despacho;
    }
    
    public void setIdDespacho(int id_despacho) {
        this.id_despacho = id_despacho;
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
    
    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
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
