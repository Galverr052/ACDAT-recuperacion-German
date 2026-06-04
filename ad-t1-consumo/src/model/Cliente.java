package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cliente implements Serializable {

    private String nif;
    private String nombre;
    private String apellido;
    private Date fechaAlta;
    private List<Contador> contadores = new ArrayList<>();

    public Cliente(String nif, String nombre, String apellido, Date fechaAlta, List<Contador> contadores) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaAlta = fechaAlta;
        this.contadores = contadores;
    }

    public float getFactura(Contador c, int anio, int mes){
        return c.getConsumo(mes) * c.getTarifaKwh();
    }

    public Contador[] getcontadores(){
        return  contadores.toArray(new Contador[0]);
    }

    public void addContador (Contador c){
        contadores.add(c);
    }

    public void delContador (Contador c){
        contadores.remove(c);
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
