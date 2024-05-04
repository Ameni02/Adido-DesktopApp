package controllers.product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import models.Image;
import models.product;
import services.Serviceproduct;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class ShopProduit  {

    public BorderPane userSidebar1;
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
    @FXML
    private TextField text;
    @FXML
    public ComboBox languageComboBox;
    @FXML
    public Button cartButton;



    // private MyListener myListener;
    private final Serviceproduct sp = new Serviceproduct();

    @FXML
    void initialize() throws SQLException {
        //String textDesc = productDesc.getText();

        // Fetch the list of products
        Serviceproduct serviceProduct = new Serviceproduct();
        List<product> productList = serviceProduct.selectAll();

        // Keep track of the position in the grid
        int column = 0;
        int row = 0;

        for (product product : productList) {
            if (product.getApproved() == 1) {
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
                    itemProduct.setData(product.getNomproduct(), product.getPrixproduct(), imagePath, product.getPromotionproduct());

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



    /*public String getTextDesc() {
        return textDesc;
    }
*/

    
    public void setTranslatedContent(String textDesc, String language) {

        // Fetch translated title and content based on the selected language
        String translatedProductDescResponse = sp.translate("en", language, text.getText());

        // Extract the translated text from the JSON response
        String translatedDesc = Serviceproduct.extractTranslatedText(translatedProductDescResponse);

        // Check if translation is successful, otherwise use the original text
        if (translatedDesc != null) {

            text.setText(translatedDesc);
        }else {
            System.out.println("ERREUR TRADUCTION");
        }

    }


    public void translateText(ActionEvent actionEvent) throws SQLException {


        String selectedLanguage = (String) languageComboBox.getValue();

        // Implement translation logic based on the selected language
        // For simplicity, just add the translated text directly
        System.out.println(selectedLanguage);
        switch (selectedLanguage) {
            case "English":
               // initialize();
                setTranslatedContent(String.valueOf(text), "en");
                break;
            case "French":
                setTranslatedContent(String.valueOf(text), "fr");
                break;
            case "Arabic":
                setTranslatedContent(String.valueOf(text), "ar");
                break;
            default:
                System.out.println("hhhhhhh");

                // initialize();
                break;
        }
    }

    @FXML
    public void viewCart(ActionEvent actionEvent) {

        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/affichercart.fxml")));
            cartButton.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}