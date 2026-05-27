package iesmm.ad.ad_recuperacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/ws")
public class ws {
    private final Logger logTag = Logger.getLogger("ad");


    public ws() {
        logTag.log(Level.INFO,("Nueva instancia Spring Controller generada:" + this));
    }

    @GetMapping("/dado/lanzar")
    @ResponseBody
    public String dado (){
        int random1 = (int)Math.random() * (7 - 1) + 1;


       return "numero: " +  random1;
    }

    @GetMapping("/dado/lanzar/numeros")
    @ResponseBody
    public String lanzar (@RequestParam int n, @RequestParam int m){

        int ran = (int)Math.random() * (m - n) + n;

        return "numero: " + ran;
    }

    @GetMapping("/dado/lanzar/jugada")
    @ResponseBody
    public String jugada () {

        for (int i = 0; i <= 5; i++) {
            int numero = (int)Math.random() * (7 - 1) + 1;
        }

        if(){

        }
    }


}
