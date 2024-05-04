
package controllers.Event;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import models.Event.Event;
import services.Event.ServiceEvent;
import test.FxMain;

public class show_for_user {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox eventBox;

    public show_for_user() {
    }

    @FXML
    void initialize() throws SQLException {
        ServiceEvent serviceEvent = new ServiceEvent();
        List<Event> eventList = ServiceEvent.selectAll();
        this.eventBox.getChildren().clear();
        Iterator var3 = eventList.iterator();

        while(var3.hasNext()) {
            Event event = (Event)var3.next();

            try {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Event/Showone.fxml"));
                Parent card = (Parent)loader.load();
                bloc bloc = (bloc)loader.getController();
                bloc.setData(event.getNameevent(), event.getDescriptionevent(), event.getIdevent(), event.getAffiche());
                this.eventBox.getChildren().add(card);
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        }

    }

    @FXML
    void Ajoutereventttt(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Event/AjouterEvent.fxml");
    }
}
