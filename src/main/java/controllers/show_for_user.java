package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import model.Event;
import servises.ServiceEvent;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class show_for_user {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox eventBox;



    @FXML
    void initialize() throws SQLException {

        ServiceEvent serviceEvent = new ServiceEvent();
        List<Event> eventList = serviceEvent.selectAll();

        // Clear the eventBox to avoid duplicates
        eventBox.getChildren().clear();

        for (Event event : eventList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Showone.fxml"));
                Parent card = loader.load();
                bloc bloc = loader.getController();
                bloc.setData(event.getNameevent(), event.getDescriptionevent(), event.getIdevent(), event.getAffiche());

                // Add the card to the VBox
                eventBox.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void Ajoutereventttt(ActionEvent event) throws IOException {
        FxMain.loadFXML("/AjouterEvent.fxml");
    }

}
