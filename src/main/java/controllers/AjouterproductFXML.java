package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.product;
import models.Image;
import services.Serviceproduct;
import test.FxMain;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

public class AjouterproductFXML {


    @FXML
    private Label nomerror211;

    @FXML
    private Label nomerror2111;

    @FXML
    private Label nomerror21111;

    @FXML
    private Label nomerror211111;

    @FXML
    private Button backtolist;
    @FXML
    private TextField tfcategorieproduct;

    @FXML
    private TextField tfnomproduct;

    @FXML
    private TextField tfprixproduct;

    @FXML
    private TextField tfpromotionproduct;

    @FXML
    private TextField tfstockproduct;
    @FXML
    private Label categorieerror;

    @FXML
    private TextField picture_input;

    @FXML
    private Label nomerror;

    @FXML
    private Label nomerror21;
    @FXML
    private Label prixerror;

    @FXML
    private Label promotionerror;

    @FXML
    private Label stockerror;
    @FXML
    private ComboBox<Integer> countrycombo;
    @FXML

    void ajouterProduct(ActionEvent event) {
        // Retrieve values from text fields
        String categorieProduct = tfcategorieproduct.getText();
        String nomProduct = tfnomproduct.getText();
        String prixString = tfprixproduct.getText();
        String promotionString = tfpromotionproduct.getText();
        String stockString = tfstockproduct.getText();
        String imagePath = picture_input.getText();
        Integer selectedCountryId = countrycombo.getValue(); // Utilisez Integer pour le type de `selectedCountryId`

        // Validate input fields
        if (categorieProduct.isEmpty() || nomProduct.isEmpty() || prixString.isEmpty() || promotionString.isEmpty() || stockString.isEmpty() || imagePath.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "All fields and image path are required.").show();
            return;
        }

        // Vérifiez que `selectedCountryId` n'est pas null
        if (selectedCountryId == null) {
            new Alert(Alert.AlertType.ERROR, "You must select a country ID.").show();
            return;
        }

        // Vérification que `categorieProduct` et `nomProduct` ne contiennent pas de chiffres
        if (!categorieProduct.matches("[a-zA-Z\\s]+")) {
            nomerror211.setText("Category cannot contain numbers.");
            return;
        }
        if (!nomProduct.matches("[a-zA-Z\\s]+")) {
            nomerror21.setText("Name cannot contain numbers.");
            return;
        }

        try {
            // Parse price, promotion, and stock values
            float prixProduct = Float.parseFloat(prixString);
            int promotionProduct = Integer.parseInt(promotionString);
            int stockProduct = Integer.parseInt(stockString);

            // Validate numeric ranges
            if (prixProduct < 0) {
                nomerror21111.setText("Price cannot be negative.");
                return;
            }
            if (stockProduct < 0) {
                nomerror2111.setText("Stock cannot be negative.");
                return;
            }
            if (promotionProduct < 0 || promotionProduct > 90) {
                nomerror211111.setText("Promotion must be between 0 and 90.");
                return;
            }

            // Create a new product
            product newProduct = new product(categorieProduct, nomProduct, prixProduct, promotionProduct, stockProduct, selectedCountryId);

            // Create a service to manage the product
            Serviceproduct serviceProduct = new Serviceproduct();

            // Insert the product into the database
            serviceProduct.insertOne(newProduct);

            // Retrieve the ID of the newly inserted product
            int productId = newProduct.getId(); // Assume your product class provides this method

            // Create a new Image object for the image
            Image imageProduit = new Image();
            imageProduit.setName_img(imagePath);
            imageProduit.setId_product(productId); // Link the image to the product

            // Insert the image into the database
            serviceProduct.insertImageProduit(imageProduit);

            new Alert(Alert.AlertType.INFORMATION, "Product and image added successfully.").show();

        } catch (SQLException e) {
            // Provide detailed error messages
            new Alert(Alert.AlertType.ERROR, "Error adding product or image: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }
    public void upload_img(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload your profile picture");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName().toLowerCase();
            if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                picture_input.setText(selectedFile.getPath());
            } else {
                System.out.println("Invalid file format. Please select a PNG or JPG file.");
            }
        } else {
            System.out.println("No file selected");
        }
    }
    @FXML
    void backtolist(ActionEvent event) throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }
    @FXML
    public void initialize() {
        // Créez une instance de votre service de produits
        Serviceproduct serviceProduct = new Serviceproduct();

        try {
            // Obtenir tous les ID de pays
            List<Integer> countryIds = serviceProduct.getAllCountryIds();

            // Ajouter tous les ID de pays au ComboBox
            countrycombo.getItems().addAll(countryIds);
        } catch (SQLException e) {
            // Gérez les exceptions en cas d'erreur de base de données
            e.printStackTrace();
        }
    }

}
