package iesmm.ad.spring_ej2.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Objects;

@JacksonXmlRootElement(localName = "persona")
public class Cliente {
    private String nombre;
    private String apellidos;
    private String poblacion;
    private long telefono;

    public Cliente(String nombre, String apellidos, String poblacion, long telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.poblacion = poblacion;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    //public boolean contieneTelefono(long t){
    //
    //}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return telefono == cliente.telefono && Objects.equals(poblacion, cliente.poblacion);
    }

}
