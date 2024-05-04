package models.Blog;

import java.util.Objects;

public class Image {
    private int id_img;
    private String name_img;
    private int idblog;

    public Image(int id_img, String name_img, int idblog) {
        this.id_img = id_img;
        this.name_img = name_img;
        this.idblog = idblog;
    }

    public Image() {

    }

    public Image(String imageName) {
        this.name_img = imageName;
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

    public int getIdblog() {
        return idblog;
    }

    public void setIdblog(int idblog) {
        this.idblog = idblog;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_img, name_img, idblog);
    }

    public void add(Image image) {
    }


}
