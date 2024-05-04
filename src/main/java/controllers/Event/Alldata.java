

package controllers.Event;

import java.awt.event.MouseEvent;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javax.swing.text.html.ImageView;
import models.Event.Event;
import services.Event.ServiceEvent;
import test.FxMain;

public class Alldata {
    @FXML
    private Label NewsLabelID;
    @FXML
    private Button backbtn;
    @FXML
    private Text fullPostContent;
    @FXML
    private Label fullPostDate;
    @FXML
    private ComboBox<?> languageComboBox;
    @FXML
    private Label fullPostLabel;
    @FXML
    private TextArea comment_section;
    @FXML
    private ListView<HBox> comment_view;
    @FXML
    private Button like;
    @FXML
    private Label dateend;
    @FXML
    private ImageView imageeeee;
    @FXML
    private Button post_button;
    @FXML
    private Label locationnnnn;
    private Event event;
    private int Id_news;
    ServiceEvent ServiceEvent = new ServiceEvent();
    private final ServiceEvent ns = new ServiceEvent();

    public Alldata() {
    }

    @FXML
    void handleBackButton(ActionEvent event) {
        try {
            FxMain.loadFXML("/show_data_for_user.fxml");
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void setFullContent(Event event) {
        this.event = event;
        this.locationnnnn.setText(event.getLocationevent());
        this.NewsLabelID.setText(String.valueOf(event.getIdevent()));
        this.fullPostContent.setText(event.getNameevent());
        this.fullPostLabel.setText(event.getDescriptionevent());
        this.dateend.setText(String.valueOf(event.getDatestartevent()));
        this.fullPostDate.setText(String.valueOf(event.getDateendevent()));
    }

    @FXML
    void initialize() {
    }

    @FXML
    void AjouterEvent(MouseEvent event) throws IOException {
        FxMain.loadFXML("/Event/AjouterEvent.fxml");
    }

    public void AjouterEvent(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        FxMain.loadFXML("/Event/AjouterEvent.fxml");
    }
}
