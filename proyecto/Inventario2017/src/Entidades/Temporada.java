
package Entidades;


public class Temporada {
    int idtemporada;
    String nombretemporada;
    int activa;
    String activaTexto;
    
    public Temporada() {
    }

    public Temporada(int id_temporada, String nombre_temporada, int activa) {
        this.idtemporada = id_temporada;
        this.nombretemporada = nombre_temporada;
        this.activa = activa;
    }

    public int getIdTemporada() {
        return idtemporada;
    }
    
    public void setIdTemporada(int id_temporada) {
        this.idtemporada = id_temporada;
    }

    public String getNombreTemporada() {
        return nombretemporada;
    }

    public void setNombreTemporada(String nombre_temporada) {
        this.nombretemporada = nombre_temporada;
    }
    public int getActiva() {
        return activa;
    }
    
    public void setActiva(int activa) {
        this.activa = activa;
    }
    
    public String getActivaTexto() {
        return activaTexto;
    }
    public void setActivaTexto(String activatexto) {
        this.activaTexto = activatexto;
    }
}
