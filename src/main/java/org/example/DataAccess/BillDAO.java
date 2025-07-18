package org.example.DataAccess;

import org.example.Connection.ConnectionFactory;
import org.example.Model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa BillDAO gestioneaza accesul la tabela Log din baza de date,
 * care contine informatii despre facturile generate pentru comenzi.
 * Aceasta clasa permite inserarea si listarea facturilor.
 */
public class BillDAO {
    private static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());

    /**
     * Insereaza o factura noua in tabela Log.
     *
     * @param bill Factura care trebuie inserata.
     */
    public void insert(Bill bill) {
        String query = "INSERT INTO Log (id, order_id, generated_date, total_price) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, bill.id());
            statement.setInt(2, bill.orderId());
            statement.setTimestamp(3, new Timestamp(bill.generatedDate().getTime()));
            statement.setFloat(4, bill.totalPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:insert -> " + e.getMessage());
        }
    }

    /**
     * Returneaza toate facturile existente in tabela Log.
     *
     * @return Lista de obiecte Bill.
     */
    public List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Log";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                Bill bill = new Bill(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getTimestamp("generated_date"),
                        rs.getFloat("total_price")
                );
                bills.add(bill);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:findAll -> " + e.getMessage());
        }

        return bills;
    }
}