package controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Event;
import servises.ServiceEvent;
import test.FxMain;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
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
    private ComboBox<Integer> countryComboBox; // Combo box for selecting country ID

    @FXML
    private Button imageevent;

    public static final String ACCOUNT_SID = "AC6fa7a4508189dd4848b872646bb798ac";
    public static final String AUTH_TOKEN = "3a68fa1f343241016c4be7ade77e0675";
    public void envoyerMessage(String nameevent) {
        // Initialisation de Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String contenuMessage = "event "+ nameevent + " added succesfuly.";

        // Création et envoi du message
        Message message = Message.creator(
                        new PhoneNumber("+21626585443"),  // Numéro de destination
                        new PhoneNumber("+16812011937"),  // Numéro expéditeur
                        contenuMessage)  // Contenu du message
                .create();

        // Affichage du SID du message pour vérification
        System.out.println(message.getSid());
    }
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
                tfnbattendees.getText().isEmpty() || tfaffiche.getText().isEmpty() || countryComboBox.getValue() == null) {
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


        try {
            // Convert LocalDate to java.util.Date
            java.util.Date utilDateStart = java.util.Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.util.Date utilDateEnd = java.util.Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Convert java.util.Date to java.sql.Date
            Date dateStart = new Date(utilDateStart.getTime());
            Date dateEnd = new Date(utilDateEnd.getTime());

            // Convertir prixString, promotionString, et stockString en entiers
            int idorga = Integer.parseInt(tfidorganiser.getText());
            int nbatendees = Integer.parseInt(tfnbattendees.getText());
            int country = countryComboBox.getValue(); // Get selected country from combo box

            // Si toutes les vérifications sont réussies, créer un nouvel objet product
            Event eventToAdded = new Event(nameevent, descriptionevent, dateStart, dateEnd, tflocationevent.getText(), idorga, nbatendees, tfaffiche.getText(), country);

            // Appeler le service pour ajouter le produit
            ServiceEvent sp = new ServiceEvent();
            envoyerMessage(tfnameevent.getText());
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
        assert countryComboBox != null : "fx:id=\"countryComboBox\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        // Populate country combo box, you need to replace this with your own method of populating country IDs
        //countryComboBox.getItems().addAll(1, 2, 3, 4, 5); // Example items, replace with your own data
        ServiceEvent serviceEvent = new ServiceEvent();
        try {
            // Obtenir tous les ID de pays
            List<Integer> countryIds = serviceEvent.getAllCountryIds();

            // Ajouter tous les ID de pays au ComboBox
            countryComboBox.getItems().addAll(countryIds);
        } catch (SQLException e) {
            // Gérez les exceptions en cas d'erreur de base de données
            e.printStackTrace();
        }
    }

    @FXML
    void goto_dashboard(ActionEvent event) throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }

    @FXML
    void goto_show_for_user(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Show_data_for_user.fxml");
    }

    public void upload_img(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload your profile picture");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName().toLowerCase();
            if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                tfaffiche.setText(selectedFile.getPath());
            } else {
                System.out.println("Invalid file format. Please select a PNG or JPG file.");
            }
        } else {
            System.out.println("No file selected");
        }
    }


}
