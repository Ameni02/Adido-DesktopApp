package controllers.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.Blog.Blog;
import models.Blog.Image;
import services.Blog.ServiceBlog;
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
    void ajouterBlog(ActionEvent event) {
        try {
            // Récupérez l'ID de l'utilisateur connecté depuis votre système d'authentification
            int idUser = getUserIdFromParameter(); // Remplacez cette ligne par votre méthode pour obtenir l'ID utilisateur

            // Vérifiez que l'ID utilisateur est valide
            if (idUser <= 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid user ID.").show();
                return;
            }

            // Vérifiez les champs du blog
            String titleBlog = tfnomproduct.getText();
            String contentBlog = tfcategorieproduct.getText();
            String countryBlog = tfstockproduct.getText();

            if (titleBlog.isEmpty() || contentBlog.isEmpty() || countryBlog.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "All fields and image path are required.").show();
                return;
            }

            if (titleBlog.length() > 15 || countryBlog.length() > 20 ||
                    !titleBlog.matches("[a-zA-Z\\s]+") || !contentBlog.matches("[a-zA-Z\\s]+") ||
                    !countryBlog.matches("[a-zA-Z\\s]+")) {
                new Alert(Alert.AlertType.ERROR, "Invalid input data.").show();
                return;
            }

            // Créez un nouveau blog et insérez-le dans la base de données
            Blog newBlog = new Blog(titleBlog, contentBlog, countryBlog);
            ServiceBlog serviceProduct = new ServiceBlog();
            serviceProduct.insertOne(newBlog, idUser); // Utilisez l'ID utilisateur correct

            // Obtenez l'ID du blog inséré
            int idblog = newBlog.getIdblog();

            // Vérifiez que l'image est valide et insérez-la dans la base de données
            String imagePath = picture_input.getText();
            if (imagePath == null || imagePath.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Error downloading image.").show();
                return;
            }

            Image imageblog = new Image();
            imageblog.setName_img(imagePath);
            imageblog.setIdblog(idblog); // Associez l'image au blog

            serviceProduct.insertImageProduit(imageblog);

            // Affichez un message de succès
            new Alert(Alert.AlertType.INFORMATION, "Your post is treated by the Admin, you will receive an email.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error adding post or image: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    private int getUserIdFromParameter() {
        // Implémentez la logique pour récupérer l'ID utilisateur à partir d'un paramètre
        // Par exemple, si vous appelez cette méthode avec un paramètre, retournez cet ID
        // Sinon, retournez un ID par défaut ou lancez une exception si l'ID n'est pas disponible
        return 1; // ID utilisateur par défaut (remplacez-le par la logique appropriée)
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
        FxMain.loadFXML("/Event/Blog/BlogOfficell.fxml");
    }

    @FXML
    public void initialize() throws SQLException {
        ServiceBlog serviceProduct = new ServiceBlog();
        // List<Integer> countryIds = serviceProduct.getAllCountryIds();
        // countrycombo.getItems().addAll(countryIds);
    }

    public void main() {
    }

}

