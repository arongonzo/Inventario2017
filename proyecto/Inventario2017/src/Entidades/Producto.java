
package Entidades;

public class Producto {
    int idproducto;
    String nsn;
    String nombre;
    String descripcion;
    String n_parte;
    int cantidad;
    int valor_unitario;
    String ubicacion;
    String ubicacion_detalle;
    String anio_ingreso;
    String anio_salida;
    int estado;

    public Producto() {
    }

    public Producto(
        int idproducto, String nsn, String nombre,String descripcion, String n_parte, int cantidad, int valor_unitario, String ubicacion, String ubicacion_detalle, String anio_ingreso, String anio_salida, int estado) 
    {
        this.idproducto = idproducto;
        this.nsn = nsn;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.n_parte = n_parte;
        this.cantidad = cantidad;
        this.valor_unitario = valor_unitario;
        this.ubicacion = ubicacion;
        this.ubicacion_detalle = ubicacion_detalle;
        this.anio_ingreso = anio_ingreso;
        this.anio_salida = anio_salida;
        this.estado = estado;
    }

    
    public int getIdproducto() {
        return idproducto;
    }
    
    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNsn() {
        return nsn;
    }

    public void setNsn(String nsn) {
        this.nsn = nsn;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nobmre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getN_parte() {
        return n_parte;
    }

    public void setN_parte(String n_parte) {
        this.n_parte = n_parte;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(int valor_unitario) {
        this.valor_unitario = valor_unitario;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public String getUbicacion_detalle() {
        return ubicacion;
    }

    public void setUbicacion_detalle(String ubicacion_detalle) {
        this.ubicacion_detalle = ubicacion_detalle;
    }
    
    public String getAnio_ingreso() {
        return anio_ingreso;
    }

    public void setAnio_ingreso(String anio_ingreso) {
        this.anio_ingreso = anio_ingreso;
    }
    
    public String getAnio_salida() {
        return anio_ingreso;
    }

    public void setAnio_salida(String anio_salida) {
        this.anio_salida = anio_salida;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}