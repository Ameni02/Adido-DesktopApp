package controllers.product;

import javafx.event.ActionEvent;
import test.FxMain;

import java.io.IOException;

public class OptionProduct {
    public void ajouterpathP(ActionEvent actionEvent) throws IOException {
        FxMain.loadFXML("/AjouterProductFXML.fxml");
    }

    public void ShoppathP(ActionEvent actionEvent) throws IOException {
        FxMain.loadFXML("/shopProduit.fxml");
    }
}
