package org.example.PresentationLayer;

import org.example.Model.Bill;
import org.example.Start.Reflection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clasa OrderWindow reprezinta interfata grafica pentru gestionarea comenzilor.
 * Permite adaugarea unei comenzi noi si vizualizarea facturilor existente.
 */
public class OrderWindow extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;

    private JButton addOrderButton;
    private JButton viewBillsButton;

    private JTable billTable;
    private JScrollPane tableScrollPane;
    private final OrderController controller;

    /**
     * Constructorul clasei. Creeaza fereastra si initializeaza controllerul asociat.
     */
    public OrderWindow() {
        super("Order Operations");
        this.controller = new OrderController(this);
        prepareGui();
    }

    /**
     * Metoda care construieste structura ferestrei si a componentelor grafice.
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
     * Creeaza si adauga panoul de titlu in partea de sus a ferestrei.
     */
    private void prepareTitlePanel() {
        JPanel titlePanel = new JPanel();
        Color backgroundColor = new Color(107, 144, 128);
        Color textColor = new Color(246, 255, 248);
        titlePanel.setBackground(backgroundColor);

        this.titleLabel = new JLabel("Order Operations", JLabel.CENTER);
        this.titleLabel.setFont(new Font("Comic Sans", Font.BOLD, 24));
        this.titleLabel.setForeground(textColor);
        titlePanel.add(titleLabel);

        this.contentPane.add(titlePanel, BorderLayout.NORTH);
    }

    /**
     * Creeaza panoul cu butoane si le configureaza cu actiuni si stil.
     *
     * @param parent Panoul in care se adauga butoanele.
     */
    private void prepareButtonsPanel(JPanel parent) {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        Color buttonColor = new Color(164, 195, 178);
        Color textColor = new Color(246, 255, 248);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        this.addOrderButton = createButton("Add Order", "ADD_ORDER", buttonColor, textColor);
        this.viewBillsButton = createButton("View Bills", "VIEW_BILLS", buttonColor, textColor);

        buttonPanel.add(addOrderButton);
        buttonPanel.add(viewBillsButton);

        parent.add(buttonPanel, BorderLayout.NORTH);
    }

    /**
     * Creeaza un buton personalizat cu text, actiune si culori.
     *
     * @param text           Textul afisat pe buton.
     * @param actionCommand  Comanda transmisa controllerului.
     * @param bg             Culoarea de fundal.
     * @param fg             Culoarea textului.
     * @return Butonul configurat.
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
     * Creeaza panoul pentru afisarea tabelului de facturi.
     *
     * @param parent Panoul in care se adauga tabelul.
     */
    private void prepareTablePanel(JPanel parent) {
        JPanel displayPanel = new JPanel(new BorderLayout());
        Color backgroundColor = new Color(234, 244, 244);
        displayPanel.setBackground(backgroundColor);

        this.billTable = new JTable();
        this.billTable.setBackground(backgroundColor);
        this.billTable.setFillsViewportHeight(true);

        this.tableScrollPane = new JScrollPane(billTable);
        displayPanel.add(tableScrollPane, BorderLayout.CENTER);

        parent.add(displayPanel, BorderLayout.CENTER);
    }

    /**
     * Actualizeaza tabelul cu lista de facturi primita ca parametru.
     *
     * @param bills Lista de facturi care trebuie afisata.
     */
    public void refreshBillTable(List<Bill> bills) {
        DefaultTableModel model = Reflection.createTableModel(bills);
        billTable.setModel(model);
    }
}