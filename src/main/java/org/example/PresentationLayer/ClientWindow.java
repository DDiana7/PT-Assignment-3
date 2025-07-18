package org.example.PresentationLayer;

import org.example.PresentationLayer.ClientController;
import org.example.Model.Client;
import org.example.Start.Reflection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clasa ClientWindow reprezinta fereastra grafica pentru operatii legate de clienti.
 * Permite adaugarea, stergerea, actualizarea si afisarea clientilor intr-un JTable.
 */
public class ClientWindow extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;

    private JButton addClientButton;
    private JButton viewClientsButton;
    private JButton deleteClientButton;
    private JButton updateClientButton;

    private JTable clientTable;
    private JScrollPane tableScrollPane;
    private final ClientController controller;

    /**
     * Constructorul clasei. Creeaza fereastra si initializeaza controllerul asociat.
     */
    public ClientWindow() {
        super("Client Operations");
        this.controller = new ClientController(this);
        prepareGui();
    }

    /**
     * Metoda principala care construieste structura interfetei grafice.
     * Seteaza layout-ul, dimensiunea ferestrei si apeleaza metodele pentru panouri.
     */
    private void prepareGui() {
        this.setSize(800, 500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);

        prepareTitlePanel();

        JPanel centerPanel = new JPanel(new BorderLayout());
        prepareButtonsPanel(centerPanel);
        prepareTablePanel(centerPanel);
        this.contentPane.add(centerPanel, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Creeaza panoul de titlu din partea de sus a ferestrei.
     */
    private void prepareTitlePanel() {
        JPanel titlePanel = new JPanel();
        Color backgroundColor = new Color(107, 144, 128);
        Color textColor = new Color(246, 255, 248);
        titlePanel.setBackground(backgroundColor);

        this.titleLabel = new JLabel("Client Operations", JLabel.CENTER);
        this.titleLabel.setFont(new Font("Comic Sans", Font.BOLD, 24));
        this.titleLabel.setForeground(textColor);
        titlePanel.add(titleLabel);

        this.contentPane.add(titlePanel, BorderLayout.NORTH);
    }

    /**
     * Creeaza panoul cu butoane si le configureaza cu actiuni si stiluri.
     *
     * @param parent Panoul in care se adauga butoanele.
     */
    private void prepareButtonsPanel(JPanel parent) {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        Color buttonColor = new Color(164, 195, 178);
        Color textColor = new Color(246, 255, 248);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        this.addClientButton = createButton("Add Client", "ADD_CLIENT", buttonColor, textColor);
        this.viewClientsButton = createButton("View Clients", "VIEW_CLIENTS", buttonColor, textColor);
        this.deleteClientButton = createButton("Delete Client", "DELETE_CLIENT", buttonColor, textColor);
        this.updateClientButton = createButton("Update Client", "UPDATE_CLIENT", buttonColor, textColor);

        buttonPanel.add(addClientButton);
        buttonPanel.add(viewClientsButton);
        buttonPanel.add(deleteClientButton);
        buttonPanel.add(updateClientButton);

        parent.add(buttonPanel, BorderLayout.NORTH);
    }

    /**
     * Creeaza un buton configurat cu text, comanda si stil.
     *
     * @param text           Textul afisat pe buton.
     * @param actionCommand  Comanda care va fi trimisa catre controller.
     * @param bg             Culoarea de fundal.
     * @param fg             Culoarea textului.
     * @return Un obiect JButton configurat.
     */
    private JButton createButton(String text, String actionCommand, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setActionCommand(actionCommand);
        button.addActionListener((ActionListener) controller);
        button.setFont(new Font("Comic Sans", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Creeaza panoul care contine JTable-ul pentru afisarea clientilor.
     *
     * @param parent Panoul in care se adauga tabelul.
     */
    private void prepareTablePanel(JPanel parent) {
        JPanel displayPanel = new JPanel(new BorderLayout());
        Color backgroundColor = new Color(234, 244, 244);
        displayPanel.setBackground(backgroundColor);

        this.clientTable = new JTable();
        this.clientTable.setBackground(backgroundColor);
        this.clientTable.setFillsViewportHeight(true);

        this.tableScrollPane = new JScrollPane(clientTable);
        displayPanel.add(tableScrollPane, BorderLayout.CENTER);

        parent.add(displayPanel, BorderLayout.CENTER);
    }

    /**
     * Reincarca tabelul de clienti cu date actualizate din baza de date.
     * Utilizeaza metoda de reflectie pentru a genera dinamica tabela.
     */

    public void refreshClientTable() {
        List<Client> clientList = controller.findAll();
        DefaultTableModel model = Reflection.createTableModel(clientList);
        clientTable.setModel(model);
    }
}