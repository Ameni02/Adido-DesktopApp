package controllers.product;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import test.FxMain;

import java.io.IOException;
import java.util.Objects;

public class OptionProduct {
    @FXML
    private Button dashbord;
    public void ajouterpathP(ActionEvent actionEvent) throws IOException {
        FxMain.loadFXML("/AjouterProductFXML.fxml");
    }

    public void ShoppathP(ActionEvent actionEvent) throws IOException {
        FxMain.loadFXML("/shopProduit.fxml");
    }
    public void Dachboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ShowAll.fxml")));
        dashbord.getScene().setRoot(root);
    }
}
