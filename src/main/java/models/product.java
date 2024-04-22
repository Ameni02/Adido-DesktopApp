package models;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class product {
    private int id;
    private String categorieproduct;
    private String nomproduct;
    private float prixproduct;
    private int promotionproduct;
    private int stockproduct;
    private int approved=0;
    private int idCountry;




    public product() {

    }

    public product(String categorieproduct, String nomproduct, float prixproduct, int promotionproduct, int stockproduct, int approved,int idCountry) {
        this.categorieproduct = categorieproduct;
        this.nomproduct = nomproduct;
        this.prixproduct = prixproduct;
        this.promotionproduct = promotionproduct;
        this.stockproduct = stockproduct;
        this.approved = approved;
        this.idCountry = idCountry;
    }

    public product(String categorieproduct, String nomproduct, float prixproduct, int promotionproduct, int stockproduct,int idCountry) {
        this.categorieproduct = categorieproduct;
        this.nomproduct = nomproduct;
        this.prixproduct = prixproduct;
        this.promotionproduct = promotionproduct;
        this.stockproduct = stockproduct;
        this.idCountry = idCountry;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorieproduct() {
        return categorieproduct;
    }

    public void setCategorieproduct(String categorieproduct) {
        this.categorieproduct = categorieproduct;
    }

    public String getNomproduct() {
        return nomproduct;
    }

    public void setNomproduct(String nomproduct) {
        this.nomproduct = nomproduct;
    }

    public float getPrixproduct() {
        return prixproduct;
    }

    public void setPrixproduct(float prixproduct) {
        this.prixproduct = prixproduct;
    }

    public int getPromotionproduct() {
        return promotionproduct;
    }

    public void setPromotionproduct(int promotionproduct) {
        this.promotionproduct = promotionproduct;
    }

    public int getStockproduct() {
        return stockproduct;
    }

    public void setStockproduct(int stockproduct) {
        this.stockproduct = stockproduct;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }


    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        product person = (product) o;
        return id == person.id && Float.compare(prixproduct, person.prixproduct) == 0 && Float.compare(promotionproduct, person.promotionproduct) == 0 && stockproduct == person.stockproduct && approved == person.approved && Objects.equals(categorieproduct, person.categorieproduct) && Objects.equals(nomproduct, person.nomproduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categorieproduct, nomproduct, prixproduct, promotionproduct, stockproduct, approved);
    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", categorieproduct='" + categorieproduct + '\'' +
                ", nomproduct='" + nomproduct + '\'' +
                ", prixproduct=" + prixproduct +
                ", promotionproduct=" + promotionproduct +
                ", stockproduct=" + stockproduct +
                ", approved=" + approved +
                ", idCountry=" + idCountry +
                '}';
    }
}
