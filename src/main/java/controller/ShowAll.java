package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import models.product;
import services.Serviceproduct;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;

public class ShowAll {

    @FXML
    private TableColumn<?, ?> country;

    @FXML
    private TableColumn<?, ?> id_categorieproduct;

    @FXML
    private TableColumn<?, ?> id_nomproduct;

    @FXML
    private TableColumn<?, ?> id_prixproduct;

    @FXML
    private TableColumn<?, ?> id_product;
    @FXML
    private TableColumn<?, ?> tfAprouved;

    @FXML
    private TableColumn<?, ?> id_promotionproduct;

    @FXML
    private TableColumn<?, ?> id_stockproduct;


    @FXML
    private TableView<product> productListe;
    @FXML

    void ajouterProduct(ActionEvent event) throws IOException {
        FxMain.loadFXML("/shopProduit.fxml");

    }

    Serviceproduct Serviceroduct = new Serviceproduct();

    @FXML
    void initialize() throws SQLException {
        ObservableList<product> list = FXCollections.observableList(Serviceroduct.selectAll());
        //id_product.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_categorieproduct.setCellValueFactory(new PropertyValueFactory<>("categorieproduct"));
        id_nomproduct.setCellValueFactory(new PropertyValueFactory<>("nomproduct"));
        id_prixproduct.setCellValueFactory(new PropertyValueFactory<>("prixproduct"));
        id_promotionproduct.setCellValueFactory(new PropertyValueFactory<>("promotionproduct"));
        id_stockproduct.setCellValueFactory(new PropertyValueFactory<>("stockproduct"));
        //country.setCellValueFactory(new PropertyValueFactory<>("idcountry"));
        tfAprouved.setCellValueFactory(new PropertyValueFactory<>("approved"));

        productListe.setItems(list);


        TableColumn<product, Void> actionButtonColumn = new TableColumn<>("Actions");
        actionButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");
            private final Button aprouve = new Button("Aprouve");

            {
                deleteButton.setOnAction(event -> {
                    product product = getTableView().getItems().get(getIndex());
                    try {
                        Serviceroduct.deleteOne(product);
                        productListe.getItems().remove(product);
                    } catch (SQLException e) {
                        // Gérez l'exception ici
                        System.err.println("Impossible de supprimer le produit en raison d'une contrainte de clé étrangère : " + e.getMessage());
                        // Afficher un message d'erreur à l'utilisateur
                        // Par exemple, en utilisant une alerte JavaFX
                        // Alert alert = new Alert(Alert.AlertType.ERROR);
                        // alert.setTitle("Erreur de suppression");
                        // alert.setHeaderText("Suppression impossible");
                        // alert.setContentText("Ce produit est référencé par une autre table et ne peut pas être supprimé.");
                        // alert.showAndWait();
                    }
                });

                updateButton.setOnAction(event -> {
                    product product = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = FxMain.loadFXML("/updateProduct.fxml");
                        updateProduct updateController = loader.getController();
                        updateController.retrievedata(product);
                        System.out.println("Selected");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                });
                aprouve.setOnAction(event -> {
                    product product = getTableView().getItems().get(getIndex());
                    product.setApproved(1); // Changer l'état approved à true

                    try {
                        Serviceproduct serviceProduct = new Serviceproduct();
                        serviceProduct.updateApprovedStatus(product); // Mettre à jour le produit dans la base de données
                        productListe.refresh(); // Rafraîchir la table pour refléter les modifications
                    } catch (SQLException e) {
                        // Gérer l'exception ici
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(deleteButton, updateButton,aprouve);
                    setGraphic(buttons);
                }
            }
        });

        productListe.getColumns().add(actionButtonColumn);
    }

}

