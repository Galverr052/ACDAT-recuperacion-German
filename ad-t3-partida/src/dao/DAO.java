package dao;

import model.Acceso;
import model.Jugador;
import model.Partida;

import java.sql.Date;
import java.util.List;

public interface DAO {
        List<Acceso> accesosJugador(Jugador jugador);
         List<Jugador> cuotaMasAlta();
        int contabilizarNumPartidas(Date finicio, Date ffin);
        List<Acceso> insertarAcceso(Jugador jugador, Partida partida);
        boolean eliminarJugador(Jugador jugador);
        void mostrarAcceso();
        int nivelPartidas(Date finicio, Date ffin);
        int elimminarPartidasIncompletas();
}
