package models.Offer;

import java.time.LocalDate;
import java.util.Objects;

public class Offer {
    private int idpost,likespost,iduser,reservation;
    private LocalDate dateDebut,dateFin;
    private double prixpost;
    private String descriptionpost,photopost,nom_post,country;

    public Offer() {}
    public Offer(int iduser, int reservation, double prixpost, String descriptionpost, String photopost, String nom_post,LocalDate dateDebut,LocalDate dateFin,String country) {
        this.likespost = 0;
        this.iduser = iduser;
        this.reservation = reservation;
        this.prixpost = prixpost;
        this.descriptionpost = descriptionpost;
        this.photopost = photopost;
        this.nom_post = nom_post;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.country=country;
    }
    public Offer(String title, String Description, String Country) {
        this.nom_post = title;
        this.descriptionpost= Description;
        this.country= Country;
    }

    public Offer(int idpost, String nom_post, String descriptionpost, String country, String photopost, LocalDate dateDebut, LocalDate dateFin) {
        this.idpost = idpost;
        this.nom_post = nom_post;
        this.descriptionpost = descriptionpost;
        this.country = country;
        this.photopost = photopost;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public void setIdpost(int idpost) {
        this.idpost = idpost;
    }

    public int getIdpost() {
        return idpost;
    }

    public int getLikespost() {
        return likespost;
    }

    public void setLikespost(int likespost) {
        this.likespost = likespost;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getReservation() {
        return reservation;
    }

    public void setReservation(int reservation) {
        this.reservation = reservation;
    }

    public double getPrixpost() {
        return prixpost;
    }

    public void setPrixpost(double prixpost) {
        this.prixpost = prixpost;
    }

    public String getDescriptionpost() {
        return descriptionpost;
    }

    public void setDescriptionpost(String descriptionpost) {
        this.descriptionpost = descriptionpost;
    }

    public String getPhotopost() {
        return photopost;
    }

    public void setPhotopost(String photopost) {
        this.photopost = photopost;
    }

    public String getNom_post() {
        return nom_post;
    }

    public void setNom_post(String nom_post) {
        this.nom_post = nom_post;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "Offer{" +
                "idpost=" + idpost +
                ", likespost=" + likespost +
                ", iduser=" + iduser +
                ", reservation=" + reservation +
                ", prixpost=" + prixpost +
                ", descriptionpost='" + descriptionpost + '\'' +
                ", photopost='" + photopost + '\'' +
                ", nom_post='" + nom_post + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer offer)) return false;
        return getIdpost() == offer.getIdpost();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdpost(), getIduser(),getPrixpost());
    }
}