package controller;

import java.io.File;

public class ClientesApp {
    public static void main(String[] args) {

        GestionaClientes gestionaClientes = new GestionaClientes();

        File fclientes = new File("res" + File.separator + "clientes.csv");
        File fcontadores = new File("res" + File.separator + "contadores.csv");
        File fmediciones = new File("res" + File.separator + "mediciones.csv");

        File resultFile = gestionaClientes.consumoSuministros(fmediciones);

        if (resultFile != null) {
            System.out.println("Archivo de consumo generado en: "
                    + resultFile.getAbsolutePath());
        }

        if (gestionaClientes.generarFicheroContadores(
                fcontadores, fmediciones)) {

            File fbincontadores =
                    new File("res" + File.separator + "contadores.dat");

            System.out.println("\nCONTADORES:");
            gestionaClientes.consumoContadores(fbincontadores);


            File txtClientes =
                    gestionaClientes.clienteContadores(
                            fclientes,
                            fcontadores);

            if (txtClientes != null) {
                System.out.println(
                        "\nGenerado: "
                                + txtClientes.getAbsolutePath());
            }

            if (gestionaClientes.generarFicheroClientes(
                    fclientes,
                    fbincontadores)) {

                File fbinclientes =
                        new File("res" + File.separator + "clientes.dat");

                System.out.println(
                        "\nclientes.dat generado correctamente");

                System.out.println("\nCLIENTES:");
                gestionaClientes.mostrarClientes(
                        fbinclientes);
            }
        }
    }
}
