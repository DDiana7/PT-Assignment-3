package org.example.BusinessLogic;

import org.example.DataAccess.*;
import org.example.Model.*;

import java.util.Date;
import java.util.List;

/**
 * Clasa OrderBLL se ocupa de logica de afaceri pentru gestionarea comenzilor.
 * Verifica validitatea clientului si a produsului, stocul, plaseaza comenzi si genereaza facturi.
 */
public class OrderBLL {
    private final OrderDAO orderDAO;
    private final ClientDAO clientDAO;
    private final ProductDAO productDAO;
    private final BillDAO billDAO;

    /**
     * Constructorul clasei. Initializeaza toate obiectele DAO necesare pentru procesarea comenzilor.
     */
    public OrderBLL() {
        this.orderDAO = new OrderDAO();
        this.clientDAO = new ClientDAO();
        this.productDAO = new ProductDAO();
        this.billDAO = new BillDAO();
    }

    /**
     * Plaseaza o comanda pentru un client asupra unui produs specific.
     * Verifica daca clientul si produsul exista si daca exista stoc suficient.
     * Genereaza automat factura si actualizeaza stocul.
     *
     * @param clientId  ID-ul clientului care plaseaza comanda.
     * @param productId ID-ul produsului comandat.
     * @param quantity  Cantitatea dorita.
     * @throws IllegalArgumentException daca datele sunt invalide sau nu exista stoc suficient.
     */
    public void placeOrder(int clientId, int productId, int quantity) {
        // Validare client
        Client client = clientDAO.findById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Nu exista client cu ID-ul: " + clientId);
        }

        // Validare produs
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Nu exista produs cu ID-ul: " + productId);
        }

        // Verificare stoc
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Stoc insuficient! Disponibil: " + product.getStock());
        }

        // Creare comanda
        int orderId = orderDAO.getNextOrderId();
        Order order = new Order(orderId, productId, clientId, quantity, new Date());
        orderDAO.insert(order);

        // Actualizare stoc produs
        product.setStock(product.getStock() - quantity);
        productDAO.update(product);

        // Generare factura
        float total = product.getPrice() * quantity;
        int billId = billDAO.findAll().size() + 1;
        Bill bill = new Bill(billId, orderId, new Date(), total);
        billDAO.insert(bill);
    }

    /**
     * Returneaza lista tuturor facturilor emise.
     *
     * @return Lista de obiecte Bill.
     */
    public List<Bill> getAllBills() {
        return billDAO.findAll().stream().toList();
    }
}