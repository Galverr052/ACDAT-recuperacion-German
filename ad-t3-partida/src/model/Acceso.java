package model;

import java.sql.Date;

public class Acceso {
    private int idjugador;
    private int idpartida;
    private Date fhentrada;
    private Date fhsalida;

    public Acceso(int idjugador, int idpartida, Date fhentrada, Date fhsalida) {
        this.idjugador = idjugador;
        this.idpartida = idpartida;
        this.fhentrada = fhentrada;
        this.fhsalida = fhsalida;
    }

    public int getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(int idjugador) {
        this.idjugador = idjugador;
    }

    public int getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(int idpartida) {
        this.idpartida = idpartida;
    }

    public Date getFhentrada() {
        return fhentrada;
    }

    public void setFhentrada(Date fhentrada) {
        this.fhentrada = fhentrada;
    }

    public Date getFhsalida() {
        return fhsalida;
    }

    public void setFhsalida(Date fhsalida) {
        this.fhsalida = fhsalida;
    }

    @Override
    public String toString() {
        return "Acceso{" +
                "idjugador=" + idjugador +
                ", idpartida=" + idpartida +
                ", fhentrada=" + fhentrada +
                ", fhsalida=" + fhsalida +
                '}';
    }
}