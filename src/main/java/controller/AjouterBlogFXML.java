package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.Blog;
import models.Image;
import services.ServiceBlog;
import test.FxMain;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterBlogFXML {


    @FXML
    private TextField tfcategorieproduct;

    @FXML
    private TextField tfnomproduct;


    @FXML
    private TextField tfstockproduct;

    @FXML
    private TextField picture_input;



    @FXML
    void ajouterProduct(ActionEvent event) {
        try {
            String titleBlog = tfnomproduct.getText();
            String contentBlog = tfcategorieproduct.getText();
            String countryBlog = tfstockproduct.getText();

            if (titleBlog.isEmpty() || contentBlog.isEmpty() || countryBlog.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "All fields and image path are required.").show();
                return;
            }
            if (titleBlog.length() > 15) {
                new Alert(Alert.AlertType.ERROR, "Title should not exceed 15 characters.").show();
                return;
            }

            if (countryBlog.length() > 20) {
                new Alert(Alert.AlertType.ERROR, "Country should not exceed 20 characters.").show();
                return;
            }

            if (!titleBlog.matches("[a-zA-Z\\s]+")) {
                new Alert(Alert.AlertType.ERROR, "Title should only contain letters and spaces.").show();
                return;
            }

            if (!contentBlog.matches("[a-zA-Z\\s]+")) {
                new Alert(Alert.AlertType.ERROR, "Content should only contain letters and spaces.").show();
                return;
            }

            if (!countryBlog.matches("[a-zA-Z\\s]+")) {
                new Alert(Alert.AlertType.ERROR, "Country should only contain letters and spaces.").show();
                return;
            }

            Blog newBlog = new Blog(titleBlog, contentBlog, countryBlog);
            ServiceBlog serviceProduct = new ServiceBlog();
            serviceProduct.insertOne(newBlog);
            int idblog = newBlog.getIdblog();

            // Ajout de l'image associée au blog (code non modifié ici)

            new Alert(Alert.AlertType.INFORMATION, "Post and image added successfully.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error adding post or image: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    @FXML
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
    public void initialize() throws SQLException {
        ServiceBlog serviceProduct = new ServiceBlog();
        // List<Integer> countryIds = serviceProduct.getAllCountryIds();
        // countrycombo.getItems().addAll(countryIds);
    }

    public void main() {
    }
    @FXML
 void AfficheComment(ActionEvent actionEvent)throws IOException {
        FxMain.loadFXML("/DashComment.fxml");
    }
}