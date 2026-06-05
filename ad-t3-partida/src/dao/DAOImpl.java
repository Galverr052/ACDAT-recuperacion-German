package dao;

import model.Acceso;
import model.Jugador;
import model.Partida;
import utils.Conexion;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl implements DAO{

    public List<Acceso> accesosJugador(Jugador jugador) {
        Connection c;
        List<Acceso> listaAcceso = new ArrayList<>();
        String sql = "SELECT * FROM acceso WHERE idjugador = ? ORDER BY fhentrada DESC";

        try {
            c =Conexion.getConnection();
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setInt(1, jugador.getIdjugador());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                do {
                    int idjugador = resultSet.getInt("idjugador");
                    int idpartida = resultSet.getInt("idpartida");
                    Date fhentrada = resultSet.getDate("fhentrada");
                    Date fhsalida = resultSet.getDate("fhsalida");

                    Acceso acceso = new Acceso(idjugador, idpartida, fhentrada, fhsalida);
                    listaAcceso.add(acceso);
                } while (resultSet.next());
            }
            Conexion.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listaAcceso;
    }

    public List<Jugador> cuotaMasAlta() {
        Connection c;
        List<Jugador> listajugadores = null;
        String sql = "SELECT * FROM jugador WHERE cuota = (SELECT MAX(cuota) FROM jugador)";

        try {
            c =Conexion.getConnection();
            PreparedStatement statement = c.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                listajugadores = new ArrayList<Jugador>();

                do{
                    int idjugador = resultSet.getInt("idjugador");
                    int dni = resultSet.getInt("dni");
                    String nombre = resultSet.getString("nombre");
                    String iban = resultSet.getString("iban");
                    float cuota = resultSet.getFloat("cuota");
                    Date falta = resultSet.getDate("falta");

                    Jugador jugador = new Jugador(idjugador,dni,nombre,iban,cuota,falta);

                    listajugadores.add(jugador);
                }while (resultSet.next());
            }
            Conexion.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listajugadores;
    }

    public int contabilizarNumPartidas(Date finicio, Date ffin) {
        Connection c;
        int npartidas = 1;
        String sql = "{? = call num_partidas(?,?)}";

        try {
            c =Conexion.getConnection();

            CallableStatement callableStatement = c.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setDate(2, finicio);
            callableStatement.setDate(3, ffin);
            callableStatement.execute();
            npartidas = callableStatement.getInt(1);
            Conexion.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return npartidas;
    }

    public List<Acceso> insertarAcceso(Jugador jugador, Partida partida) {
        Connection c = null;
        List<Acceso> listaAccesos = null;
        String sql1 = "{ call entrar_jugador_en_partida(?,?)}";
        String sql2 = "SELECT * FROM acceso ORDER BY fhentrada DESC";

        try {
            c = Conexion.getConnection();
            c.setAutoCommit(false);

            CallableStatement callableStatement = c.prepareCall(sql1);
            callableStatement.setInt(1, jugador.getIdjugador());
            callableStatement.setInt(2, partida.getIdpartida());
            callableStatement.execute();

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(sql2);

            if (resultSet.next()){
                listaAccesos = new ArrayList<>();

                do{
                    int idjugador = resultSet.getInt("idjugador");
                    int idpartida = resultSet.getInt("idpartida");
                    Date fhentrada = resultSet.getDate("fhentrada");
                    Date fhsalida = resultSet.getDate("fhsalida");

                    Acceso acceso = new Acceso(idjugador, idpartida, fhentrada,fhsalida);

                    listaAccesos.add(acceso);
                }while (resultSet.next());

                c.commit();
            }


        } catch (SQLException e) {
            if (c != null){
                try {
                    c.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(c != null){
                try {
                    c.setAutoCommit(true);
                    Conexion.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return listaAccesos;
    }

    public boolean eliminarJugador(Jugador jugador) {
        Connection c = null;
        boolean vreturn = false;
        String sql1 = "DELETE FROM acceso WHERE idjugador = ?";
        String sql2 = "DELETE FROM jugador WHERE idjugador = ?";
        try {
            c = Conexion.getConnection();
            c.setAutoCommit(false);

            PreparedStatement preparedStatement1 =  c.prepareStatement(sql1);
            preparedStatement1.setInt(1,jugador.getIdjugador());
            vreturn = preparedStatement1.executeUpdate() > 0;

            if (vreturn){
                PreparedStatement preparedStatement2 =  c.prepareStatement(sql2);
                preparedStatement2.setInt(1,jugador.getIdjugador());
                vreturn = preparedStatement2.executeUpdate() > 0;

                c.commit();
            }
        } catch (SQLException e) {
            if (c != null){
                try {
                    c.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(c != null){
                try {
                    c.setAutoCommit(true);
                    Conexion.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return vreturn;
    }
}
