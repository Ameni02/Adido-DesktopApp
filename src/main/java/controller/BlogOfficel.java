package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import models.Blog;
import models.Image;
import services.ServiceBlog;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BlogOfficel {


    @FXML
    private GridPane grid;






    @FXML

    void initialize() throws SQLException {
        // Fetch the list of products
        ServiceBlog serviceProduct = new ServiceBlog();

        // Appelez selectAll() à partir de l'instance créée
        List<Blog> BlogList = serviceProduct.selectAll();
        // Keep track of the position in the grid
        int column = 0;
        int row = 0;

        for (Blog blog :BlogList) {
            try {
                // Load the itemProduct FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemBlog.fxml"));
                Parent card = loader.load();
                ItemPost itemPost = loader.getController();

                // Get the list of images for the product
                List<Image> images = serviceProduct.getImagesByProductId(blog.getIdblog());
                String imagePath = null;

                // Check if there are images available for the product
                if (!images.isEmpty()) {
                    // Assuming you want to use the first image in the list as the product image
                    imagePath = images.get(0).getName_img();
                }

                // Set the data for the itemProduct
                itemPost.setData(blog.getTitleBlog(), blog.getContentBlog(), blog.getCountryBlog(),  imagePath);

                itemPost.setSelectedBlog(blog);

                // Add the card to the grid
                grid.add(card, column, row);

                // Update column and row values
                column++;
                if (column == 1) { // Assuming 3 columns per row
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void addpost(ActionEvent actionEvent)throws IOException {
        FxMain.loadFXML("/AjouterBlogFXML.fxml");
    }
    @FXML
    void showDashbord(ActionEvent actionEvent)throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }


}