package controllers.Blog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class CommentSection {
    @FXML
    public Label commentShow;
    @FXML
    public  Label NameUser;

    public CommentSection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/Blog/CommentSection.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void setDataC(String content, int iduser)
{
     commentShow.setText(content);
    NameUser.setText(String.valueOf(iduser));
}

}