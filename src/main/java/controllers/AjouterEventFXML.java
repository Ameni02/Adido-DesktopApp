package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Event;
import servises.ServiceEvent;
import test.FxMain;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;


public class AjouterEventFXML {

    @FXML
    private TextField tfnameevent;

    @FXML
    private TextField tfdescriptionevent;

    @FXML
    private DatePicker tfdatestartevent;

    @FXML
    private DatePicker tfdateendevent;

    @FXML
    private TextField tflocationevent;

    @FXML
    private TextField tfidorganiser;

    @FXML
    private TextField tfnbattendees;

    @FXML
    private TextField tfaffiche;

    @FXML
    private TextField tfidcountry;


    @FXML
    void AjouterEvent(ActionEvent event) throws SQLException {
        // Récupération des valeurs des champs de texte
        String nameevent = tfnameevent.getText();
        String descriptionevent = tfdescriptionevent.getText();

        LocalDate localDateStart = tfdatestartevent.getValue();
        LocalDate localDateEnd = tfdateendevent.getValue();

        // Vérification de la non-nullité des champs
        if (nameevent.isEmpty() || descriptionevent.isEmpty() || localDateStart == null ||
                localDateEnd == null || tflocationevent.getText().isEmpty() || tfidorganiser.getText().isEmpty() ||
                tfnbattendees.getText().isEmpty() || tfaffiche.getText().isEmpty() || tfidcountry.getText().isEmpty()) {
            showErrorAlert("Error", "All fields are required.");
            return;
        }

        // Vérification de la validité des champs
        if (!nameevent.matches("[a-zA-Z]+")) {
            showErrorAlert("Error", "Name must contain only letters.");
            return;
        }
        if (!descriptionevent.matches("[a-zA-Z]+")) {
            showErrorAlert("Error", "Description must contain only letters.");
            return;
        }
        if (!tflocationevent.getText().matches("[a-zA-Z]+")) {
            showErrorAlert("Error", "Location must contain only letters.");
            return;
        }
        if (!tfidorganiser.getText().matches("\\d+")) {
            showErrorAlert("Error", "Organizer ID must contain only numbers.");
            return;
        }
        if (!tfnbattendees.getText().matches("\\d+")) {
            showErrorAlert("Error", "Number of attendees must contain only numbers.");
            return;
        }
        if (!tfaffiche.getText().matches("[a-zA-Z]+")) {
            showErrorAlert("Error", "Affiche must contain only letters.");
            return;
        }
        if (!tfidcountry.getText().matches("\\d+")) {
            showErrorAlert("Error", "Country ID must contain only numbers.");
            return;
        }

        // Convert LocalDate to java.util.Date
        java.util.Date utilDateStart = java.util.Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date utilDateEnd = java.util.Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Convert java.util.Date to java.sql.Date
        Date dateStart = new Date(utilDateStart.getTime());
        Date dateEnd = new Date(utilDateEnd.getTime());

        try {
            // Convertir prixString, promotionString, et stockString en entiers
            int idorga = Integer.parseInt(tfidorganiser.getText());
            int nbatendees = Integer.parseInt(tfnbattendees.getText());
            int country = Integer.parseInt(tfidcountry.getText());

            // Si toutes les vérifications sont réussies, créer un nouvel objet product
            Event eventToAdded = new Event(nameevent, descriptionevent, dateStart, dateEnd, tflocationevent.getText(), idorga, nbatendees, tfaffiche.getText(), country);

            // Appeler le service pour ajouter le produit
            ServiceEvent sp = new ServiceEvent();
            sp.insertOne(eventToAdded);

            // Afficher un message de réussite
            Alert alert = new Alert((Alert.AlertType.INFORMATION), "The product was added successfully.");
            alert.show();
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si les valeurs de prix, promotion ou stock ne sont pas des entiers valides
            showErrorAlert("Error", "Invalid input for organizer ID, attendees number, or country ID.");
        }
    }


    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    @FXML
    void initialize() {
        assert tfnameevent != null : "fx:id=\"tfnameevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert tfdescriptionevent != null : "fx:id=\"tfdescriptionevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert tfdatestartevent != null : "fx:id=\"tfdatestartevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert tfdateendevent != null : "fx:id=\"tfdateendevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert tflocationevent != null : "fx:id=\"tflocationevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert tfidorganiser != null : "fx:id=\"tfidorganiser\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert tfnbattendees != null : "fx:id=\"tfnbattendees\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert tfaffiche != null : "fx:id=\"tfaffiche\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert tfidcountry != null : "fx:id=\"tfidcountry\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
    }

    @FXML
    void goto_dashboard(ActionEvent event) throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }
}
