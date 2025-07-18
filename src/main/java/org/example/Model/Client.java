package org.example.Model;

/**
 * Clasa Client reprezinta un client al aplicatiei.
 * Contine informatii precum ID-ul, numele, adresa si adresa de email.
 * Este folosita in operatiile de gestiune clienti si in comenzile plasate.
 */
public class Client {
    private int id;
    private String name;
    private String email;
    private String address;

    /**
     * Constructor complet pentru initializarea unui client.
     *
     * @param id      ID-ul clientului.
     * @param name    Numele clientului.
     * @param email   Adresa de email a clientului.
     * @param address Adresa fizica a clientului.
     */
    public Client(int id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     * Constructor gol necesar pentru reflectie sau instantiere implicita.
     */
    public Client() {
    }

    /**
     * Returneaza ID-ul clientului.
     *
     * @return ID-ul clientului.
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza ID-ul clientului.
     *
     * @param id Noua valoare a ID-ului.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza numele clientului.
     *
     * @return Numele clientului.
     */
    public String getName() {
        return name;
    }

    /**
     * Seteaza numele clientului.
     *
     * @param name Noul nume.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returneaza adresa de email a clientului.
     *
     * @return Email-ul clientului.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Seteaza adresa de email a clientului.
     *
     * @param email Noul email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returneaza adresa fizica a clientului.
     *
     * @return Adresa clientului.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Seteaza adresa fizica a clientului.
     *
     * @param address Noua adresa.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returneaza o reprezentare text a obiectului Client.
     *
     * @return String cu informatii despre client.
     */
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}