package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Ecommerce.Panier;
import services.ServiceCommande;
import services.ServicePanier;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Affichercart {

    @FXML
    private VBox cartItems;

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

    private  ServicePanier sp = new ServicePanier();

    @FXML
    void checkoutClicked(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Ajoutcommande.fxml")));
            checkoutButton.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void clearAllClicked(ActionEvent event) {

    }

    @FXML
    void goBackShoppingClicked(ActionEvent event) {
        try {
            // Load the "" FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShopProduit.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();

            // Set the scene on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading fails
        }
    }


    public void initialize() {
        try {
            // Call initialize method to populate cart items
            populateCartItems();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception if needed
        }
    }

    private void populateCartItems() throws SQLException {
        // Retrieve cart items from the database using the injected ServicePanier instance
        List<Panier> cartItemList = sp.selectAll("userId");

        // Iterate over cart items and add them to the UI
        for (Panier item : cartItemList) {
            HBox itemBox = new HBox();
            Label productNameLabel = new Label(item.getProductName());
            Label productQuantityLabel = new Label("Quantity: " + item.getQuantity());
            Label productPriceLabel = new Label("Price: $" + item.getPrixTotal());
            itemBox.getChildren().addAll(productNameLabel, productQuantityLabel, productPriceLabel);
            cartItems.getChildren().add(itemBox);
        }

        // Update total and taxes labels if needed
        // totalLabel.setText(...);
        // taxesLabel.setText(...);
    }

    public void setServicePanier(ServicePanier servicePanier) {
        this.sp = servicePanier;
    }

    public void removeItemClicked(ActionEvent actionEvent) {

    }
}


