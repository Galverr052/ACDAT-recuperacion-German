package model;

import java.io.Serializable;
import java.util.Date;

public class Medicion implements Serializable {

    private Date fecha;
    private int hora;
    private float Kwh;

    public Medicion(Date fecha, int hora, float kwh) {
        this.fecha = fecha;
        this.hora = hora;
        Kwh = kwh;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public float getKwh() {
        return Kwh;
    }

    public void setKwh(float kwh) {
        Kwh = kwh;
    }
}
