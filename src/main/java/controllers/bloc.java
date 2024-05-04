package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Event;
import servises.ServiceEvent;

import java.io.IOException;
public class bloc {


    @FXML
    private Text descriptionevent;

    @FXML
    private VBox eventBox;

    @FXML
    private Button event_readmore;

    @FXML
    private Label nameevent;
    @FXML
    private ImageView imagee;

    private int idevent;  // Variable to store the thread ID


    @FXML
    private Button backbtn;



    @FXML
    void initialize() {
    //    event_readmore.setOnAction(event -> handleReadMore());
    }

    public void setData(String title, String content, int id, String afficheUrl) {
        nameevent.setText(title);
        descriptionevent.setText(content);
        this.idevent = id;
        Image image = new Image(afficheUrl);
        imagee.setImage(image);

    }




    @FXML
    void handleReadMore() {
        try {
            // Retrieve the news by id
            ServiceEvent ServiceEvent = new ServiceEvent();
            Event event = ServiceEvent.getNewsById(idevent);


            // Load the Fullnews.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alldata.fxml"));
            Parent fullNewsRoot = loader.load();

            // Access the FullNews and set the news content
            Alldata AlldataController = loader.getController();
            AlldataController.setFullContent(event);
           // AlldataController.initializeWithData();

            // Access the current scene
            Scene currentScene = event_readmore.getScene();

            // Replace the content of the root node with the FullNews.fxml content
            ((Pane) currentScene.getRoot()).getChildren().setAll(fullNewsRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
