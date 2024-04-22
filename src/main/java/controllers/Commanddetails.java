package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Ecommerce.Commande;
import services.ServiceCommande;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import java.sql.SQLException;
import java.util.Objects;


public class Commanddetails {

    @FXML
    private Button backtocart;

    @FXML
    private TextField tfadresse;

    @FXML
    private TextArea tfinfo;

    @FXML
    private TextField tfphone;

    @FXML
    void ajoutcommandeController(ActionEvent event) {
        // Check if any of the input fields are empty
        if (tfadresse.getText().isEmpty() || tfphone.getText().isEmpty() || tfinfo.getText().isEmpty()) {
            showAlert("Empty Fields", "Please fill in all the fields.");
            return;
        }

        // Validate address input
        String adresse = tfadresse.getText();
        if (adresse.length() < 3) {
            showAlert("Invalid Address", "Please enter a valid adress.");
            return;
        }

        // Validate phone number input
        String phoneText = tfphone.getText();
        if (!phoneText.matches("\\d{8}")) { // Check if phoneText contains exactly 8 digits
            showAlert("Invalid Phone Number", "Please enter a valid phone number.");
            return;
        }

        // Get today's date
        LocalDate localDate = LocalDate.now();
        // Convert LocalDate to Date
        Date date = Date.valueOf(localDate);

        // Retrieve data from text fields
        int phone = Integer.parseInt(phoneText);
        String info = tfinfo.getText();

        // Set the etat to "process"
        String etat = "process";

        // Create the Commande object with today's date, "process" etat, and retrieved data
        Commande commande = new Commande(0, date, etat, "", adresse, phone, info);

        try {
            ServiceCommande serviceCommande = new ServiceCommande();
            serviceCommande.insertOne(commande);
            showSuccessAlert("Success", "Thank you for ordering. Your order is in process.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while inserting the command.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void backtocart(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/affichercart.fxml")));
            backtocart.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    @FXML
    void historique(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/historique.fxml")));
            backtocart.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }



    }


}


