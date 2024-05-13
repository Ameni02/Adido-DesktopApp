package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import services.ServicePerson;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Rsetpw2 {

    @FXML
    private TextField one;

    @FXML
    private Button three;

    @FXML
    private TextField two;

    @FXML
    public void three1(ActionEvent event) throws SQLException, IOException {
        ServicePerson person = new ServicePerson();
        person.updatePw(one.getText(),1);
        FxMain.loadFXML("/AjouterPersonneFXML.fxml");

    }
}
