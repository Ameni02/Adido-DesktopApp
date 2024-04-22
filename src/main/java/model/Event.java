package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Event {
    private int idevent;
    private String nameevent;
    private String descriptionevent;
    private Date datestartevent;
    private Date dateendevent;
    private String locationevent;
    private int idorganiser;
    private int nbattendees;
    private String affiche;
    private int idcountry;

    public Event() {
    }

    public Event(int idevent, String nameevent, String descriptionevent, Date datestartevent, Date dateendevent, String locationevent, int idorganiser, int nbattendees, String affiche, int idcountry) {
        this.idevent = idevent;
        this.nameevent = nameevent;
        this.descriptionevent = descriptionevent;
        this.datestartevent = datestartevent;
        this.dateendevent = dateendevent;
        this.locationevent = locationevent;
        this.idorganiser = idorganiser;
        this.nbattendees = nbattendees;
        this.affiche = affiche;
        this.idcountry = idcountry;
    }

    public Event(String nameevent, String descriptionevent, Date datestartevent, Date dateendevent, String locationevent, int idorganiser, int nbattendees, String affiche, int idcountry) {
        this.nameevent = nameevent;
        this.descriptionevent = descriptionevent;
        this.datestartevent = datestartevent;
        this.dateendevent = dateendevent;
        this.locationevent = locationevent;
        this.idorganiser = idorganiser;
        this.nbattendees = nbattendees;
        this.affiche = affiche;
        this.idcountry = idcountry;
    }

    public Date getDatestartevent() {
        return datestartevent;
    }

    public void setDatestartevent(Date datestartevent) {
        this.datestartevent = datestartevent;
    }

    public Date getDateendevent() {
        return dateendevent;
    }

    public void setDateendevent(Date dateendevent) {
        this.dateendevent = dateendevent;
    }

    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public String getNameevent() {
        return nameevent;
    }

    public void setNameevent(String nameevent) {
        this.nameevent = nameevent;
    }

    public String getDescriptionevent() {
        return descriptionevent;
    }

    public void setDescriptionevent(String descriptionevent) {
        this.descriptionevent = descriptionevent;
    }



    public String getLocationevent() {
        return locationevent;
    }

    public void setLocationevent(String locationevent) {
        this.locationevent = locationevent;
    }

    public int getIdorganiser() {
        return idorganiser;
    }

    public void setIdorganiser(int idorganiser) {
        this.idorganiser = idorganiser;
    }

    public int getNbattendees() {
        return nbattendees;
    }

    public void setNbattendees(int nbattendees) {
        this.nbattendees = nbattendees;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public int getIdcountry() {
        return idcountry;
    }

    public void setIdcountry(int idcountry) {
        this.idcountry = idcountry;
    }

    @Override
    public String toString() {
        return "Event{" +
                "idevent=" + idevent +
                ", nameevent='" + nameevent + '\'' +
                ", descriptionevent='" + descriptionevent + '\'' +
                ", datestartevent='" + datestartevent + '\'' +
                ", dateendevent='" + dateendevent + '\'' +
                ", locationevent='" + locationevent + '\'' +
                ", idorganiser=" + idorganiser +
                ", nbattendees=" + nbattendees +
                ", affiche='" + affiche + '\'' +
                ", idcountry=" + idcountry +
                '}';
    }}
