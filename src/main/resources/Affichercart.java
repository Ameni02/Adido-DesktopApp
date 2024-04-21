package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Ecommerce.Panier;
import services.ServicePanier;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Affichercart {

    @FXML
    private TableView<Panier> cartItemsTable;

    @FXML
    private Button checkoutButton;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label productQuantityLabel;

    @FXML
    private Label taxesLabel;

    @FXML
    private Label totalLabel;

    private ServicePanier sp = new ServicePanier();

    @FXML
    void checkoutClicked(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Ajoutcommande.fxml")));
            checkoutButton.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clearAllClicked(ActionEvent event) {

    }

    @FXML
    void goBackShoppingClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShopProduit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        try {
            populateCartItems();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void populateCartItems() throws SQLException {
        TableColumn<Panier, String> productNameCol = new TableColumn<>("Product Name");
        productNameCol.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());

        TableColumn<Panier, String> productQuantityCol = new TableColumn<>("Quantity");
        productQuantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asString());

        TableColumn<Panier, String> productPriceCol = new TableColumn<>("Price");
        productPriceCol.setCellValueFactory(cellData -> cellData.getValue().prixTotalProperty().asString());

        cartItemsTable.getColumns().addAll(productNameCol, productQuantityCol, productPriceCol);

        List<Panier> cartItemList = sp.selectAll("userId");
        cartItemsTable.getItems().addAll(cartItemList);
    }

    public void setServicePanier(ServicePanier servicePanier) {
        this.sp = servicePanier;
    }

    public void removeItemClicked(ActionEvent actionEvent) {

    }
}
