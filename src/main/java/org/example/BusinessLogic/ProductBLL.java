package org.example.BusinessLogic;

import org.example.DataAccess.ProductDAO;
import org.example.Model.Product;

import java.util.List;

/**
 * Clasa ProductBLL contine logica de afaceri pentru manipularea produselor.
 * Aici se efectueaza validarile legate de stoc si interactiunea cu ProductDAO.
 */
public class ProductBLL {
    private final ProductDAO productDAO;

    /**
     * Constructorul clasei. Initializeaza instanta ProductDAO.
     */
    public ProductBLL() {
        this.productDAO = new ProductDAO();
    }

    /**
     * Gaseste un produs dupa ID.
     *
     * @param id ID-ul produsului cautat.
     * @return Obiectul Product daca este gasit.
     * @throws IllegalArgumentException daca ID-ul este invalid sau produsul nu este gasit.
     */
    public Product findProductById(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID produs invalid");
        Product product = productDAO.findById(id);
        if (product == null) throw new IllegalArgumentException("Produsul cu ID-ul " + id + " nu a fost gasit.");
        return product;
    }

    /**
     * Returneaza lista tuturor produselor.
     *
     * @return Lista cu obiecte de tip Product.
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll().stream().toList();
    }

    /**
     * Insereaza un nou produs in baza de date.
     *
     * @param product Obiectul Product care trebuie inserat.
     * @throws IllegalArgumentException daca produsul este invalid.
     */
    public void insertProduct(Product product) {
        validateProduct(product);
        productDAO.insert(product);
    }

    /**
     * Actualizeaza un produs existent.
     *
     * @param product Obiectul Product actualizat.
     * @throws IllegalArgumentException daca produsul nu exista sau datele sunt invalide.
     */
    public void updateProduct(Product product) {
        if (product.getId() <= 0) throw new IllegalArgumentException("ID produs invalid");
        Product existing = findProductById(product.getId());
        validateProduct(product);
        productDAO.update(product);
    }

    /**
     * Sterge un produs dupa ID.
     *
     * @param id ID-ul produsului.
     * @throws IllegalArgumentException daca produsul nu exista sau ID-ul este invalid.
     */
    public void deleteProduct(int id) {
        Product existing = findProductById(id);
        productDAO.deleteById(id);
    }

    /**
     * Actualizeaza stocul unui produs.
     *
     * @param productId ID-ul produsului.
     * @param newStock Noua valoare pentru stoc.
     * @throws IllegalArgumentException daca ID-ul este invalid sau stocul este negativ.
     */
    public void updateStock(int productId, int newStock) {
        if (newStock < 0) throw new IllegalArgumentException("Stocul trebuie sa fie pozitiv");
        Product product = findProductById(productId);
        product.setStock(newStock);
        productDAO.update(product);
    }

    /**
     * Valideaza un obiect Product.
     *
     * @param product Obiectul Product de validat.
     * @throws IllegalArgumentException daca datele produsului nu sunt valide.
     */
    private void validateProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Produsul nu poate fi null");
        if (product.getName() == null || product.getName().trim().isEmpty())
            throw new IllegalArgumentException("Numele produsului nu poate fi gol");
        if (product.getPrice() < 0) throw new IllegalArgumentException("Pretul trebuie sa fie pozitiv");
        if (product.getStock() < 0) throw new IllegalArgumentException("Stocul trebuie sa fie pozitiv");
    }

    /**
     * Returneaza urmatorul ID disponibil pentru adaugarea unui nou produs.
     * ID-ul este calculat pe baza valorii maxime existente in tabela Product.
     *
     * @return Valoarea urmatorului ID (MAX(id) + 1) sau 1 daca tabela este goala.
     */
    public int getNextProductId() {
        return productDAO.getNextProductId();
    }

}