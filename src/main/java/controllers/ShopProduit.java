package controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import models.Image;
import models.product;
import services.Serviceproduct;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShopProduit  {

    @FXML
    private ImageView ProductImage;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private Label ProductPriceLabel;

    @FXML
    private VBox chosenProductCart;
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrol;

    @FXML
    private HBox productBox;


    // private MyListener myListener;


    @FXML

    void initialize() throws SQLException {
        // Fetch the list of products
        Serviceproduct serviceProduct = new Serviceproduct();
        List<product> productList = serviceProduct.selectAll();

        // Keep track of the position in the grid
        int column = 0;
        int row = 0;

        for (product product : productList) {
            try {
                // Load the itemProduct FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemProduct.fxml"));
                Parent card = loader.load();
                ItemProduct itemProduct = loader.getController();

                // Get the list of images for the product
                List<Image> images = serviceProduct.getImagesByProductId(product.getId());
                String imagePath = null;

                // Check if there are images available for the product
                if (!images.isEmpty()) {
                    // Assuming you want to use the first image in the list as the product image
                    imagePath = images.get(0).getName_img();
                }

                // Set the data for the itemProduct
                itemProduct.setData(product.getNomproduct(), product.getPrixproduct(), imagePath);

                // Add the card to the grid
                grid.add(card, column, row);

                // Update column and row values
                column++;
                if (column == 3) { // Assuming 3 columns per row
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}