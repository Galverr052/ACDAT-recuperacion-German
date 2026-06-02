package iesmm.ad.ad_recuperacion.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ws3")
public class ws3 {


    // VALIDACIÓN + RANGO + SALIDA FORMATEADA

    @GetMapping("/pares/rango")
    @ResponseBody
    public String paresRango(@RequestParam String min, @RequestParam String max) {

        int nMin, nMax;

        // conversión segura
        try {
            nMin = Integer.parseInt(min);
            nMax = Integer.parseInt(max);
        } catch (Exception e) {
            return "Error de formato";
        }

        // validación
        if (nMin > nMax) {
            return "min no puede ser mayor que max";
        }

        String result = "";

        // recorrer rango
        for (int i = nMin; i <= nMax; i++) {

            if (i % 2 == 0) {
                result += i + " ";
            }
        }

        return result;
    }


    // BINARIO A DECIMAL (CORRECTO)

    @PostMapping("/binario/{bin:[01]+}")
    @ResponseBody
    public String binToDec(@PathVariable String bin) {

        long resultado = 0;

        // recorrer desde el final (potencias correctas)
        for (int i = 0; i < bin.length(); i++) {

            if (bin.charAt(bin.length() - 1 - i) == '1') {
                resultado += Math.pow(2, i);
            }
        }

        return String.valueOf(resultado);
    }


    // 3. FILTRADO + ORDENACIÓN (CLÁSICO EXAMEN)

    @GetMapping(value = "/clientes/pob", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> clientesPorPob(@RequestParam String pob) {

        List<String> clientes = List.of(
                "Ana - Sevilla",
                "Luis - Carmona",
                "Maria - Sevilla",
                "Jose - Malaga"
        );

        List<String> resultado = new ArrayList<>();

        // filtro ignore case
        for (String c : clientes) {
            if (c.toLowerCase().contains(pob.toLowerCase())) {
                resultado.add(c);
            }
        }

        // ordenación alfabética
        resultado.sort(String::compareTo);

        return resultado;
    }


    // 4. CONTAR DIGITOS EN UN NUMERO

    @GetMapping("/contar/digitos/{num:\\d+}")
    @ResponseBody
    public String contarDigitos(@PathVariable String num) {

        int pares = 0;
        int impares = 0;

        for (char c : num.toCharArray()) {

            int n = c - '0';

            if (n % 2 == 0) {
                pares++;
            } else {
                impares++;
            }
        }

        return "pares: " + pares + " impares: " + impares;
    }

    // 5. MAYOR Y MENOR EN UNA LISTA

    @GetMapping("/minmax")
    @ResponseBody
    public String minMax() {

        int[] nums = {12, 5, 9, 30, 1};

        int min = nums[0];
        int max = nums[0];

        for (int n : nums) {

            if (n > max) {
                max = n;
            }

            if (n < min) {
                min = n;
            }
        }

        return "min: " + min + " max: " + max;
    }


    // 6. BUSCAR PREFIJO (AUTOCOMPLETE)

    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> autocomplete(@RequestParam String prefijo) {

        List<String> nombres = List.of("Antonio", "Ana", "Alberto", "Carlos", "Andres");

        List<String> resultado = new ArrayList<>();

        for (String n : nombres) {

            if (n.toLowerCase().startsWith(prefijo.toLowerCase())) {
                resultado.add(n);
            }
        }

        return resultado;
    }


    // FILTRADO CON DOS CONDICIONES OPCIONALES

    @GetMapping("/filtrar")
    @ResponseBody
    public List<Integer> filtrar(
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {

        List<Integer> nums = List.of(5, 10, 15, 20, 25, 30);

        List<Integer> resultado = new ArrayList<>();

        for (int n : nums) {

            boolean ok = true;

            if (min != null && n < min) {
                ok = false;
            }

            if (max != null && n > max) {
                ok = false;
            }

            if (ok) {
                resultado.add(n);
            }
        }

        return resultado;
    }


    // 8. CONTAR PALABRAS EN STRING

    @GetMapping("/palabras")
    @ResponseBody
    public String palabras(@RequestParam String frase) {

        String[] partes = frase.trim().split(" ");

        return "total palabras: " + partes.length;
    }


    //  ELIMINAR REPETIDOS EN STRING

    @GetMapping("/unicos")
    @ResponseBody
    public String unicos(@RequestParam String texto) {

        String resultado = "";

        for (char c : texto.toCharArray()) {

            if (!resultado.contains(String.valueOf(c))) {
                resultado += c;
            }
        }

        return resultado;
    }


    // 10. SUMA DE CIFRAS

    @GetMapping("/suma/cifras/{num:\\d+}")
    @ResponseBody
    public String sumaCifras(@PathVariable String num) {

        int suma = 0;

        for (char c : num.toCharArray()) {
            suma += c - '0';
        }

        return "suma: " + suma;
    }

    //multiplos
    @GetMapping("/multiplos")
    @ResponseBody
    public String multiplos(@RequestParam String min, @RequestParam String max) {

        int nMin, nMax;

        // conversión segura
        try {
            nMin = Integer.parseInt(min);
            nMax = Integer.parseInt(max);
        } catch (Exception e) {
            return "Error de formato";
        }

        // validación rango
        if (nMin > nMax) {
            return "Rango inválido";
        }

        String r = "";

        // recorrer y filtrar múltiplos de 3
        for (int i = nMin; i <= nMax; i++) {
            if (i % 3 == 0) {
                r += i + " ";
            }
        }

        return r;
    }

    //palabra larga
    @GetMapping("/larga")
    @ResponseBody
    public String larga(@RequestParam String frase) {

        String[] partes = frase.split(" ");
        String max = "";

        for (String p : partes) {
            if (p.length() > max.length()) {
                max = p;
            }
        }

        return max;
    }

    //binario a decimal
    @PostMapping("/bin2dec/{bin:[01]+}")
    @ResponseBody
    public String bin2dec(@PathVariable String bin) {

        long result = 0;

        for (int i = 0; i < bin.length(); i++) {

            if (bin.charAt(bin.length() - 1 - i) == '1') {
                result += Math.pow(2, i);
            }
        }

        return String.valueOf(result);
    }

    //filtrado por letra:
    @GetMapping("/personas")
    @ResponseBody
    public String personas(@RequestParam char letra) {

        List<String> lista = List.of("Ana", "Antonio", "Carlos", "Alberto", "Lucia", "Andres");

        String r = "";

        for (String n : lista) {
            if (n.toLowerCase().contains(String.valueOf(letra).toLowerCase())) {
                r += n + " ";
            }
        }

        return r;
    }
}