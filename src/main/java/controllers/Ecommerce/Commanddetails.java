package controllers.Ecommerce;
import controllers.Ecommerce.Affichercart;
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
import javafx.scene.layout.AnchorPane;
import models.Commande;
import models.Panier;
import org.controlsfx.control.Notifications;
import services.ServiceCommande;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Commanddetails {
    @FXML
    private Button btnmode;

    @FXML
    private AnchorPane parent;


    @FXML
    private Button backtocart;

    @FXML
    private TextField tfadresse;

    @FXML
    private TextArea tfinfo;

    @FXML
    private TextField tfphone;

    @FXML
    void ajoutcommandeController(ActionEvent event) throws SQLException {
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
        // Panier panier = new Panier(); // create an instance of Panier
        // double prixTotal = panier.getPrixTotal(); // get prixTotal from Panier
        int totalPrice = 0;

        totalPrice = Affichercart.calculateTotalPrice();

        // Process the payment
        if (!processPayment(totalPrice, "tok_visa")) {
            showAlert("Payment Error", "An error occurred while processing the payment. Please try again.");
            return;
        }

        // Create the Commande object with today's date, "process" etat, and retrieved data
        Commande commande = new Commande(0, date, etat, "", adresse, phone, info);

        try {
            ServiceCommande serviceCommande = new ServiceCommande();

            // Send email notification with personalized content
            String emailContent = "<html><body>" +
                    "<h1>Hello,</h1>" +
                    "<p>Thank you for shopping with Adido!</p>" +
                    "<p>Your order has been successfully placed and <strong>payed</strong>. Below are the details:</p>" +
                    "<ul>" +
                    "<li><strong>Command Address:</strong> " + adresse + "</li>" +
                    "<li><strong>Phone Number:</strong> " + phone + "</li>" +

                    "<li><strong>Total Price:</strong> " + totalPrice + "</li>" +
                    "</ul>" +
                    "<p>If you have any questions or concerns regarding your order, feel free to contact us.</p>" +
                    "<p>Best regards,<br>Adido</p>" +
                    "</body></html>";
            sendEmail("zbiramenikhan@gmail.com", "Order Confirmation", emailContent);
            serviceCommande.insertOne(commande);
            //Notification
            Notifications.create()
                    .title("Success")
                    .text("Thank you for ordering. Please check your email for the receipt.")
                    .showConfirm();
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
        } catch (StripeException e) {
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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void historique(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/historique.fxml")));
            backtocart.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void sendEmail(String recipientEmail, String subject, String emailContent) {
        // Paramètres de la session pour envoyer l'e-mail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP de Gmail
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Port SMTP pour TLS (587)
        props.put("mail.smtp.auth", "true"); // Authentification requise
        props.put("mail.smtp.starttls.enable", "true"); // Activation de TLS

        // Informations d'authentification pour votre compte Gmail
        String username = "adidotravelladido@gmail.com"; // Votre adresse e-mail Gmail
        String password = "yrka cnai sgaz fcda"; // Votre mot de passe Gmail

        // Création de la session avec les informations d'authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Adresse e-mail du destinataire
            message.setSubject(subject); // Objet de l'e-mail
            message.setText(emailContent); // Contenu de l'e-mail
            message.setContent(emailContent, "text/html");

            // Envoi de l'e-mail
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
    private boolean isLightMode = true ;
    public void changeMode(ActionEvent actionEvent) {
        isLightMode = !isLightMode ;
        if(isLightMode){
            setLightMode();

        }else {
            setDarkMode();
        }

    }
    private void setLightMode(){
        parent.getStylesheets().remove("DarkMode.css");
        parent.getStylesheets().add("LightMode.css");

    }
    private void setDarkMode(){
        parent.getStylesheets().remove("LightMode.css");
        parent.getStylesheets().add("DarkMode.css");

    }
}