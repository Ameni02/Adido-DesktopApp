package models.Ecommerce;

import java.util.Date;
import java.util.Objects;

public class Commande {
    private final int idCommande;
    private Date dateCommande;
    private String etatCommande;
    private String coupon;
    private String commandeAdresse;
    private int commandePhoneNumber;
    private String additionalInformation;

    // Constructeur
    public Commande(int idCommande, Date dateCommande, String etatCommande, String coupon, String commandeAdresse, int commandePhoneNumber, String additionalInformation) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.etatCommande = etatCommande;
        this.coupon = coupon;
        this.commandeAdresse = commandeAdresse;
        this.commandePhoneNumber = commandePhoneNumber;
        this.additionalInformation = additionalInformation;
    }

    // Getters et Setters


    public int getIdCommande() {
        return idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCommandeAdresse() {
        return commandeAdresse;
    }

    public void setCommandeAdresse(String commandeAdresse) {
        this.commandeAdresse = commandeAdresse;
    }

    public int getCommandePhoneNumber() {
        return commandePhoneNumber;
    }

    public void setCommandePhoneNumber(int commandePhoneNumber) {
        this.commandePhoneNumber = commandePhoneNumber;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commande commande)) return false;
        return getIdCommande() == commande.getIdCommande() && getCommandePhoneNumber() == commande.getCommandePhoneNumber() && Objects.equals(getDateCommande(), commande.getDateCommande()) && Objects.equals(getEtatCommande(), commande.getEtatCommande()) && Objects.equals(getCoupon(), commande.getCoupon()) && Objects.equals(getCommandeAdresse(), commande.getCommandeAdresse()) && Objects.equals(getAdditionalInformation(), commande.getAdditionalInformation());
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + idCommande +
                ", dateCommande=" + dateCommande +
                ", etatCommande='" + etatCommande + '\'' +
                ", coupon='" + coupon + '\'' +
                ", commandeAdresse='" + commandeAdresse + '\'' +
                ", commandePhoneNumber=" + commandePhoneNumber +
                ", additionalInformation='" + additionalInformation + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCommande(), getDateCommande(), getEtatCommande(), getCoupon(), getCommandeAdresse(), getCommandePhoneNumber(), getAdditionalInformation());
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
