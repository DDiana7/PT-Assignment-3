package org.example.PresentationLayer;

import org.example.BusinessLogic.OrderBLL;
import org.example.Model.Bill;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clasa OrderController gestioneaza evenimentele declansate din interfata grafica OrderWindow.
 * Permite plasarea unei comenzi si afisarea facturilor existente.
 */
public class OrderController implements ActionListener {
    private final OrderBLL orderBLL;
    private final OrderWindow view;

    /**
     * Constructorul clasei OrderController.
     *
     * @param view Fereastra grafica pentru gestionarea comenzilor.
     */
    public OrderController(OrderWindow view) {
        this.view = view;
        this.orderBLL = new OrderBLL();
    }

    /**
     * Metoda apelata la declansarea unei actiuni in interfata grafica.
     * In functie de comanda primita, apeleaza metoda corespunzatoare.
     *
     * @param e Evenimentul ActionEvent generat din GUI.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ADD_ORDER" -> addOrder();
            case "VIEW_BILLS" -> viewBills();
        }
    }

    /**
     * Permite introducerea unei comenzi noi prin dialoguri.
     * Valideaza datele introduse si plaseaza comanda daca acestea sunt corecte.
     */
    private void addOrder() {
        try {
            String clientIdStr = JOptionPane.showInputDialog(view, "Enter Client ID:");
            String productIdStr = JOptionPane.showInputDialog(view, "Enter Product ID:");
            String quantityStr = JOptionPane.showInputDialog(view, "Enter Quantity:");

            if (clientIdStr == null || productIdStr == null || quantityStr == null ||
                    clientIdStr.isBlank() || productIdStr.isBlank() || quantityStr.isBlank()) {
                JOptionPane.showMessageDialog(view, "All fields are required!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int clientId = Integer.parseInt(clientIdStr.trim());
            int productId = Integer.parseInt(productIdStr.trim());
            int quantity = Integer.parseInt(quantityStr.trim());

            orderBLL.placeOrder(clientId, productId, quantity);
            JOptionPane.showMessageDialog(view, "✅ Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter numeric values for IDs and quantity.", "Format Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Afiseaza lista tuturor facturilor existente in sistem.
     * Daca nu exista facturi, afiseaza un mesaj de informare.
     */
    private void viewBills() {
        List<Bill> bills = orderBLL.getAllBills();
        if (bills.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No bills found.");
            return;
        }
        view.refreshBillTable(bills);
    }
}