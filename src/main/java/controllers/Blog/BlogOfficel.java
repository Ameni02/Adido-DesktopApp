package controllers.Blog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import models.Blog.Blog;
import models.Blog.Image;
import services.Blog.ServiceBlog;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BlogOfficel {


    @FXML
    private GridPane grid;


    @FXML
    void initialize() throws SQLException {
        // Fetch the list of approved postsm
        ServiceBlog serviceBlog = new ServiceBlog();
        List<Blog> approvedBlogList = serviceBlog.selectAllApproved();

        // Keep track of the position in the grid
        int column = 0;
        int row = 0;

        for (Blog blog : approvedBlogList) {
            try {
                // Load the itemBlog FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/Blog/itemBlog.fxml"));
                Parent card = loader.load();
                ItemPost itemPost = loader.getController();

                // Get the list of images for the post
                List<Image> images = serviceBlog.getImagesByProductId(blog.getIdblog());
                String imagePath = null;

                // Check if there are images available for the post
                if (!images.isEmpty()) {
                    // Assuming you want to use the first image in the list as the post image
                    imagePath = images.get(0).getName_img();
                }

                // Set the data for the itemBlog
                itemPost.setData(blog.getTitleBlog(), blog.getContentBlog(), blog.getCountryBlog(), imagePath);

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
    void addpost(ActionEvent actionEvent) throws IOException {
        FxMain.loadFXML("/Event/Blog/AjouterBlogFXML.fxml");
    }

    @FXML
    void showDashbordBlog(ActionEvent actionEvent) throws IOException {
        FxMain.loadFXML("/Event/Blog/ShowAllBlog.fxml");
    }

    @FXML
    void showEvent(ActionEvent actionEvent) throws IOException {
        FxMain.loadFXML("/Event/Show_data_for_user.fxml");
    }



}