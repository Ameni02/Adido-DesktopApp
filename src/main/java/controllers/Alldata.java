package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.Event;
import servises.ServiceEvent;
import test.FxMain;

import javax.swing.text.html.ImageView;
import java.awt.event.MouseEvent;
import java.io.IOException;

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





    @FXML
    void handleBackButton(ActionEvent event) {
        try {
            FxMain.loadFXML("/show_data_for_user.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public void setFullContent(Event event) {
        this.event = event;
        locationnnnn.setText(event.getLocationevent());
        NewsLabelID.setText(String.valueOf(event.getIdevent()));
        fullPostContent.setText(event.getNameevent());
        fullPostLabel.setText(event.getDescriptionevent());
        dateend.setText(String.valueOf(event.getDatestartevent()));
        fullPostDate.setText(String.valueOf(event.getDateendevent()));


    }




    @FXML
    void initialize() {


    }


    @FXML
    void AjouterEvent(MouseEvent event)throws IOException {

        FxMain.loadFXML("/AjouterEvent.fxml");


    }

    public void AjouterEvent(javafx.scene.input.MouseEvent mouseEvent)throws IOException {

        FxMain.loadFXML("/AjouterEvent.fxml");
    }
}