package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import models.Ecommerce.Panier;
import services.ServicePanier;

import java.io.IOException;
import java.sql.SQLException;

public class ItemProduct extends VBox {
    @FXML
    private ImageView imageLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    public ItemProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemProduct.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(String nomproduct, Float price, String imagePath) {
        // Set the name and price of the product
        nameLabel.setText(nomproduct);
        priceLabel.setText(String.valueOf(price));

        // Load the image from the provided path and set it to the ImageView
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Check if the image path starts with "file:" or a valid URL format
                if (!imagePath.startsWith("file:") && !imagePath.startsWith("http:") && !imagePath.startsWith("https:")) {
                    imagePath = "file:" + imagePath;
                }
                Image image = new Image(imagePath);
                imageLabel.setImage(image);
            } catch (IllegalArgumentException e) {
                // Handle cases where the image path is invalid
                System.err.println("Invalid image path provided: " + imagePath);
            }
        } else {
            // Handle the case where imagePath is null or empty
            System.out.println("Invalid image path provided.");
        }
    }

    @FXML
    public void addtocart(ActionEvent actionEvent) throws SQLException {
        ServicePanier sp = new ServicePanier();
        Panier panier = new Panier(2, sp.retrieveOneProduct(nameLabel.getText()), 1, Math.round(Float.parseFloat(priceLabel.getText())), nameLabel.getText());
        int quantity = sp.getQuantity(panier.getProductName());
        int stock = sp.getStock(panier.getProductName());
        if (stock < quantity + 1) {
            // stock is less than the number of clicks, so show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Insufficient Stock");
            alert.setHeaderText(null);
            alert.setContentText("Insufficient Stock");
            alert.showAndWait();
        } else {
            if (quantity == 0) {
                // item does not exist in the cart, so add it
                sp.addToCart(panier);
            } else {
                // item already exists in the cart, so update the quantity
                panier.setQuantity(quantity + 1);
                sp.updateCartItem(panier);
            }
        }
    }
}