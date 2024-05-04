package models.Blog;

public class Comments {

    private int idcomment;
    private String contentcomment;
    private int idblog;
    private int iduser;
public Comments(){

}
    public Comments(int idcomment, String contentcomment, int idblog) {
        this.idcomment = idcomment;
        this.contentcomment = contentcomment;
        this.idblog = idblog;
    }
    public Comments(int idcomment, String contentcomment, int idblog,int iduser) {
        this.idcomment = idcomment;
        this.contentcomment = contentcomment;
        this.idblog = idblog;
        this.iduser=iduser;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public Comments(String commentcontent) {

        this.contentcomment = commentcontent;
    }

    public Comments(int idblog, String content) {

        this.contentcomment = content;
        this.idblog = idblog;
    }

    public int getIdcomment() {
        return idcomment;
    }

    public void setIdcomment(int idcomment) {
        this.idcomment = idcomment;
    }

    public String getContentcomment() {
        return contentcomment;
    }

    public void setContentcomment(String contentcomment) {
        this.contentcomment = contentcomment;
    }

    public int getIdblog() {
        return idblog;
    }

    public void setIdblog(int idblog) {
        this.idblog = idblog;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "idcomment=" + idcomment +
                ", contentcomment='" + contentcomment + '\'' +
                ", idblog=" + idblog +
                '}';
    }
}
