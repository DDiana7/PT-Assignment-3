package org.example.PresentationLayer;

import org.example.Model.Client;
import org.example.BusinessLogic.ClientBLL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clasa ClientController gestioneaza evenimentele declansate din interfata grafica pentru clienti.
 * Se ocupa de adaugarea, stergerea, actualizarea si afisarea clientilor folosind logica din ClientBLL.
 */
public class ClientController implements ActionListener {
    private final ClientBLL clientBLL;
    private final ClientWindow view;

    /**
     * Constructorul initializeaza controllerul cu o referinta catre fereastra ClientWindow.
     *
     * @param view Fereastra grafica asociata clientilor.
     */
    public ClientController(ClientWindow view) {
        this.view = view;
        this.clientBLL = new ClientBLL();
    }

    /**
     * Metoda apelata automat la declansarea unui eveniment in GUI.
     * In functie de actiunea primita, apeleaza metodele corespunzatoare.
     *
     * @param e Evenimentul ActionEvent generat din interfata.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "ADD_CLIENT":
                addClient();
                break;
            case "VIEW_CLIENTS":
                view.refreshClientTable();
                break;
            case "DELETE_CLIENT":
                deleteClient();
                break;
            case "UPDATE_CLIENT":
                updateClient();
                break;
        }
    }

    /**
     * Adauga un client nou in sistem, pe baza datelor introduse de utilizator.
     */
    private void addClient() {
        try {
            int id = clientBLL.getNextClientId();
            String name = JOptionPane.showInputDialog(view, "Client name:");
            String email = JOptionPane.showInputDialog(view, "Client email:");
            String address = JOptionPane.showInputDialog(view, "Client address:");

            Client client = new Client(id, name, email, address);
            clientBLL.insertClient(client);
            JOptionPane.showMessageDialog(view, "Client added!");
            view.refreshClientTable();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sterge un client existent in baza de date, pe baza ID-ului introdus de utilizator.
     */
    private void deleteClient() {
        String input = JOptionPane.showInputDialog(view, "Enter client ID to delete:");

        try {
            int id = Integer.parseInt(input);
            clientBLL.deleteClient(id);
            JOptionPane.showMessageDialog(view, "Client deleted!");
            view.refreshClientTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualizeaza datele unui client existent, pe baza informatiilor introduse in dialoguri.
     */
    private void updateClient() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(view, "Enter client ID to update:"));
            String name = JOptionPane.showInputDialog(view, "New name:");
            String email = JOptionPane.showInputDialog(view, "New email:");
            String address = JOptionPane.showInputDialog(view, "New address:");

            Client client = new Client(id, name, email, address);
            clientBLL.updateClient(client);
            JOptionPane.showMessageDialog(view, "Client updated!");
            view.refreshClientTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returneaza toti clientii existenti in baza de date.
     *
     * @return Lista de obiecte Client.
     */
    public List<Client> findAll() {
        return clientBLL.findAllClients();
    }
}