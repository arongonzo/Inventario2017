package Entidades;


public class View_ProductosSolicitados {
    int id_ProductoSolicitado;
    int id_Solicitud;
    int id_producto;
    int cantidad;
    
    String nsn;
    String producto;
    String descripcion;
    String numeroparte;
    int cantidaddisponible;
    
    int id_sistema;
    int id_unidad;
    
    String pista;
    String marca;
    String modelo;
    String fecha;
    
    public View_ProductosSolicitados() {
    }

    public View_ProductosSolicitados(int id_ProductoSolicitado,
        int id_Solicitud,
        int id_producto,
        int cantidad,
        int id_sistema, 
        int id_unidad, 
        String pista,
        String marca,
        String modelo,
        String fecha) 
    {
        this.id_ProductoSolicitado = id_ProductoSolicitado;
        this.id_Solicitud = id_Solicitud;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.id_sistema = id_sistema;
        this.id_unidad = id_unidad;
        this.pista = pista;
        this.marca = marca;
        this.modelo = modelo;
        this.fecha = fecha;
    }
    
    public int getidProductoSolicitado() {
        return id_ProductoSolicitado;
    }
    
    public void setidProductoSolicitado(int id_ProductoSolicitado) {
        this.id_ProductoSolicitado = id_ProductoSolicitado;
    }
    
    public int getIdSolicitud() {
        return id_Solicitud;
    }
    
    public void setIdSolicitud(int id_Solicitud) {
        this.id_Solicitud = id_Solicitud;
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
    
    public String getpista() {
        return pista;
    }

    public void setpista(String pista) {
        this.pista = pista;
    }

    public String getmarca() {
        return marca;
    }

    public void setmarca(String marca) {
        this.marca = marca;
    }

    public String getmodelo() {
        return modelo;
    }

    public void setmodelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getfecha() {
        return fecha;
    }

    public void setfecha(String fecha) {
        this.fecha = fecha;
    }
}
