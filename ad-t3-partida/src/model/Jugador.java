package model;

import java.sql.Date;

public class Jugador {
    private int idjugador;
    private int dni;
    private String nombre;
    private String iban;
    private float cuota;
    private Date falta;

    public Jugador(int idjugador, int dni, String nombre, String iban, float cuota, Date falta) {
        this.idjugador = idjugador;
        this.dni = dni;
        this.nombre = nombre;
        this.iban = iban;
        this.cuota = cuota;
        this.falta = falta;
    }

    public int getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(int idjugador) {
        this.idjugador = idjugador;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public float getCuota() {
        return cuota;
    }

    public void setCuota(float cuota) {
        this.cuota = cuota;
    }

    public Date getFalta() {
        return falta;
    }

    public void setFalta(Date falta) {
        this.falta = falta;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "idjugador=" + idjugador +
                ", dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", iban='" + iban + '\'' +
                ", cuota=" + cuota +
                ", falta=" + falta +
                '}';
    }
}