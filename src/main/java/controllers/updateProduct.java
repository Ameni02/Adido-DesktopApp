package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.product;
import test.FxMain;
import services.Serviceproduct;
import java.io.IOException;
import java.sql.SQLException;

public class updateProduct {

    @FXML
    private Label mylabel11;

    @FXML
    private Label mylabel12;

    @FXML
    private Label mylabel13;

    @FXML
    private TextField updated_categorie;

    @FXML
    private TextField updated_nomproduct;

    @FXML
    private TextField updated_prixProduct;

    @FXML
    private TextField updated_promotionProduct;

    @FXML
    private TextField updated_stockproduct;
    @FXML
    private Label mylabelcate;

    @FXML
    private Label mylabelnom;

    @FXML
    private Label mylabelprix;

    @FXML
    private Label mylabelpromotion;

    @FXML
    private Label mylabelstock;
    @FXML
    private ComboBox<Integer> countrycombo;
    @FXML
    void back_to_list(ActionEvent event) throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }
    private product products;
    Serviceproduct Serviceproduct = new Serviceproduct();
    void retrievedata(product products) {
        this.products = products;
        if (products != null) {
            String categorie = products.getCategorieproduct();
            updated_categorie.setText(products.getCategorieproduct());
            String nomproduct = products.getNomproduct();
            updated_nomproduct.setText(products.getNomproduct());

            String prixString = Float.toString(products.getPrixproduct());
            updated_prixProduct.setText(prixString);


            String stockString = Integer.toString(products.getStockproduct());
            updated_stockproduct.setText(stockString);

            String promotionString = Integer.toString(products.getPromotionproduct());
            updated_promotionProduct.setText(promotionString);


        }
    }
    @FXML
    void update_data(ActionEvent event) throws IOException, SQLException {

        String categorie = updated_categorie.getText();
        String nom = updated_nomproduct.getText();
        String prixString = updated_prixProduct.getText();
        String promotionString = updated_promotionProduct.getText();
        String stockString = updated_stockproduct.getText();
        Float prix = Float.parseFloat(prixString);
        int promotion = Integer.parseInt(promotionString);
        int stock = Integer.parseInt(stockString);
        Integer CountryId = countrycombo.getValue();
        product productToUpdate = new product(categorie, nom, prix, promotion, stock,CountryId);
        productToUpdate.setId(products.getId());

        try {
        // Valider les entrées d'utilisateur
        if (categorie.isEmpty() || nom.isEmpty() || prixString.isEmpty() || promotionString.isEmpty() || stockString.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "All fields are required.").show();
            return;
        }
        if (!categorie.matches("[a-zA-Z\\s]+") ) {
            mylabelcate.setText("Category  cannot contain numbers.");

            return;
        }
        if ( !nom.matches("[a-zA-Z\\s]+")) {
            mylabelnom.setText("name  cannot contain numbers.");

            return;
        }






        if (prix < 0) {
                mylabelprix.setText("Price cannot be negative.");
                return;
            }
            if (stock < 0) {
                mylabelstock.setText("stock cannot be negative.");
                return;
            }
            if (promotion < 0 || promotion > 90) {
                mylabelpromotion.setText("solde must be between 0 and 90.");
                return;
            }
           /* new Alert(Alert.AlertType.ERROR, "Invalid number format in price, promotion, or stock.").show();
            return;
*/

        // Créer ou mettre à jour une instance de product



        } catch (Exception e) {
            throw new RuntimeException();
        }
        Serviceproduct.updateOne(productToUpdate);
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.setContentText("the product was updated succefully");
        alert.show();

    }
    @FXML
    void initialize() {
        // Listener for title text field
        updated_categorie.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                mylabelcate.setText(""); // Clear the label when the text field is modified
            }
        });

        // Listener for author text field
        updated_nomproduct.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                mylabelnom.setText(""); // Clear the label when the text field is modified
            }
        });

        // Listener for content text field
        updated_prixProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                mylabelprix.setText(""); // Clear the label when the text field is modified
            }
        });
        updated_promotionProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                mylabelpromotion.setText(""); // Clear the label when the text field is modified
            }
        });
        updated_stockproduct.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                mylabelstock.setText(""); // Clear the label when the text field is modified
            }
        });

    }


}
