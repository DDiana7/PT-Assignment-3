package org.example.BusinessLogic;

import org.example.DataAccess.ClientDAO;
import org.example.Model.Client;

import java.util.List;

/**
 * Clasa ClientBLL contine logica de afaceri pentru manipularea clientilor.
 * Aici se realizeaza validarile si interactiunea cu baza de date prin ClientDAO.
 */
public class ClientBLL {
    private final ClientDAO clientDAO;

    /**
     * Constructorul clasei. Initializeaza instanta ClientDAO.
     */
    public ClientBLL() {
        this.clientDAO = new ClientDAO();
    }

    /**
     * Gaseste un client dupa ID-ul sau.
     *
     * @param id ID-ul clientului cautat.
     * @return Obiectul Client corespunzator.
     * @throws IllegalArgumentException daca ID-ul este invalid sau clientul nu este gasit.
     */
    public Client findClientById(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID client invalid");
        Client client = clientDAO.findById(id);
        if (client == null) throw new IllegalArgumentException("Clientul cu ID-ul " + id + " nu a fost gasit.");
        return client;
    }

    /**
     * Returneaza lista tuturor clientilor existenti in baza de date.
     *
     * @return Lista cu obiecte de tip Client.
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll().stream().toList();
    }

    /**
     * Creeaza un nou client in baza de date.
     *
     * @param client Obiectul Client care trebuie inserat.
     * @throws IllegalArgumentException daca datele clientului sunt invalide.
     */
    public void insertClient(Client client) {
        validateClient(client);
        clientDAO.insert(client);
    }

    /**
     * Actualizeaza informatiile unui client existent.
     *
     * @param client Obiectul Client actualizat.
     * @throws IllegalArgumentException daca clientul nu exista sau datele sunt invalide.
     */
    public void updateClient(Client client) {
        if (client == null || client.getId() <= 0)
            throw new IllegalArgumentException("Client invalid");
        findClientById(client.getId());
        validateClient(client);
        clientDAO.update(client);
    }

    /**
     * Sterge un client dupa ID.
     *
     * @param id ID-ul clientului care trebuie sters.
     * @throws IllegalArgumentException daca clientul nu exista sau ID-ul este invalid.
     */
    public void deleteClient(int id) {
        findClientById(id);
        clientDAO.deleteById(id);
    }

    /**
     * Valideaza campurile unui obiect Client.
     *
     * @param client Obiectul Client de validat.
     * @throws IllegalArgumentException daca numele, emailul sau adresa sunt invalide.
     */
    private void validateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Clientul nu poate fi null");
        }
        if (client.getName() == null || client.getName().trim().isEmpty()
                || client.getEmail() == null || client.getEmail().trim().isEmpty()
                || client.getAddress() == null || client.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Toate campurile clientului trebuie completate");
        }
    }

    /**
     * Returneaza urmatorul ID disponibil pentru adaugarea unui nou client.
     * ID-ul este calculat pe baza valorii maxime existente in tabela Client.
     *
     * @return Valoarea urmatorului ID (MAX(id) + 1) sau 1 daca tabela este goala.
     */
    public int getNextClientId() {
        return clientDAO.getNextClientId();
    }
}