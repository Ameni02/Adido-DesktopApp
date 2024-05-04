package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import models.Comments;
import services.ServiceBlog;
import services.ServiceComment;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterCommentFXML {
    @FXML
    public TextField Ajcomment;


    @FXML
    private ChoiceBox<Integer> choixx_id; // Update ChoiceBox type to Integer

    @FXML
    void ajouterCOMMENT(ActionEvent event) {
        try {
            int idblog = choixx_id.getValue(); // Get the selected idblog directly
            String commentcontent = Ajcomment.getText();

            System.out.println("Comment content: " + commentcontent);

            if (commentcontent.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Comment content is required.").show();
                return;
            }

            // Create the Comments object and insert comment
            Comments newComment = new Comments(idblog, commentcontent);
            ServiceComment servicecomment = new ServiceComment();
            servicecomment.insertOneComment(newComment,1);

            new Alert(Alert.AlertType.INFORMATION, "Comment added successfully.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error adding comment: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void AfficheComment(ActionEvent event) throws IOException {
        FxMain.loadFXML("/DashComment.fxml");
    }

    @FXML
    public void initialize() throws SQLException {
        // Populate the ChoiceBox with idblog values from the database
        ServiceBlog serviceBlog = new ServiceBlog();
        List<Integer> idBlogList = serviceBlog.getAllIdBlogs(); // Assuming you have a method to retrieve idblog values
        choixx_id.getItems().addAll(idBlogList);
    }

    public void main() {
    }


}
