package controllers;

import javafx.fxml.FXML;
import services.ServicePerson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import test.FxMain;

import java.awt.*;
import java.io.IOException;

public class Resetpw1 {
    @FXML
    private Button btncode;

    @FXML
    private TextField code;
    public void Verify() throws IOException {
        ServicePerson person  = new ServicePerson();
        String code1 = code.getText();
        String code2 = person.selectSecret(1);
        String code3 = HomePage.getTOTPCode(code2);
        if (code3.equals(code1))
        {
            FxMain.loadFXML("/rsetpw2.fxml");
        }

    }
}
