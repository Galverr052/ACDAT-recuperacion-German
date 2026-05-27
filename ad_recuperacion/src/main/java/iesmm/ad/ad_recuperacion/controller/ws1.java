package iesmm.ad.ad_recuperacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/ws")
public class ws1 {

    @GetMapping("/ex/hora")
    @ResponseBody
    public String hora (){
        LocalTime hora = LocalTime.now();

        return hora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }



}
