package org.example.DataAccess;

import org.example.Model.Client;
import org.example.Connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa ClientDAO ofera functionalitati pentru accesarea tabelei Client din baza de date.
 * Extinde AbstractDAO pentru operatii generice si adauga o metoda pentru obtinerea urmatorului ID disponibil.
 */
public class ClientDAO extends AbstractDAO<Client> {
    private static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    /**
     * Returneaza urmatorul ID disponibil pentru inserarea unui nou client.
     *
     * @return Valoarea urmatorului ID (MAX(id) + 1) sau 1 daca tabela este goala.
     */
    public int getNextClientId() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT MAX(id) AS max_id FROM Client";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:getNextClientId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 1;
    }
}