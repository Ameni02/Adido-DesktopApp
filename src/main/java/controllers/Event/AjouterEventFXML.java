

package controllers.Event;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import models.Event.Event;
import services.Event.ServiceEvent;
import test.FxMain;

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
    private ComboBox<Integer> countryComboBox;
    @FXML
    private Button imageevent;
    public static final String ACCOUNT_SID = "AC6fa7a4508189dd4848b872646bb798ac";
    public static final String AUTH_TOKEN = "3a68fa1f343241016c4be7ade77e0675";

    public AjouterEventFXML() {
    }

    public void envoyerMessage(String nameevent) {
        Twilio.init("AC6fa7a4508189dd4848b872646bb798ac", "3a68fa1f343241016c4be7ade77e0675");
        String contenuMessage = "event " + nameevent + " added succesfuly.";
        Message message = (Message)Message.creator(new PhoneNumber("+21626585443"), new PhoneNumber("+16812011937"), contenuMessage).create();
        System.out.println(message.getSid());
    }

    @FXML
    void AjouterEvent(ActionEvent event) throws SQLException {
        String nameevent = this.tfnameevent.getText();
        String descriptionevent = this.tfdescriptionevent.getText();
        LocalDate localDateStart = (LocalDate)this.tfdatestartevent.getValue();
        LocalDate localDateEnd = (LocalDate)this.tfdateendevent.getValue();
        if (!nameevent.isEmpty() && !descriptionevent.isEmpty() && localDateStart != null && localDateEnd != null && !this.tflocationevent.getText().isEmpty() && !this.tfidorganiser.getText().isEmpty() && !this.tfnbattendees.getText().isEmpty() && !this.tfaffiche.getText().isEmpty() && this.countryComboBox.getValue() != null) {
            if (!nameevent.matches("[a-zA-Z]+")) {
                this.showErrorAlert("Error", "Name must contain only letters.");
            } else if (!descriptionevent.matches("[a-zA-Z]+")) {
                this.showErrorAlert("Error", "Description must contain only letters.");
            } else if (!this.tflocationevent.getText().matches("[a-zA-Z]+")) {
                this.showErrorAlert("Error", "Location must contain only letters.");
            } else if (!this.tfidorganiser.getText().matches("\\d+")) {
                this.showErrorAlert("Error", "Organizer ID must contain only numbers.");
            } else if (!this.tfnbattendees.getText().matches("\\d+")) {
                this.showErrorAlert("Error", "Number of attendees must contain only numbers.");
            } else {
                try {
                    Date utilDateStart = Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date utilDateEnd = Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    java.sql.Date dateStart = new java.sql.Date(utilDateStart.getTime());
                    java.sql.Date dateEnd = new java.sql.Date(utilDateEnd.getTime());
                    int idorga = Integer.parseInt(this.tfidorganiser.getText());
                    int nbatendees = Integer.parseInt(this.tfnbattendees.getText());
                    int country = (Integer)this.countryComboBox.getValue();
                    Event eventToAdded = new Event(nameevent, descriptionevent, dateStart, dateEnd, this.tflocationevent.getText(), idorga, nbatendees, this.tfaffiche.getText(), country);
                    ServiceEvent sp = new ServiceEvent();
                    this.envoyerMessage(this.tfnameevent.getText());
                    sp.insertOne(eventToAdded);
                    Alert alert = new Alert(AlertType.INFORMATION, "The event was added successfully.", new ButtonType[0]);
                    alert.show();
                } catch (NumberFormatException var16) {
                    this.showErrorAlert("Error", "Invalid input for organizer ID, attendees number, or country ID.");
                }

            }
        } else {
            this.showErrorAlert("Error", "All fields are required.");
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    @FXML
    void initialize() {
        assert this.tfnameevent != null : "fx:id=\"tfnameevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        assert this.tfdescriptionevent != null : "fx:id=\"tfdescriptionevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        assert this.tfdatestartevent != null : "fx:id=\"tfdatestartevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        assert this.tfdateendevent != null : "fx:id=\"tfdateendevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        assert this.tflocationevent != null : "fx:id=\"tflocationevent\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        assert this.tfidorganiser != null : "fx:id=\"tfidorganiser\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        assert this.tfnbattendees != null : "fx:id=\"tfnbattendees\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        assert this.tfaffiche != null : "fx:id=\"tfaffiche\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        assert this.countryComboBox != null : "fx:id=\"countryComboBox\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

        ServiceEvent serviceEvent = new ServiceEvent();

        try {
            List<Integer> countryIds = ServiceEvent.getAllCountryIds();
            this.countryComboBox.getItems().addAll(countryIds);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    void goto_dashboard(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Event/ShowAll.fxml");
    }

    @FXML
    void goto_show_for_user(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Event/Show_data_for_user.fxml");
    }

    public void upload_img(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload your profile picture");
        File selectedFile = fileChooser.showOpenDialog((Window)null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName().toLowerCase();
            if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
                System.out.println("Invalid file format. Please select a PNG or JPG file.");
            } else {
                this.tfaffiche.setText(selectedFile.getPath());
            }
        } else {
            System.out.println("No file selected");
        }

    }
    @FXML
    void testrassil(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Event/ShowAll.fxml");
    }

}
