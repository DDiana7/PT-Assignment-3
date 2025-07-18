package org.example.DataAccess;

import org.example.Connection.ConnectionFactory;
import org.example.Model.Order;

/**
 * Clasa OrderDAO ofera functionalitati pentru accesarea tabelei Order din baza de date.
 * Extinde AbstractDAO pentru operatii generice si adauga o metoda pentru obtinerea urmatorului ID disponibil.
 */
public class OrderDAO extends AbstractDAO<Order> {

    /**
     * Returneaza urmatorul ID disponibil pentru inserarea unei noi comenzi.
     *
     * @return Valoarea urmatorului ID (MAX(id) + 1) sau 1 daca tabela este goala.
     */
    public int getNextOrderId() {
        String query = "SELECT MAX(id) AS max_id FROM `Order`";
        try (
                var connection = ConnectionFactory.getConnection();
                var statement = connection.prepareStatement(query);
                var resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                return resultSet.getInt("max_id") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}