package org.example.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa ConnectionFactory gestioneaza conexiunile catre baza de date.
 * Utilizeaza pattern-ul Singleton pentru a furniza o singura instanta de conexiune.
 * Ofera metode pentru deschiderea si inchiderea conexiunilor, statement-urilor si rezultatelor.
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/asg3db";
    private static final String USER = "root";
    private static final String PASS = "diana.parola30";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructor privat care incarca driverul bazei de date.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creeaza o noua conexiune catre baza de date.
     *
     * @return Obiect Connection daca conexiunea este realizata cu succes, altfel null.
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "A aparut o eroare la conectarea la baza de date.");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Furnizeaza o conexiune catre baza de date folosind instanta Singleton.
     *
     * @return Obiect Connection valid.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Inchide o conexiune deschisa catre baza de date.
     *
     * @param connection Obiectul Connection de inchis.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A aparut o eroare la inchiderea conexiunii.");
            }
        }
    }

    /**
     * Inchide un obiect Statement.
     *
     * @param statement Obiectul Statement de inchis.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A aparut o eroare la inchiderea statement-ului.");
            }
        }
    }

    /**
     * Inchide un obiect ResultSet.
     *
     * @param resultSet Obiectul ResultSet de inchis.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A aparut o eroare la inchiderea ResultSet-ului.");
            }
        }
    }
}