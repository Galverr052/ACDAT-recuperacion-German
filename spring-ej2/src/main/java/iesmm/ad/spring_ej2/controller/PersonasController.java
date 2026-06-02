package iesmm.ad.spring_ej2.controller;


import iesmm.ad.spring_ej2.model.Persona;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.*;

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

        for (Persona p : personas) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
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

    @PostMapping(value = "/mayor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Persona getPersonaMayor() {

        Persona mayor = null;

        for (Persona p : personas) {
            if (mayor == null || p.getEdad() > mayor.getEdad()) {
                mayor = p;
            }
        }

        return mayor;
    }

    /*
     * EJ4:
     * Calcular a partir de un parámetro, pasado por GET, para que devuelva:
     * la lista de personas ordenada o no por su edad o nombre en formato XML.
     *
     * Ejemplo:
     * http://localhost:8080/ws/personas/listapersonas?orden=edad
     * http://localhost:8080/ws/personas/listapersonas?orden=nombre
     *
     */

    @GetMapping(value = "/listapersonas" , produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public List<Persona> getListaPersonas(@RequestParam String orden){

        List<Persona> lista = new ArrayList<>(personas);

        if(orden.equalsIgnoreCase("edad")){
            personas.stream().sorted(Comparator.comparing(Persona::getEdad));
        } else if(orden.equalsIgnoreCase("nombre")){
            personas.stream().sorted(Comparator.comparing(Persona::getNombre));
        }
        return lista;
    }

    /*
     * EJ5: Búsqueda por coincidencia parcial e ignorando mayúsculas
     * A partir de una petición GET con un parámetro 'termino', buscar personas cuyo apellido
     * contenga ese texto, sin importar si se escribe en mayúsculas o minúsculas.
     * Devuelve la lista en formato JSON.
     *
     * Ejemplo:
     * http://localhost:8080/ws/personas/autocompletar?termino=gomez
     * http://localhost:8080/ws/personas/autocompletar?termino=An
     * (Debería devolver a "Ana Martínez", "Antonio Silva", etc.)
     */

    @GetMapping(value = "/autocompletar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Persona> getCoincidencia(@RequestParam String termino) {

        List<Persona> resultado = new ArrayList<>();

        for(Persona p: personas){
            if (p.getApellido().toLowerCase().contains(termino.toLowerCase()) | p.getNombre().toLowerCase().contains(termino.toLowerCase())){
                resultado.add(p);
            }
        }
        return resultado;
    }

    /*
     * EJ6: Filtrado por rango y formato XML
     * Calcular a partir de dos parámetros pasados por GET ('min' y 'max'), para que devuelva:
     * la lista de personas cuya edad esté en ese rango (ambos inclusive) en formato XML.
     *
     * Ejemplo:
     * http://localhost:8080/ws/personas/rango?min=20&max=40
     */

    @GetMapping(value = "/rango", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public List<Persona> getRango(@RequestParam int min, @RequestParam int max){
        List<Persona> resultado = new ArrayList<>();

        for (Persona p : personas){
            if(p.getEdad() >= min && p.getEdad()<= max){
                resultado.add(p);
            }
        }
        return resultado;
    }

    /*
     * EJ7: Estadísticas
     * Devolver, a partir de la siguiente petición POST, los datos estadísticos en formato JSON
     * del conjunto global de personas (total de personas, media de edad y la edad máxima).
     *
     * Ejemplo:
     * http://localhost:8080/ws/personas/estadisticas
     *
     * Devuelve:
     * {"total": 5, "edadMedia": 38.5, "edadMaxima": 89}
     */

    @PostMapping("/estadisticas")
    @ResponseBody
    public Map<String, Object> Getestadisticas(){
        int total = personas.size();
        int suma = 0;
        int max = Integer.MIN_VALUE;

        for (Persona p: personas){
            suma += p.getEdad();

            if(p.getEdad() > max){
                max = p.getEdad();
            }
        }

        double media = total > 0 ? (double) suma / total : 0;

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("total", total);
        resultado.put("media", media);
        resultado.put("edadMaxima", max);

        return resultado;
    }

    /*
     * EJ8: Filtrado combinado
     * A partir de una petición GET, permitir filtrar de manera opcional por apellido (coincidencia parcial) Y/O por edad máxima.
     * Si no se envía el apellido, no se filtra por texto. Si no se envía la edad máxima, se asume que no hay límite.
     * Devolver la lista en formato XML.
     * Ejemplo:
     * http://localhost:8080/ws/personas/buscar?apellido=silva&edadmax=30
     * (Devuelve personas que se apelliden Silva y tengan 30 años o menos).
     */


    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public List<Persona> buscar(@RequestParam String apellido, @RequestParam Integer edadmax){

        List<Persona> resultado = new ArrayList<>();

        for (Persona p: personas) {
            Boolean ok = true;

            if (apellido !=null && !p.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
                ok = false;
            }

            if (edadmax != null && p.getEdad() > edadmax){
                ok = false;
            }

            if (ok){
                resultado.add(p);
            }
        }
        return resultado;
    }
}
