package model;



public class Partida {
    private int idpartida;
    private String nombre;

    public Partida(int idpartida, String nombre) {
        this.idpartida = idpartida;
        this.nombre = nombre;
    }

    public int getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(int idpartida) {
        this.idpartida = idpartida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "idpartida=" + idpartida +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}