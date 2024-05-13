package controllers.product;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import test.FxMain;

import java.io.IOException;

public class adminSideBar {

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private AnchorPane sidebarAdmin;

    @FXML
    void handleClicks(ActionEvent event) {

    }
    @FXML
    void goto_blog(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Event/Blog/ShowAllBlog.fxml");
    }

    @FXML
    void goto_dashboard(ActionEvent event) throws IOException {

        FxMain.loadFXML("/HomePage.fxml");
    }

    @FXML
    void goto_edit(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Ajoutcommande.fxml");
    }

    @FXML
    void goto_event(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Event/ShowAll.fxml");
    }

    @FXML
    void goto_forum(ActionEvent event) throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }

    @FXML
    void goto_project(ActionEvent event) {

    }

    @FXML
    void goto_shop(ActionEvent event) throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }

    @FXML
    void goto_user(ActionEvent event) {

    }

}
