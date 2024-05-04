package model;

public class Continent {
    private int  idcontinent;
    private  String namecontinent;
    private  String descriptioncontinent;

    public Continent(int idcontinent, String namecontinent, String descriptioncontinent) {
        this.idcontinent = idcontinent;
        this.namecontinent = namecontinent;
        this.descriptioncontinent = descriptioncontinent;
    }

    public int getIdcontinent() {
        return idcontinent;
    }

    public void setIdcontinent(int idcontinent) {
        this.idcontinent = idcontinent;
    }

    public String getNamecontinent() {
        return namecontinent;
    }

    public void setNamecontinent(String namecontinent) {
        this.namecontinent = namecontinent;
    }

    public String getDescriptioncontinent() {
        return descriptioncontinent;
    }

    public void setDescriptioncontinent(String descriptioncontinent) {
        this.descriptioncontinent = descriptioncontinent;
    }
}