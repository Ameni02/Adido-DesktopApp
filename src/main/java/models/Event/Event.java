

package models.Event;

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
        return this.datestartevent;
    }

    public void setDatestartevent(Date datestartevent) {
        this.datestartevent = datestartevent;
    }

    public Date getDateendevent() {
        return this.dateendevent;
    }

    public void setDateendevent(Date dateendevent) {
        this.dateendevent = dateendevent;
    }

    public int getIdevent() {
        return this.idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public String getNameevent() {
        return this.nameevent;
    }

    public void setNameevent(String nameevent) {
        this.nameevent = nameevent;
    }

    public String getDescriptionevent() {
        return this.descriptionevent;
    }

    public void setDescriptionevent(String descriptionevent) {
        this.descriptionevent = descriptionevent;
    }

    public String getLocationevent() {
        return this.locationevent;
    }

    public void setLocationevent(String locationevent) {
        this.locationevent = locationevent;
    }

    public int getIdorganiser() {
        return this.idorganiser;
    }

    public void setIdorganiser(int idorganiser) {
        this.idorganiser = idorganiser;
    }

    public int getNbattendees() {
        return this.nbattendees;
    }

    public void setNbattendees(int nbattendees) {
        this.nbattendees = nbattendees;
    }

    public String getAffiche() {
        return this.affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public int getIdcountry() {
        return this.idcountry;
    }

    public void setIdcountry(int idcountry) {
        this.idcountry = idcountry;
    }

    public String toString() {
        return "Event{idevent=" + this.idevent + ", nameevent='" + this.nameevent + "', descriptionevent='" + this.descriptionevent + "', datestartevent='" + this.datestartevent + "', dateendevent='" + this.dateendevent + "', locationevent='" + this.locationevent + "', idorganiser=" + this.idorganiser + ", nbattendees=" + this.nbattendees + ", affiche='" + this.affiche + "', idcountry=" + this.idcountry + "}";
    }
}
