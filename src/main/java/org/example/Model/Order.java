package org.example.Model;

import java.util.Date;

/**
 * Clasa Order reprezinta o comanda efectuata de un client pentru un anumit produs.
 * Contine informatii despre produs, client, cantitatea comandata si data plasarii comenzii.
 */
public class Order {
    private int id;
    private int product_id;
    private int client_id;
    private int quantity;
    private Date order_date;

    /**
     * Constructor complet care initializeaza toate campurile comenzii.
     *
     * @param id          ID-ul comenzii.
     * @param product_id  ID-ul produsului comandat.
     * @param client_id   ID-ul clientului care a plasat comanda.
     * @param quantity    Cantitatea comandata.
     * @param order_date  Data la care a fost plasata comanda.
     */
    public Order(int id, int product_id, int client_id, int quantity, Date order_date) {
        this.id = id;
        this.product_id = product_id;
        this.client_id = client_id;
        this.quantity = quantity;
        this.order_date = order_date;
    }

    /**
     * Constructor gol necesar pentru reflectie sau instantiere implicita.
     */
    public Order() {
    }

    /**
     * Returneaza ID-ul comenzii.
     *
     * @return ID-ul comenzii.
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza ID-ul comenzii.
     *
     * @param id Noua valoare pentru ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza ID-ul produsului comandat.
     *
     * @return ID-ul produsului.
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * Seteaza ID-ul produsului comandat.
     *
     * @param product_id ID-ul produsului.
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * Returneaza ID-ul clientului care a plasat comanda.
     *
     * @return ID-ul clientului.
     */
    public int getClient_id() {
        return client_id;
    }

    /**
     * Seteaza ID-ul clientului.
     *
     * @param client_id ID-ul nou al clientului.
     */
    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    /**
     * Returneaza cantitatea comandata.
     *
     * @return Cantitatea produsului comandat.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Seteaza cantitatea comandata.
     *
     * @param quantity Noua cantitate.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returneaza data la care a fost plasata comanda.
     *
     * @return Data comenzii.
     */
    public Date getOrder_date() {
        return order_date;
    }

    /**
     * Seteaza data comenzii.
     *
     * @param order_date Noua data.
     */
    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    /**
     * Returneaza o reprezentare textuala a comenzii.
     *
     * @return String cu informatii despre comanda.
     */
    @Override
    public String toString() {
        return "Order{" +
                "Bill Id=" + id +
                ", product_id=" + product_id +
                ", client_id=" + client_id +
                ", quantity=" + quantity +
                ", order_date=" + order_date +
                '}';
    }
}