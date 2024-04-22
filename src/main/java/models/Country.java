package models;

public class Country {
   private int  idcountry;
   private String namecountry;
   private  String descriptioncountry;
   private  int idcontinent;

    public Country(int idcountry, String namecountry, String descriptioncountry, int idcontinent) {
        this.idcountry = idcountry;
        this.namecountry = namecountry;
        this.descriptioncountry = descriptioncountry;
        this.idcontinent = idcontinent;

    }

    public int getIdcountry() {
        return idcountry;
    }

    public void setIdcountry(int idcountry) {
        this.idcountry = idcountry;
    }

    public String getNamecountry() {
        return namecountry;
    }

    public void setNamecountry(String namecountry) {
        this.namecountry = namecountry;
    }

    public String getDescriptioncountry() {
        return descriptioncountry;
    }

    public void setDescriptioncountry(String descriptioncountry) {
        this.descriptioncountry = descriptioncountry;
    }

    public int getIdcontinent() {
        return idcontinent;
    }

    public void setIdcontinent(int idcontinent) {
        this.idcontinent = idcontinent;
    }

    @Override
    public String toString() {
        return "Country{" +
                "idcountry=" + idcountry +
                ", namecountry='" + namecountry + '\'' +
                ", descriptioncountry='" + descriptioncountry + '\'' +
                ", idcontinent=" + idcontinent +
                '}';
    }
}
