
import dao.DAO;
import dao.DAOImpl;
import model.Acceso;
import model.Jugador;
import model.Partida;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) {
        Logger logTag = Logger.getLogger("AD");
        boolean valueReturn = false; // Valor de retorno en cada modificación del modelo
        Scanner sc = new Scanner(System.in);
        String categoria;

        // Obtiene un objeto DAO a partir de la sesión iniciada y los parámetros configurados
        DAO dao = new DAOImpl();

        // MENÚ DE CONSULTAS
        int op = 0;
        do {
            System.out.println("============================");
            System.out.println("1. Accesos por jugador");
            System.out.println("2. Jugadores con cuota más alta");
            System.out.println("3. Número de partidas según fechas");
            System.out.println("4. Insertar jugador en una partida");
            System.out.println("5. Eliminar jugador");
            System.out.println("6. SALIR");
            System.out.println("Introducir opción (1-6): ");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    logTag.log(Level.INFO, "Acción consultada: " + "CONSULTA");
                    System.out.print("Introducir idjugador: ");
                    int idjugador = sc.nextInt();
                    List<Acceso> accesos = dao.accesosJugador(new Jugador(idjugador, 0, null, null, 0, null));

                    if (accesos != null)
                        for (Acceso a : accesos)
                            System.out.println(a);
                    break;

                case 2:
                    logTag.log(Level.INFO, "Acción consultada: " + "JUGADORES CON CUOTA MÁS ALTA");
                    List<Jugador> jugadores = dao.cuotaMasAlta();

                    if (jugadores != null)
                        for (Jugador jugador : jugadores)
                            System.out.println(jugador);

                    break;

                case 3:
                    logTag.log(Level.INFO, "Acción consultada: " + "NÚMERO DE PARTIDAS");
                    System.out.print("Introducir fecha inicio (YYYY-MM-DD): ");
                    Date finicio = Date.valueOf(sc.next());
                    System.out.print("Introducir fecha final (YYYY-MM-DD): ");
                    Date ffin = Date.valueOf(sc.next());

                    if (finicio.before(ffin))
                        System.out.println("TOTAL DE NUMERO DE PARTIDAS: " + dao.contabilizarNumPartidas(finicio, ffin));
                    else
                        System.err.println("Fecha de inicio debe ser anterior a la fecha final");
                    break;

                case 4:
                    logTag.log(Level.INFO, "Acción consultada: " + "INSERTAR JUGADOR");
                    List<Acceso> listaAccesos = dao.insertarAcceso(new Jugador(10, 0, null, null, 0, null), new Partida(6, null));

                    if (listaAccesos != null)
                        for (Acceso a : listaAccesos)
                            System.out.println(a);

                    break;

                case 5:
                    logTag.log(Level.INFO, "Acción consultada: " + "ELIMINAR JUGADOR");
                    valueReturn = dao.eliminarJugador(new Jugador(10, 0, null, null, 0, null));

                    if (valueReturn)
                        System.out.println("Jugador eliminado");
                    else
                        System.err.println("Fallo en la eliminación");
                    break;

                case 6:
                    break;
                default:
                    System.out.println("Opción errónea");
            }
        } while (op != 6);
    }
}