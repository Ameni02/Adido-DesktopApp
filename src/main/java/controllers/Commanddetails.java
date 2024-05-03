package controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Ecommerce.Commande;
import models.Ecommerce.Panier;
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
            showAlert("Invalid Address", "Please enter a valid address.");
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

        // Create a new instance of Panier
        Panier panier = new Panier(0, 0, 1, 1, "null"); // create an instance of Panier
        double prixTotal = panier.getPrixTotal(); // get prixTotal from Panier

        // Process the payment
        if (!processPayment(prixTotal, "tok_visa")) {
            showAlert("Payment Error", "An error occurred while processing the payment. Please try again.");
            return;
        }

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

    public boolean processPayment(double amount, String token) {
        // Configuration de la clé secrète de l'API Stripe
        Stripe.apiKey = ("sk_test_51PCTpzAITMBvnG134uDPYNnhJK2W4krUJMp9FijdxzuSIxslKA9ROyYMsxxbBJ7lQTYOsWR2eqSHQnCg73UYW1PA00aLyfeH9O");

        try {
            // Création de la charge
            Charge charge = Charge.create(
                    new ChargeCreateParams.Builder()
                            .setAmount((long) (amount * 100)) // Montant en centimes
                            .setCurrency("usd") // Devise
                            .setSource(token) // Token de carte de crédit
                            .build()
            );

            // Le paiement a réussi
            return true;
        }  catch (StripeException e) {
            e.printStackTrace();
            System.out.println("Error message: " + e.getMessage());
            return false;
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


