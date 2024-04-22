package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Event;
import servises.ServiceEvent;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ShowAll {

    @FXML
    private TableView<Event> eventListe;

    @FXML
    private TableColumn<?, ?> idevent;

    @FXML
    private TableColumn<?, ?> nameevent;

    @FXML
    private TableColumn<?, ?> tfaffiche;

    @FXML
    private TableColumn<?, ?> tfdateendevent;

    @FXML
    private TableColumn<?, ?> tfdatestartevent;

    @FXML
    private TableColumn<?, ?> tfdescriptionevent;

    @FXML
    private TableColumn<?, ?> tfidcountry;

    @FXML
    private TableColumn<?, ?> tfidorganiser;

    @FXML
    private TableColumn<?, ?> tflocationevent;

    @FXML
    private TableColumn<?, ?> tfnbattendees;


    @FXML
    void ajouterEvent(ActionEvent event) throws IOException {
        FxMain.loadFXML("/AjouterEvent.fxml");

    }
    ServiceEvent ServicEvent = new ServiceEvent();

    @FXML
    void initialize() throws SQLException {
        ObservableList<Event> list = FXCollections.observableList(ServiceEvent.selectAll());

        //id_product.setCellValueFactory(new PropertyValueFactory<>("id"));
        idevent.setCellValueFactory(new PropertyValueFactory<>("idevent"));
        nameevent.setCellValueFactory(new PropertyValueFactory<>("nameevent"));
        tfdescriptionevent.setCellValueFactory(new PropertyValueFactory<>("descriptionevent"));
        tfdatestartevent.setCellValueFactory(new PropertyValueFactory<>("datestartevent"));
        tfdateendevent.setCellValueFactory(new PropertyValueFactory<>("dateendevent"));
        tflocationevent.setCellValueFactory(new PropertyValueFactory<>("locationevent"));
        tfidorganiser.setCellValueFactory(new PropertyValueFactory<>("idorganiser"));
        tfnbattendees.setCellValueFactory(new PropertyValueFactory<>("nbattendees"));
        tfaffiche.setCellValueFactory(new PropertyValueFactory<>("affiche"));
        tfidcountry.setCellValueFactory(new PropertyValueFactory<>("idcountry"));
        eventListe.setItems(list);


        TableColumn<Event, Void> actionButtonColumn = new TableColumn<>("Actions");
        actionButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.setStyle("-fx-background-color: #4766EF; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 10px; " +
                        "-fx-padding: 5px 10px; " +
                        "-fx-border-radius: 50px; " +
                        "-fx-background-radius: 50px;");
                deleteButton.setOnAction(event -> {
                    Event event1 = getTableView().getItems().get(getIndex());
                    try {
                        ServiceEvent.deleteOne(event1);
                        eventListe.getItems().remove(event1);
                    } catch (SQLException e) {
                        System.err.println("Impossible de supprimer le event en raison d'une contrainte de clé étrangère : " + e.getMessage());
                    }
                });


                updateButton.setStyle("-fx-background-color: #4766EF; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 10px; " +
                        "-fx-padding: 5px 10px; " +
                        "-fx-border-radius: 50px; " +
                        "-fx-background-radius: 50px;");
                updateButton.setOnAction(event -> {
                    Event event1 = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = FxMain.loadFXML("/updateEvent.fxml");
                        updateEvent updateController = loader.getController();
                        updateController.retrievedata(event1);
                        System.out.println("Selected");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(deleteButton, updateButton);
                    setGraphic(buttons);
                }
            }
        });

        eventListe.getColumns().add(actionButtonColumn);
    }


}
