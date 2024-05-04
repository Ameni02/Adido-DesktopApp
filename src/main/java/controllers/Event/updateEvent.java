

package controllers.Event;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import models.Event.Event;
import services.Event.ServiceEvent;
import test.FxMain;

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
    private Event events;
    ServiceEvent ServiceEvent = new ServiceEvent();

    public updateEvent() {
    }

    @FXML
    void back_to_list(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Event/ShowAll.fxml");
    }

    void retrievedata(Event event) {
        this.events = event;
        if (event != null) {
            String nameevent = event.getNameevent();
            this.tfnameeventupdate.setText(event.getNameevent());
            String discriptionevent = event.getDescriptionevent();
            this.tfdescriptioneventupdate.setText(event.getDescriptionevent());
            Date datestartevent = event.getDatestartevent();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(datestartevent);
            this.tfdatestarteventupdate.setValue(LocalDate.parse(dateString));
            Date dateendevent = event.getDateendevent();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String dateString1 = sdf1.format(dateendevent);
            this.tfdateendeventupdate.setValue(LocalDate.parse(dateString1));
            String locationevent = event.getLocationevent();
            this.tflocationeventupdate.setText(event.getLocationevent());
            String idorganiser = Integer.toString(event.getIdorganiser());
            this.tfidorganiserupdate.setText(idorganiser);
            String nbattendees = Integer.toString(event.getNbattendees());
            this.tfnbattendeesupdate.setText(nbattendees);
            String affiche = event.getAffiche();
            this.tfafficheupdate.setText(event.getAffiche());

            try {
                ServiceEvent var10000 = this.ServiceEvent;
                List<Integer> countryIds = services.Event.ServiceEvent.getAllCountryIds();
                this.tfidcountryupdate.getItems().addAll(countryIds);
                this.tfidcountryupdate.setValue(event.getIdcountry());
            } catch (SQLException var15) {
                var15.printStackTrace();
            }
        }

    }

    private void populateCountryComboBox() {
        try {
            ServiceEvent var10000 = this.ServiceEvent;
            List<Integer> countryIds = services.Event.ServiceEvent.getAllCountryIds();
            this.tfidcountryupdate.getItems().addAll(countryIds);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    @FXML
    void update_data(ActionEvent event) throws IOException, SQLException {
        String nameevent = this.tfnameeventupdate.getText().trim();
        String descriptionevent = this.tfdescriptioneventupdate.getText().trim();
        LocalDate localDateStart = (LocalDate)this.tfdatestarteventupdate.getValue();
        LocalDate localDateEnd = (LocalDate)this.tfdateendeventupdate.getValue();
        String locationevent = this.tflocationeventupdate.getText().trim();
        String idorganiser = this.tfidorganiserupdate.getText().trim();
        String nbattendees = this.tfnbattendeesupdate.getText().trim();
        String affiche = this.tfafficheupdate.getText().trim();
        int idcountry = (Integer)this.tfidcountryupdate.getValue();

        int idcountryy;
        try {
            new ServiceEvent();
            List<Integer> countryIds = services.Event.ServiceEvent.getAllCountryIds();
            this.tfidcountryupdate.getItems().addAll(countryIds);
            idcountryy = this.events.getIdcountry();
            if (countryIds.contains(idcountryy)) {
                this.tfidcountryupdate.setValue(idcountryy);
            } else {
                System.out.println("Le pays associé au event n'est pas dans la liste.");
            }
        } catch (SQLException var19) {
            var19.printStackTrace();
        }

        if (!nameevent.isEmpty() && nameevent.matches("[a-zA-Z]+")) {
            if (!descriptionevent.isEmpty() && descriptionevent.matches("[a-zA-Z]+")) {
                if (localDateStart != null && localDateEnd != null) {
                    if (!locationevent.isEmpty() && locationevent.matches("[a-zA-Z]+")) {
                        if (!idorganiser.isEmpty() && idorganiser.matches("\\d+")) {
                            if (!nbattendees.isEmpty() && nbattendees.matches("\\d+")) {
                                if (!affiche.isEmpty() && !affiche.matches("\\d+")) {
                                    int idorganise;
                                    int nbattendeess;
                                    try {
                                        idorganise = Integer.parseInt(idorganiser);
                                        nbattendeess = Integer.parseInt(nbattendees);
                                        idcountryy = Integer.parseInt(String.valueOf(idcountry));
                                    } catch (NumberFormatException var18) {
                                        this.showAlert("Invalid numeric input.");
                                        return;
                                    }

                                    Date datestartevent = Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                    Date dateendevent = Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                    Event eventToUpdate = new Event(nameevent, descriptionevent, datestartevent, dateendevent, locationevent, idorganise, nbattendeess, affiche, idcountryy);
                                    eventToUpdate.setIdevent(this.events.getIdevent());
                                    services.Event.ServiceEvent.updateOne(eventToUpdate);
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setContentText("The event was updated successfully");
                                    alert.show();
                                } else {
                                    this.showAlert("Affiche must not be empty and contain only letters.");
                                }
                            } else {
                                this.showAlert("Number of attendees must not be empty and contain only numbers.");
                            }
                        } else {
                            this.showAlert("Organiser ID must not be empty and contain only numbers.");
                        }
                    } else {
                        this.showAlert("Location must not be empty and contain only letters.");
                    }
                } else {
                    this.showAlert("Please select both start and end dates.");
                }
            } else {
                this.showAlert("Description event must not be empty and contain only letters.");
            }
        } else {
            this.showAlert("Name event must not be empty and contain only letters.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
