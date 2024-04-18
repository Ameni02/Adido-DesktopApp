package models.Ecommerce;

import java.util.Objects;

public class Panier {
    private int idPanier;
    private int id;
    private int quantity;
    private int prixTotal;
    private String productName;

    // Constructeur
    public Panier(int idPanier, int id, int quantity, int prixTotal, String productName) {
        this.idPanier = idPanier;
        this.id = id;
        this.quantity = quantity;
        this.prixTotal = prixTotal;
        this.productName = productName;
    }

    // Getters et Setters
    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Panier panier)) return false;
        return getIdPanier() == panier.getIdPanier() && getId() == panier.getId() && getQuantity() == panier.getQuantity() && getPrixTotal() == panier.getPrixTotal() && Objects.equals(getProductName(), panier.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPanier(), getId(), getQuantity(), getPrixTotal(), getProductName());
    }

    public int getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(int prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idPanier=" + idPanier +
                ", id=" + id +
                ", quantity=" + quantity +
                ", prixTotal=" + prixTotal +
                ", productName='" + productName + '\'' +
                '}';
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
