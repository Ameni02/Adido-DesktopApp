package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Event;
import servises.ServiceEvent;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class updateEvent {

    @FXML
    private TextField tfafficheupdate;

    @FXML
    private DatePicker tfdateendeventupdate;

    @FXML
    private DatePicker tfdatestarteventupdate;

    @FXML
    private TextField tfdescriptioneventupdate;

    @FXML
    private ComboBox<Integer> tfidcountryupdate;

    @FXML
    private TextField tfidorganiserupdate;

    @FXML
    private TextField tflocationeventupdate;

    @FXML
    private TextField tfnameeventupdate;

    @FXML
    private TextField tfnbattendeesupdate;

    @FXML
    void back_to_list(ActionEvent event) throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }
    private Event events;
    ServiceEvent ServiceEvent = new ServiceEvent();

    void retrievedata(Event event) {
        this.events = event;
        if (event != null) {
            String nameevent = event.getNameevent();
            tfnameeventupdate.setText(event.getNameevent());
            String discriptionevent = event.getDescriptionevent();
            tfdescriptioneventupdate.setText(event.getDescriptionevent());

            Date datestartevent = event.getDatestartevent(); // Retrieve the date from the event
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Define the format you want
            String dateString = sdf.format(datestartevent); // Format the date as a string

            // Set the formatted date as the value of the date picker field
            tfdatestarteventupdate.setValue(LocalDate.parse(dateString));

            Date dateendevent = event.getDateendevent(); // Retrieve the date from the event
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd"); // Define the format you want
            String dateString1 = sdf1.format(dateendevent); // Format the date as a string

            // Set the formatted date as the value of the date picker field
            tfdateendeventupdate.setValue(LocalDate.parse(dateString1));

            String locationevent = event.getLocationevent();
            tflocationeventupdate.setText(event.getLocationevent());

            String idorganiser = Integer.toString(event.getIdorganiser());
            tfidorganiserupdate.setText(idorganiser);

            String nbattendees = Integer.toString(event.getNbattendees());
            tfnbattendeesupdate.setText(nbattendees);

            String affiche = event.getAffiche();
            tfafficheupdate.setText(event.getAffiche());

            // Populate ComboBox with country IDs and select the current country ID
            try {
                List<Integer> countryIds = ServiceEvent.getAllCountryIds();
                tfidcountryupdate.getItems().addAll(countryIds);
                tfidcountryupdate.setValue(event.getIdcountry());
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }
    }

    // Method to populate ComboBox with country IDs
    private void populateCountryComboBox() {
        try {
            List<Integer> countryIds = ServiceEvent.getAllCountryIds();
            tfidcountryupdate.getItems().addAll(countryIds);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }


    @FXML
    void update_data(ActionEvent event) throws IOException, SQLException {
        String nameevent = tfnameeventupdate.getText().trim();
        String descriptionevent = tfdescriptioneventupdate.getText().trim();
        LocalDate localDateStart = tfdatestarteventupdate.getValue();
        LocalDate localDateEnd = tfdateendeventupdate.getValue();
        String locationevent = tflocationeventupdate.getText().trim();
        String idorganiser = tfidorganiserupdate.getText().trim();
        String nbattendees = tfnbattendeesupdate.getText().trim();
        String affiche = tfafficheupdate.getText().trim();
        int idcountry = tfidcountryupdate.getValue();

        try {
            ServiceEvent serviceEvent = new ServiceEvent();
            List<Integer> countryIds = servises.ServiceEvent.getAllCountryIds();

            // Ajouter les ID de pays au ComboBox
            tfidcountryupdate.getItems().addAll(countryIds);

            // Sélectionner le pays associé au produit
            int countryId = events.getIdcountry(); // Supposons que vous avez cette méthode dans la classe product
            // Vérifier si le pays associé au event est dans la liste des pays
            if (countryIds.contains(countryId)) {
                tfidcountryupdate.setValue(countryId);
            } else {
                // Si le pays n'est pas dans la liste, afficher un message d'erreur ou gérez le cas en conséquence
                System.out.println("Le pays associé au event n'est pas dans la liste.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur
        }



        // Validate inputs
        if (nameevent.isEmpty() || !nameevent.matches("[a-zA-Z]+")) {
            showAlert("Name event must not be empty and contain only letters.");
            return;
        }

        if (descriptionevent.isEmpty() || !descriptionevent.matches("[a-zA-Z]+")) {
            showAlert("Description event must not be empty and contain only letters.");
            return;
        }

        if (localDateStart == null || localDateEnd == null) {
            showAlert("Please select both start and end dates.");
            return;
        }

        if (locationevent.isEmpty() || !locationevent.matches("[a-zA-Z]+")) {
            showAlert("Location must not be empty and contain only letters.");
            return;
        }

        if (idorganiser.isEmpty() || !idorganiser.matches("\\d+")) {
            showAlert("Organiser ID must not be empty and contain only numbers.");
            return;
        }

        if (nbattendees.isEmpty() || !nbattendees.matches("\\d+")) {
            showAlert("Number of attendees must not be empty and contain only numbers.");
            return;
        }

        if (affiche.isEmpty() || affiche.matches("\\d+")) {
            showAlert("Affiche must not be empty and contain only letters.");
            return;
        }



        // Convert to integer values
        int idorganise;
        int nbattendeess;
        int idcountryy;
        try {
            idorganise = Integer.parseInt(idorganiser);
            nbattendeess = Integer.parseInt(nbattendees);
            idcountryy = Integer.parseInt(String.valueOf(idcountry));
        } catch (NumberFormatException e) {
            showAlert("Invalid numeric input.");
            return;
        }

        // Create the event object
        Date datestartevent = Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateendevent = Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Event eventToUpdate = new Event(nameevent, descriptionevent, datestartevent, dateendevent, locationevent, idorganise, nbattendeess, affiche, idcountryy);
        eventToUpdate.setIdevent(events.getIdevent());

        // Perform update operation
        servises.ServiceEvent.updateOne(eventToUpdate);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The event was updated successfully");
        alert.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
