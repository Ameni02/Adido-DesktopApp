package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import test.FxMain;

import javax.swing.*;
import java.io.IOException;

public class Resetpw {

    @FXML
    private TextField email;

    @FXML
    private Button emailbtn;

    public void email() throws IOException {

        FxMain.loadFXML("/resetpw1.fxml");
    }

}
