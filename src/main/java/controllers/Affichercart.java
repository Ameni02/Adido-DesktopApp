package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Affichercart {

    @FXML
    private TableView<?> cartTable;

    @FXML
    private Button checkoutButton;

    @FXML
    private Label taxesLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private void checkoutClicked(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Ajoutcommande.fxml")));
            checkoutButton.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    @FXML
    private void clearAllClicked(ActionEvent event) {
        // Implement logic to clear all items from the cart
    }

    @FXML
    private void goBackShoppingClicked(ActionEvent event) {
        // Implement logic to go back to the shopping page
    }
}
