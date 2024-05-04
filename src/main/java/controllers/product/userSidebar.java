package controllers.product;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import test.FxMain;

import java.io.IOException;

public class userSidebar {

    @FXML
    private Label level;

    @FXML
    private ImageView picture;

    @FXML
    private Label role;

    @FXML
    private Label username;

    @FXML
    private Label welcome;

    @FXML
    void disconnect(ActionEvent event) {

    }

    @FXML
    void goto_blog(ActionEvent event) {

    }

    @FXML
    void goto_dashboard(ActionEvent event) throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }

    @FXML
    void goto_edit(ActionEvent event) {

    }

    @FXML
    void goto_event(ActionEvent event) {

    }

    @FXML
    void goto_forum(ActionEvent event) {

    }

    @FXML
    void goto_projects(ActionEvent event) {

    }

    @FXML
    void goto_shop(ActionEvent event) throws IOException {
        FxMain.loadFXML("/OptionProduct.fxml");

    }
}
