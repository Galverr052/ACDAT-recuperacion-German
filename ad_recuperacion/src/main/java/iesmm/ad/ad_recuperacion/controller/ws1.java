package iesmm.ad.ad_recuperacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/ws")
public class ws1 {

    @PostMapping("/ex/hora")
    @ResponseBody
    public String hora (){
        LocalTime hora = LocalTime.now();

        return hora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }


    @GetMapping("ex/numeros")
    @ResponseBody
    public String numeros(@RequestParam int min, @RequestParam int max){

        int [] numeros = new int[max - min + 1];
        int n = 0;

        for (int i = min ; i < max + 1; i++) {
            numeros[n] = i;
            n++;
        }
        String resultado = "[ ";

        int x = 0;

        for (int numero : numeros) {
            resultado += numero;

            if (x < numeros.length - 1) {
                resultado += ",";
            }

            x++;
        }

        return resultado + "]";
    }

    @GetMapping(value = "ex/nveces",  produces = "application/xml")
    @ResponseBody
    public String frase(@RequestParam String frase) {

        StringBuilder xml = new StringBuilder();
        xml.append("<lista>");

        String Procesados = "";

        for (char c : frase.toCharArray()) {

            if (Procesados.indexOf(c) == -1) {
                int count = 0;

                for (char x : frase.toCharArray()) {
                    if (x == c) count++;
                }

                xml.append("<caracter>");
                xml.append("<simbolo>").append(c).append("</simbolo>");
                xml.append("<nveces>").append(count).append("</nveces>");
                xml.append("</caracter>");

                Procesados += c;
            }
        }

        xml.append("</lista>");
        return xml.toString();
    }

}
