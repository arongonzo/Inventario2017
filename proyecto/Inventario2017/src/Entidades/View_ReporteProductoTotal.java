package Entidades;

import java.sql.Date;

public class View_ReporteProductoTotal {
    
    int id_producto;
    String nsn_producto;
    String nombre;
    String descripcion_producto;
    int TotalProgramado;
    int TotalSolicitado;
    int Despachosrealizados;
    int Saldoinicio;
    int Saldostock;
    int StockFuturo;
    int elementosporllegar;
    int requerimientoduro;
    int stockseguridad;
    double valorunitario;
    
    public View_ReporteProductoTotal() {
    }
    
    public int getidproducto() {
        return id_producto;
    }
    public void setidproducto(int id_producto) {
        this.id_producto = id_producto;
    }
    
    public String getNsnproducto() {
        return nsn_producto;
    }
    public void setNsnproducto(String nsn_producto) {
        this.nsn_producto = nsn_producto;
    }
    
    public String getnombre() {
        return nombre;
    }
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getdescripcion_producto() {
        return descripcion_producto;
    }
    public void setdescripcion_producto(String descripcion_producto) {
        this.descripcion_producto = descripcion_producto;
    }
    
    public int getTotalProgramado() {
        return TotalProgramado;
    }
    public void setTotalProgramado(int TotalProgramado) {
        this.TotalProgramado = TotalProgramado;
    }
    
    public int getTotalSolicitado() {
        return TotalSolicitado;
    }
    public void setTotalSolicitado(int TotalSolicitado) {
        this.TotalSolicitado = TotalSolicitado;
    }
    
    public int getDespachosrealizados() {
        return Despachosrealizados;
    }
    public void setDespachosrealizados(int Despachosrealizados) {
        this.Despachosrealizados = Despachosrealizados;
    }
    
    public int getSaldoinicio() {
        return Saldoinicio;
    }
    public void setSaldoinicio(int Saldoinicio) {
        this.Saldoinicio = Saldoinicio;
    }
    
    public int getSaldostock() {
        return Saldostock;
    }
    public void setSaldostock(int Saldostock) {
        this.Saldostock = Saldostock;
    }
    
    public int getStockFuturo() {
        return StockFuturo;
    }
    public void setStockFuturo(int StockFuturo) {
        this.StockFuturo = StockFuturo;
    }
    
    public int getelementosporllegar() {
        return elementosporllegar;
    }
    public void setelementosporllegar(int elementosporllegar) {
        this.elementosporllegar = elementosporllegar;
    }
    
    public int getrequerimientoduro() {
        return requerimientoduro;
    }
    public void setrequerimientoduro(int requerimientoduro) {
        this.requerimientoduro = requerimientoduro;
    }
    
    public int getstockseguridad() {
        return stockseguridad;
    }
    public void setstockseguridad(int stockseguridad) {
        this.stockseguridad = stockseguridad;
    }
    
    public double getValorunitario() {
        return valorunitario;
    }
    public void setValorunitario(double valorunitario) {
        this.valorunitario = valorunitario;
    }
}
