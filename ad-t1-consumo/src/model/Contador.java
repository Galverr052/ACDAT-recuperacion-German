package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contador implements Serializable {
    private String nif;
    private String cups;
    private float potenciaContratada;
    private String direccionSuministro;
    private float tarifaKwh;
    private float descuento;
    private Date fechaAlta;
    private List<Medicion> mediciones = new ArrayList<>();

    public Contador(String nif, String cups, float potenciaContratada, String direccionSuministro, float tarifaKwh, float descuento, Date fechaAlta) {
        this.nif = nif;
        this.cups = cups;
        this.potenciaContratada = potenciaContratada;
        this.direccionSuministro = direccionSuministro;
        this.tarifaKwh = tarifaKwh;
        this.descuento = descuento;
        this.fechaAlta = fechaAlta;
    }

    public float getConsumo(int mes){
        float total = 0;
        for (Medicion m : mediciones){
            if(m.getFecha().getMonth() == mes){
                total += m.getKwh();
            }
        }
        return total;
    }

    public float getConsumo(int mes, int hora){
        float total = 0;
        for (Medicion m : mediciones){
            if(m.getFecha().getMonth() == mes && m.getFecha().getHours() == hora){
                total += m.getKwh();
            }
        }
        return total;
    }

    public float getConsumoTotal(){
      float total = 0;
      for (Medicion m : mediciones){
          total += m.getKwh();
      }
      return total;
    }

    public void addMedicion(Medicion m){
        mediciones.add(m);
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCups() {
        return cups;
    }

    public void setCups(String cups) {
        this.cups = cups;
    }

    public float getPotenciaContratada() {
        return potenciaContratada;
    }

    public void setPotenciaContratada(float potenciaContratada) {
        this.potenciaContratada = potenciaContratada;
    }

    public String getDireccionSuministro() {
        return direccionSuministro;
    }

    public void setDireccionSuministro(String direccionSuministro) {
        this.direccionSuministro = direccionSuministro;
    }

    public float getTarifaKwh() {
        return tarifaKwh;
    }

    public void setTarifaKwh(float tarifaKwh) {
        this.tarifaKwh = tarifaKwh;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
    }
}
