package iesmm.ad.spring_ej2.controller;


import iesmm.ad.spring_ej2.model.Persona;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("ws/personas")
public class PersonasController {

    private ArrayList<Persona> personas;

    public PersonasController(){
        System.out.println("Controller arrancado");

        personas = new ArrayList<>();

        personas.add(new Persona("German", "Alvarenga", 24));
        personas.add(new Persona("Isaac", "Erroa", 24));
        personas.add(new Persona("Raul", "Prieto", 21));
    }

    @GetMapping(value = "/persona", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Persona getPersona() {
        return new Persona("manolo", "sosa", 45);

      /*
      Devuelve:
          {"nombre": "manolo", "apellidos": "sosa", "edad": 45}
       */
    }

    @GetMapping(value = "/persona/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Persona getPersonaXML() {
        return this.personas.get(0);

      /* Devuelve:
          <persona><nombre>silvia</nombre><apellidos>martín</apellidos><edad>15</edad></persona>
       */
    }

    @GetMapping(value = "/persona/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Persona getPersonaJson(@RequestParam String nombre) {

       Persona persona = null;

        if (persona.getNombre().equals(nombre)){

        }
        return null;
    }


    /*
     * EJ2:
     * Devolver, a partir de la siguiente petición GET, las personas mayores de edad en formato XML.
     *
     * Ejemplo:
     * http://localhost:8080/ws/personas/mayores
     */


    @GetMapping(value = "/mayores", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public List<Persona> getMayoresEdad() {

        List<Persona> mayoresEdad = new ArrayList<>();

        for (Persona p: personas) {

            if (p.getEdad() >= 18){
                mayoresEdad.add(p);
            }
        }

        return mayoresEdad;
    }

    @PostMapping(value = "/persona/mayor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Persona getPersonaJson() {

       personas.sort(Comparator.comparing(Persona::getEdad));

        return personas;

    }


}
