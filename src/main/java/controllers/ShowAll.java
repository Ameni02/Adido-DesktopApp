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
import models.product;
import services.Serviceproduct;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;

public class ShowAll {

    @FXML
    private TableColumn<?, ?> action;

    @FXML
    private TableColumn<?, ?> id_categorieproduct;

    @FXML
    private TableColumn<?, ?> id_nomproduct;

    @FXML
    private TableColumn<?, ?> id_prixproduct;

    @FXML
    private TableColumn<?, ?> id_product;

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
        action.setCellValueFactory(new PropertyValueFactory<>("approved"));
        productListe.setItems(list);


        TableColumn<product, Void> actionButtonColumn = new TableColumn<>("Actions");
        actionButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

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

                   FXMLLoader loader = null;
                   loader = FxMain.loadFXML("/updateProduct.fxml");
                   updateProduct updateController = loader.getController();
                        updateController.retrievedata(product);
                        System.out.println("Selected");


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

        productListe.getColumns().add(actionButtonColumn);
    }
}
