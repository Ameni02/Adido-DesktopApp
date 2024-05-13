package models.Blog;
import java.util.Objects;
public class LikePost {
    private  int idlike;
    private int idbloglike;
    public LikePost()
    {

    }
    public LikePost(int idbloglike) {

        this.idbloglike = idbloglike;
    }

    public LikePost(int idlike, int idbloglike) {
        this.idlike = idlike;
        this.idbloglike = idbloglike;
    }

    public int getIdlike() {
        return idlike;
    }

    public void setIdlike(int idlike) {
        this.idlike = idlike;
    }

    public int getIdbloglike() {
        return idbloglike;
    }

    public void setIdbloglike(int idbloglike) {
        this.idbloglike = idbloglike;
    }

    @Override
    public String toString() {
        return "LikePost{" +
                "idlike=" + idlike +
                ", idbloglike=" + idbloglike +
                '}';
    }
}
