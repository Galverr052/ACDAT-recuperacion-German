package iesmm.ad.ad_recuperacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/ws2")
public class ws2 {

    private final Logger log = Logger.getLogger("ad");

    public ws2() {
        log.log(Level.INFO, "WS2 creado: " + this);
    }


    // BUSCAR POR COINCIDENCIA EXACTA IGNORANDO MAYÚSCULAS

    @GetMapping("/buscar/exacto")
    @ResponseBody
    public String buscarExacto(@RequestParam String nombre) {

        List<String> lista = List.of("Ana", "Luis", "Carlos", "Maria");

        for (String n : lista) {
            if (n.equalsIgnoreCase(nombre)) {
                return "Encontrado: " + n;
            }
        }

        return "No encontrado";
    }


    //  FILTRAR PALABRAS QUE EMPIEZAN POR LETRA

    @GetMapping("/empiezan")
    @ResponseBody
    public String empiezan(@RequestParam char letra) {

        List<String> lista = List.of("Ana", "Antonio", "Carlos", "Alberto", "Luis");

        String r = "";

        for (String n : lista) {
            if (Character.toLowerCase(n.charAt(0)) == Character.toLowerCase(letra)) {
                r += n + " ";
            }
        }

        return r.isEmpty() ? "Ninguno" : r;
    }


    // CONTAR CUÁNTOS MAYORES Y MENORES DE EDAD

    @GetMapping("/contar/edad")
    @ResponseBody
    public String contarEdad() {

        int[] edades = {12, 18, 25, 30, 16, 40};

        int mayores = 0;
        int menores = 0;

        for (int e : edades) {
            if (e >= 18) mayores++;
            else menores++;
        }

        return "Mayores: " + mayores + " | Menores: " + menores;
    }


    // INVERTIR UNA FRASE

    @GetMapping("/invertir")
    @ResponseBody
    public String invertir(@RequestParam String frase) {

        String resultado = "";

        for (int i = frase.length() - 1; i >= 0; i--) {
            resultado += frase.charAt(i);
        }

        return resultado;
    }


    // PALÍNDROMO (nivel típico examen)

    @GetMapping("/palindromo")
    @ResponseBody
    public String palindromo(@RequestParam String texto) {

        String limpio = texto.toLowerCase();
        String invertido = "";

        for (int i = limpio.length() - 1; i >= 0; i--) {
            invertido += limpio.charAt(i);
        }

        if (limpio.equals(invertido)) {
            return "Es palíndromo";
        }

        return "No es palíndromo";
    }


    //SEGUNDO MAYOR NÚMERO

    @GetMapping("/segundo/max")
    @ResponseBody
    public String segundoMax() {

        int[] nums = {10, 5, 30, 20, 30};

        int max = Integer.MIN_VALUE;
        int segundo = Integer.MIN_VALUE;

        for (int n : nums) {

            if (n > max) {
                segundo = max;
                max = n;
            } else if (n > segundo && n != max) {
                segundo = n;
            }
        }

        return "Segundo max: " + segundo;
    }

    // SOLO NÚMEROS PARES EN UN RANGO

    @GetMapping("/sumar/pares")
    @ResponseBody
    public String sumarPares(@RequestParam int min, @RequestParam int max) {

        int suma = 0;

        for (int i = min; i <= max; i++) {
            if (i % 2 == 0) {
                suma += i;
            }
        }

        return "Suma pares: " + suma;
    }


    //  FRECUENCIA DE UNA LETRA CON MAP

    @GetMapping("/frecuencia")
    @ResponseBody
    public Map<Character, Integer> frecuencia(@RequestParam String texto) {

        Map<Character, Integer> mapa = new HashMap<>();

        for (char c : texto.toLowerCase().toCharArray()) {

            if (c != ' ') {
                mapa.put(c, mapa.getOrDefault(c, 0) + 1);
            }
        }

        return mapa;
    }

    // FILTRAR NÚMEROS MAYORES QUE UN VALOR

    @GetMapping("/filtrar")
    @ResponseBody
    public List<Integer> filtrar(@RequestParam int min) {

        List<Integer> lista = List.of(5, 10, 15, 20, 25);

        List<Integer> resultado = new ArrayList<>();

        for (int n : lista) {
            if (n > min) {
                resultado.add(n);
            }
        }

        return resultado;
    }


    //   CONTAR VOCALES
    @GetMapping("/vocales")
    @ResponseBody
    public String vocales(@RequestParam String texto) {

        int count = 0;

        for (char c : texto.toLowerCase().toCharArray()) {

            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }

        return "Vocales: " + count;
    }

    @GetMapping("/cambiar/vocal")
    @ResponseBody
    public String cambiarVocal(
            @RequestParam String frase,
            @RequestParam char vocal) {

        String resultado = "";

        for (char c : frase.toCharArray()) {

            if ("aeiouAEIOU".indexOf(c) >= 0) {
                resultado += vocal;
            } else {
                resultado += c;
            }
        }

        return resultado;
    }

    @GetMapping("/vocales/todas")
    @ResponseBody
    public String vocalesTodas(@RequestParam String frase) {

        String vocales = "aeiou";

        String resultado = "";

        for (char v : vocales.toCharArray()) {

            String nueva = "";

            for (char c : frase.toCharArray()) {

                if ("aeiouAEIOU".indexOf(c) >= 0) {
                    nueva += v;
                } else {
                    nueva += c;
                }
            }

            resultado += nueva + "<br>";
        }

        return resultado;
    }

    @GetMapping("/orden-mayor-menor")
    @ResponseBody
    public String ordenMayorMenor(@RequestParam int numero) {

        String texto = String.valueOf(numero);

        char mayor = texto.charAt(0);
        char menor = texto.charAt(0);

        for (char c : texto.toCharArray()) {

            if (c > mayor) {
                mayor = c;
            }

            if (c < menor) {
                menor = c;
            }
        }

        int posMayor = texto.indexOf(mayor);
        int posMenor = texto.indexOf(menor);

        if (posMayor < posMenor) {
            return mayor + "," + menor;
        }

        return menor + "," + mayor;
    }

}