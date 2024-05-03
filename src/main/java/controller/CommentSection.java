package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Parent;

import java.io.IOException;

public class CommentSection {
    @FXML
    public Label commentShow;

    public CommentSection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommentSection.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
