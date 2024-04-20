package models;

import java.util.Objects;

public class Image {
    private int id_img;
    private String name_img;
    private int id_product;

    public Image(int id_img, String name_img, int id_product) {
            this.id_img = id_img;
            this.name_img = name_img;
            this.id_product = id_product;
    }

    public Image() {
    }

    public int getId_img() {
        return id_img;
    }

    public void setId_img(int id_img) {
        this.id_img = id_img;
    }

    public String getName_img() {
        return name_img;
    }

    public void setName_img(String name_img) {
        this.name_img = name_img;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id_img=" + id_img +
                ", name_img='" + name_img + '\'' +
                ", id_product=" + id_product +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id_img == image.id_img && id_product == image.id_product && Objects.equals(name_img, image.name_img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_img, name_img, id_product);
    }
}
