package controller;

import model.Cliente;
import model.Contador;
import model.Medicion;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class GestionaClientes {

    public File consumoSuministros(File fmediciones) {

        File fresult = null;

        //Verificar si existe el archivo
        if (fmediciones.exists()) {
            //Remplazo su extención
            fresult = new File(fmediciones.getParent(), fmediciones.getName().replace(".csv", ".txt"));

            //Mapa para guardar cups y kwh
            Map<String, Float> consumoPorCups = new HashMap<>();

            try {

                //Leemos el archivo linea por linea
                BufferedReader br = new BufferedReader(new FileReader(fmediciones));
                String line;
                while ((line = br.readLine()) != null) {
                    //Si comineza con # se omite
                    if (line.startsWith("#")) {
                        continue;
                    }
                    //Obtengo los datos del csv por partes separadas por el punto y coma
                    String[] data = line.split(";");
                    if (data.length == 4) {
                        //optengo los cups que son el primer dato
                        String cups = data[0];
                        //y obtengo el kwh que son el cuarto en el csv
                        float kwh = Float.parseFloat(data[3]);
                        //Busca los cups y les añade los kwh
                        consumoPorCups.put(cups, consumoPorCups.getOrDefault(cups, 0f) + kwh);
                    } else {
                        System.err.println("Error de conversión");
                    }
                }
                //cierro el buffer reader
                br.close();

                PrintWriter pw = new PrintWriter(new FileWriter(fresult));

                for (Map.Entry<String, Float> entry :
                        consumoPorCups.entrySet()) {
                    pw.printf("Referencia CUPS: " + entry.getKey() + " - Consumo Total (kWh): %.2f \n", entry.getValue());
                }

                pw.close();

            } catch (IOException e) {

                System.err.println(
                        "Error de E/S: " + e.getMessage());
            }
        }

        return fresult;
    }

    public boolean generarFicheroContadores(File fcontadores, File fmediciones){
        boolean result = false;

        if(fcontadores.exists() && fmediciones.exists()){
            File fresult = new File(fcontadores.getParent(), fcontadores.getName().replace(".csv", ".dat"));

            String line;
            ArrayList<Contador> contadores =new ArrayList<>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(fcontadores));

                while ((line = br.readLine()) !=null){
                    if (!line.startsWith("#")){
                        String[] data = line.split(";");

                        if(data.length == 7){
                            String nif = data[0];
                            String cups = data[1];
                            Date fechaAlta = new SimpleDateFormat("dd/MM/yyyy").parse(data[2]);
                            String direccionSuministro = data[3];
                            float potenciaContratada = Float.parseFloat(data[4]);
                            float tarifaKwh = Float.parseFloat(data[5]);
                            float descuento = Float.parseFloat(data[6]);

                            Contador contador = new Contador(nif, cups, potenciaContratada, direccionSuministro, tarifaKwh , descuento, fechaAlta);
                            contadores.add(contador);
                        } else
                            System.err.println("Error de conversión");
                    }
                }
                br.close();
            } catch (IOException e) {
                System.err.println("Error de E/S: " + e.getMessage());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            try {
                BufferedReader br = new BufferedReader(new FileReader(fmediciones));

                while ((line = br.readLine()) !=null){

                    if (!line.startsWith("#")){

                        String[] data = line.split(";");

                        if(data.length == 4){
                            String cups = data[0];
                            Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(data[1]);
                            int hora = Integer.parseInt(data[2]);
                            float kwh = Float.parseFloat(data[3]);


                            Medicion medicion = new Medicion(fecha, hora, kwh);

                            boolean encontrado = false;
                            int i = 0;

                            while (i < contadores.size() && !encontrado){
                                if(contadores.get(i).getCups().equals(cups)){
                                    encontrado = true;
                                }else i++;
                            }
                            if (encontrado){
                                contadores.get(i).addMedicion(medicion);
                            }
                        }
                    }
                }
                br.close();
                ObjectOutputStream fbin = new ObjectOutputStream(new FileOutputStream(fresult));

                for(Contador c : contadores){
                    fbin.writeObject(c);
                }

                fbin.close();

            } catch (IOException e) {
                System.err.println("Error de E/S: " + e.getMessage());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            result = true;
        }

        return result;
    }

    public void consumoContadores(File fbincontadores) {
        try (ObjectInputStream fcontadores = new ObjectInputStream(new FileInputStream(fbincontadores))) {
            while (true) {
                Contador c = (Contador) fcontadores.readObject();
                System.out.printf("Referencia CUPS: " + c.getCups() + " - Consumo Total (kWh): %.2f \n", c.getConsumoTotal());
            }
        } catch (EOFException e) {
            System.out.println("Fin de fichero ");
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public File clienteContadores(File fclientes, File fcontadores){
        File result = null;

        if(fclientes.exists() && fcontadores.exists()){
            File fresult = new File(fclientes.getParent(),fclientes.getName().replace(".csv",".txt"));
            Map<String, Integer> contadoresCliente= new HashMap<>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(fcontadores));
                String line;

                while ((line = br.readLine()) != null){
                    if (line.startsWith("#")){
                        continue;
                    }

                    String[] data = line.split(";");
                    if (data.length == 7){
                        String nif = data[0];
                        contadoresCliente.put(nif,contadoresCliente.getOrDefault(nif,0)+1);
                    }
                }
                br.close();


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                BufferedReader br = new BufferedReader(new FileReader(fclientes));
                PrintWriter pw = new PrintWriter(new FileWriter(fresult));
                String line;
                while ((line = br.readLine()) != null) {

                    if (line.startsWith("#")) {
                        continue;
                    }

                    String[] data = line.split(";");

                    if (data.length == 4) {

                        String nif = data[0];

                        int numContadores = contadoresCliente.getOrDefault(nif, 0);

                        pw.println(nif + ": " + numContadores);
                    }
                }
                br.close();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public boolean generarFicheroClientes(File fclientes,File fbincontadores) {

        boolean result = false;

        if (fclientes.exists() && fbincontadores.exists()) {

            File fresult = new File(fclientes.getParent(),fclientes.getName().replace(".csv", ".dat"));

            try {
                ArrayList<Contador> contadores = new ArrayList<>();
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fbincontadores));
                try {
                    while (true) {
                        contadores.add((Contador) ois.readObject());
                    }
                } catch (EOFException e) {
                }

                ois.close();

                BufferedReader br = new BufferedReader(new FileReader(fclientes));

                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fresult));

                String line;

                while ((line = br.readLine()) != null) {

                    if (line.startsWith("#")) {
                        continue;
                    }

                    String[] data = line.split(";");

                    if (data.length == 4) {
                        String nif = data[0];
                        Date fechaAlta = new SimpleDateFormat("dd/MM/yyyy").parse(data[1]);
                        String nombre = data[2];
                        String apellido = data[3];

                        Cliente cliente = new Cliente(nif,nombre,apellido,fechaAlta,new ArrayList<>());
                        for (Contador c : contadores) {
                            if (c.getNif().equals(nif)) {
                                cliente.addContador(c);
                            }
                        }

                        oos.writeObject(cliente);
                    }
                }

                br.close();
                oos.close();

                result = true;

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return result;
    }

    public void mostrarClientes(File fbinclientes){

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fbinclientes))) {

            while (true){

                Cliente cliente = (Cliente) ois.readObject();

                System.out.println(cliente.getNif() + " - " + cliente.getNombre() + " " + cliente.getApellido());

                for (Contador c : cliente.getcontadores()){
                    System.out.println("CUPS: " + c.getCups() + " Potencia: " + c.getPotenciaContratada());
                }
                System.out.println();
            }
        } catch (EOFException e){
            System.out.println("Fin de fichero");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
