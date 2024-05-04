package controllers.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Blog.Blog;
import models.Blog.LikePost;
import services.Blog.ServiceLikes;

import java.io.IOException;
import java.sql.SQLException;


import java.net.URL;
import java.util.ResourceBundle;

public class ItemPost extends VBox implements Initializable {

    public Label nbLikes;
    private Blog selectedBlog;
    @FXML
    private ImageView jaime;
    @FXML
    private ImageView imageLabel;

    @FXML
    private Label nameLabel;
    @FXML
    private Label tutl;
    @FXML
    private Label contrylabel;

    public ItemPost() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemBlog.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setData(String titleBlog, String contentblog,String countryblog,String imagePath) {
        // Set the name and price of the product
        nameLabel.setText(titleBlog);
        tutl.setText(contentblog);
        contrylabel.setText(countryblog);

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
    void showPost(ActionEvent actionEvent) throws IOException {
        // Vérifiez si un blog est sélectionné
        if (selectedBlog != null) {
            // Chargez le FXML de DetailBlog.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Blog/DetailBlog.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur DetailBlog
            DetailBlog detailBlogController = loader.getController();

            // Initialisez les données du blog sélectionné dans DetailBlog
            detailBlogController.initData(selectedBlog);

            // Affichez la scène avec les détails du blog
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();
        } else {
            // Gérez le cas où aucun blog n'est sélectionné
            System.err.println("Aucun blog sélectionné.");
        }
    }

    // Méthode pour définir le blog sélectionné
    public void setSelectedBlog(Blog selectedBlog) {
        this.selectedBlog = selectedBlog;
    }


    @FXML
    public void LikeButtn(ActionEvent actionEvent) {
        if (selectedBlog != null) {
            int selectedBlogId = selectedBlog.getIdblog();
            // Ajoutez votre logique pour ajouter un like avec cet ID
            // Par exemple, vous pouvez appeler la méthode AjouterLike de ServiceLikes
            try {
                ServiceLikes serviceLikes = new ServiceLikes();
                serviceLikes.AjouterLike(new LikePost(selectedBlogId));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Mettez à jour le nombre de likes affiché
            updateLikeCount(selectedBlogId);
        } else {
            System.err.println("Aucun blog sélectionné pour ajouter un like.");
        }
    }

    private void updateLikeCount(int blogId) {
        try {
            ServiceLikes serviceLikes = new ServiceLikes();
            int likeCount = serviceLikes.getLikeCountByBlogId(blogId);
            nbLikes.setText(String.valueOf(likeCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Mettre à jour le nombre de likes lors de l'initialisation de l'interface
        if (selectedBlog != null) {
            int selectedBlogId = selectedBlog.getIdblog();
            updateLikeCount(selectedBlogId);
        }

        try {
            // Charger l'image depuis un fichier local ou une URL
            Image image = new Image("/image/like.png"); // Remplacez "path/to/your/image.jpg" par le chemin correct
            jaime.setImage(image); // Définir l'image dans l'ImageView
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }

        // Ajouter un gestionnaire d'événements pour l'image de like
        jaime.setOnMouseClicked(event -> {
            LikeButtn(new ActionEvent()); // Appeler la méthode LikeButtn lors du clic sur l'image de like
        });
       // Appeler la méthode LikeButtn lors du clic sur l'image de like


    }


    public void disllike(ActionEvent actionEvent) {
        if (selectedBlog != null) {
            int selectedBlogId = selectedBlog.getIdblog();
            ServiceLikes serviceLikes = new ServiceLikes();
            try {
                // Récupérer le dernier like ajouté pour ce blog
                LikePost lastLike = serviceLikes.getLastLikeByBlogId(selectedBlogId);
                if (lastLike != null) {
                    // Supprimer le dernier like ajouté
                    serviceLikes.deleteLikeByBlogId(selectedBlogId, lastLike.getIdlike());
                    // Mettre à jour le nombre de likes affiché
                    updateLikeCount(selectedBlogId);
                } else {
                    System.out.println("Aucun like à supprimer pour ce blog.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun blog sélectionné.");
        }
    }
}