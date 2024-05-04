
package models.Event;

public class Country {
    private int idcountry;
    private String namecountry;
    private String descriptioncountry;
    private int idcontinent;

    public Country(int idcountry, String namecountry, String descriptioncountry, int idcontinent) {
        this.idcountry = idcountry;
        this.namecountry = namecountry;
        this.descriptioncountry = descriptioncountry;
        this.idcontinent = idcontinent;
    }

    public int getIdcountry() {
        return this.idcountry;
    }

    public void setIdcountry(int idcountry) {
        this.idcountry = idcountry;
    }

    public String getNamecountry() {
        return this.namecountry;
    }

    public void setNamecountry(String namecountry) {
        this.namecountry = namecountry;
    }

    public String getDescriptioncountry() {
        return this.descriptioncountry;
    }

    public void setDescriptioncountry(String descriptioncountry) {
        this.descriptioncountry = descriptioncountry;
    }

    public int getIdcontinent() {
        return this.idcontinent;
    }

    public void setIdcontinent(int idcontinent) {
        this.idcontinent = idcontinent;
    }

    public String toString() {
        return "Country{idcountry=" + this.idcountry + ", namecountry='" + this.namecountry + "', descriptioncountry='" + this.descriptioncountry + "', idcontinent=" + this.idcontinent + "}";
    }
}
