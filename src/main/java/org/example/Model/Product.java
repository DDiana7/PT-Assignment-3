package org.example.Model;

/**
 * Clasa Product reprezinta un produs din depozit.
 * Contine informatii despre ID, denumire, pret si stocul disponibil.
 * Este utilizata in comenzile plasate de clienti si in gestiunea produselor.
 */
public class Product {
    private int id;
    private String name;
    private float price;
    private int stock;

    /**
     * Constructor complet care initializeaza toate campurile produsului.
     *
     * @param id     ID-ul produsului.
     * @param name   Numele produsului.
     * @param price  Pretul produsului.
     * @param stock  Cantitatea disponibila in stoc.
     */
    public Product(int id, String name, float price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Constructor gol necesar pentru reflectie sau instantiere implicita.
     */
    public Product() {
    }

    /**
     * Returneaza ID-ul produsului.
     *
     * @return ID-ul produsului.
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza ID-ul produsului.
     *
     * @param id Noua valoare pentru ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza numele produsului.
     *
     * @return Numele produsului.
     */
    public String getName() {
        return name;
    }

    /**
     * Seteaza numele produsului.
     *
     * @param name Noul nume.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returneaza pretul produsului.
     *
     * @return Pretul in format float.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Seteaza pretul produsului.
     *
     * @param price Noul pret.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Returneaza stocul disponibil al produsului.
     *
     * @return Cantitatea in stoc.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Seteaza stocul produsului.
     *
     * @param stock Noua cantitate in stoc.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returneaza o reprezentare textuala a produsului.
     *
     * @return String cu informatii despre produs.
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}