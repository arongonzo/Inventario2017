
package Entidades;

import java.sql.Date;
import javax.swing.JButton;

public class View_ReporteInicioTemporada {
    
    String temporada;
    int id_producto;
    String nsn_producto;
    String nombre;
    int cantidad;
    String descripcion_producto;
    String n_parte;
    int valor_unitario;
    String ubicacion;
    int estado;
    JButton btn;
    
    /**/
    public View_ReporteInicioTemporada() {
    }

    public String gettemporada() {
        return temporada;
    }

    public void settemporada(String temporada) {
        this.temporada = temporada;
    }
    
    public int getid_producto() {
        return id_producto;
    }
    
    public void setid_producto(int id_producto) {
        this.id_producto = id_producto;
    }
    
    public String getnsn_producto() {
        return nsn_producto;
    }

    public void setnsn_producto(String nsn_producto) {
        this.nsn_producto = nsn_producto;
    }
 
    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getcantidad() {
        return cantidad;
    }

    public void setcantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getdescripcion_producto() {
        return descripcion_producto;
    }

    public void setdescripcion_producto(String descripcion_producto) {
        this.descripcion_producto = descripcion_producto;
    }

    public String getn_parte() {
        return n_parte;
    }

    public void setn_parte(String n_parte) {
        this.n_parte = n_parte;
    }
    
    public int getvalor_unitario() {
        return valor_unitario;
    }

    public void setvalor_unitario(int valor_unitario) {
        this.valor_unitario = valor_unitario;
    }
    
    public String getubicacion() {
        return ubicacion;
    }

    public void setubicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public JButton getbtn() {
        return btn;
    }

    public void setbtn(JButton btn) {
        this.btn = btn;
    }
}
