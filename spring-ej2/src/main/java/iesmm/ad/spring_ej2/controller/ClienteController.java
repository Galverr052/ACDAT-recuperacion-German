package iesmm.ad.spring_ej2.controller;

import iesmm.ad.spring_ej2.model.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ex")
public class ClienteController {
    private ArrayList<Cliente> clientes = new ArrayList<>();

    ClienteController(){
        clientes.add(new Cliente("Manolo", "Gomez", "Carmona", 678543124));
        clientes.add(new Cliente("Jose", "Quijote", "Carmona", 678596745));
        clientes.add(new Cliente("Marta", "Garrido", "El Valle", 648427848));
        clientes.add(new Cliente("Alfonso", "Perez", "Mostoles", 627962472));
        clientes.add(new Cliente("Ramona", "Lopez", "Cabrera", 643497556));
    }

    //ejemplo - localhost:8081/ex/nvpar?min=2&max=7
    @GetMapping("/nvpar")
    @ResponseBody
    public String getAsteriscos(@RequestParam String min, @RequestParam String max){
        int nMin;
        int nMax;
        try {
            nMin = Integer.parseInt(min);
            nMax = Integer.parseInt(max);
        } catch (NumberFormatException e){
            return "Parametros con formato incorrecto";
        }

        if (!(nMin <= nMax)){
            return "El primer parametro debe ser menor al segundo";
        } else {
            String resultado = "";

            // Hago un ternario para asegurar que sean pares
            nMin = nMin % 2 == 0 ? nMin : nMin - 1;
            nMax = nMax % 2 == 0 ? nMax : nMax - 1;

            for (int i = nMin; i < nMax + 1; i++) {
                if (i%2==0){
                    for (int j = 0; j < i; j++) {
                        resultado += "*";
                    }
                }

                resultado += "\n";
            }

            return resultado;
        }
    }

    @PostMapping("/bintodec/{binario:[0-1]+}")
    @ResponseBody
    public String getDecimal(@PathVariable("binario") String bin){
        long resultado = 0;

        for (int i = 0; i < bin.length(); i++) {
            if (Integer.parseInt(String.valueOf(bin.charAt(i))) != 0){
                resultado += 2 ^ i;
            }
        }

        return String.valueOf(resultado);
    }

    // ejemplo - localhost:8081/ex/search?tel=678&pob=Carmona
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Cliente> getClientesApellidos(@RequestParam String tel, @RequestParam String pob){
        long nTel;
        List clientesDevolver = new ArrayList();
        try {
            nTel = Long.parseLong(tel);
        } catch (NumberFormatException e){
            return clientesDevolver;
        }

        for (Cliente c: clientes) {
            if (c.getPoblacion().equalsIgnoreCase(pob) && String.valueOf(c.getTelefono()).contains(tel)){
                clientesDevolver.add(c);
            }
        }

        clientesDevolver.sort((o1, o2) -> {
            Cliente c1 = (Cliente) o1;
            Cliente c2 = (Cliente) o2;
            return c1.getApellidos().compareTo(c2.getApellidos());
        });

        return clientesDevolver;
    }

    //ejemplo - localhost:8081/ex/ws/ex/search/627962472
    @PostMapping(value = "/ws/ex/search/{num:\\d{9}}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody()
    public Cliente getCliente(@PathVariable("num") String tel){

        Cliente cliente = new Cliente("Vacio", "", "", 0);

        for (Cliente c: clientes) {
            if (String.valueOf(c.getTelefono()).equalsIgnoreCase(tel)){
                cliente = c;
                break;
            }
        }

        return cliente;
    }

}

