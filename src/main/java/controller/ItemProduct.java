package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import models.product;
import services.Serviceproduct;

import java.io.IOException;

public class ItemProduct extends VBox {
    @FXML
    private ImageView imageLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;
    @FXML
    private Label discountLabel;
    @FXML
    private Label originalPriceLabel;

    private product product;
//    public ItemProduct() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemProduct.fxml"));
//            loader.setRoot(this);
//            loader.setController(this);
//            loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void setData(String nomproduct, Float price, String imagePath ,int promotion) {
        // Set the name and price of the product

        nameLabel.setText(nomproduct);
        if (promotion > 0) {
            float discountedPrice = price * (100 - promotion) / 100;

            // Set the original price with a strike-through
            originalPriceLabel.setText(String.format("%.2f", price));
            // Set the discounted price
            priceLabel.setText(String.format("%.2f", discountedPrice));

            // Set the discount label to indicate promotion
            discountLabel.setText("Solde");
            discountLabel.setVisible(true);
        } else {
            originalPriceLabel.setText(String.valueOf(price));
            // Cacher l'étiquette de solde si la promotion est nulle ou négative
            discountLabel.setVisible(false);
            priceLabel.setVisible(false);
        }
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


}
