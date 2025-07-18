package org.example.PresentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa Controller gestioneaza actiunile utilizatorului din fereastra principala (View).
 * In functie de butonul apasat, deschide fereastra corespunzatoare: clienti, produse sau comenzi.
 */
public class Controller implements ActionListener {
    private final View view;

    /**
     * Constructorul clasei Controller.
     *
     * @param view Fereastra principala care contine butoanele de navigare.
     */
    public Controller(View view) {
        this.view = view;
    }

    /**
     * Metoda apelata automat la apasarea unui buton.
     * Deschide fereastra corespunzatoare comenzii primite.
     *
     * @param e Evenimentul generat de actiunea utilizatorului.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "CLIENT_WINDOW" -> new ClientWindow();
            case "PRODUCT_WINDOW" -> new ProductWindow();
            case "ORDER_WINDOW" -> new OrderWindow();
            default -> System.out.println("Unknown command: " + command);
        }
    }
}