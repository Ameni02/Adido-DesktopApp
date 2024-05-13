package controllers.Offer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import models.Offer.Offer;
import services.Offer.ServiceOffer;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OfferDashboardFXML {
    @FXML
    private TableColumn<Offer, String> id_post;

    @FXML
    private TableColumn<Offer, String> id_content;

    @FXML
    private TableColumn<Offer, String> id_country;

    @FXML
    private TableColumn<Offer, String> datedebut;

    @FXML
    private TableColumn<Offer, String> datefin;

    @FXML
    private TableView<Offer> OfferList;

    @FXML
    private TextField RechercheOffer;
    private static final Logger LOGGER = Logger.getLogger(OfferDashboardFXML.class.getName());


    @FXML
    void recherchebtn(ActionEvent event) throws IOException, SQLException {
        String searchTerm = RechercheOffer.getText();
        if (!searchTerm.isEmpty()) {
            try {
                ServiceOffer serviceOffer = new ServiceOffer();
                ObservableList<Offer> searchResult = FXCollections.observableList(serviceOffer.search(searchTerm));
                OfferList.setItems(searchResult);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                initialize();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void initialize() throws SQLException {
        LOGGER.log(Level.INFO, "Initializing Offer Dashboard...");

        ServiceOffer serviceOffer = new ServiceOffer();
        ObservableList<Offer> list = FXCollections.observableList(serviceOffer.selectAll());

        // Add logging to check if data is retrieved successfully
        LOGGER.log(Level.INFO, "Retrieved {0} offers from the database.", list.size());

        id_post.setCellValueFactory(new PropertyValueFactory<>("titleOffer"));
        id_content.setCellValueFactory(new PropertyValueFactory<>("descriptionOffer"));
        id_country.setCellValueFactory(new PropertyValueFactory<>("countryOffer"));
        datedebut.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        datefin.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        OfferList.setItems(list);

        TableColumn<Offer, Void> actionButtonColumn = new TableColumn<>("Actions");
        actionButtonColumn.setCellFactory(param -> new TableCell<>() {

            private final Button deleteButton = new Button("Delete Offer");
            private final Button updateButton = new Button("Update Offer");

            {
                deleteButton.setOnAction(event -> {
                    Offer offer = getTableView().getItems().get(getIndex());
                    try {
                        serviceOffer.deleteOne(offer);
                        OfferList.getItems().remove(offer);
                        LOGGER.log(Level.INFO, "Offer deleted: {0}", offer);
                    } catch (SQLException e) {
                        if (e instanceof SQLIntegrityConstraintViolationException) {
                            LOGGER.log(Level.WARNING, "Cannot delete the offer due to foreign key constraint.");
                        } else {
                            LOGGER.log(Level.SEVERE, "Error deleting offer: {0}", e.getMessage());
                        }
                    }
                });

                updateButton.setOnAction(event -> {
                    // Implement update functionality here
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

        OfferList.getColumns().add(actionButtonColumn);

        LOGGER.log(Level.INFO, "Offer Dashboard initialized successfully.");
    }
}
