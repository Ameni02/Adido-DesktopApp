package controllers.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Blog.Comments;
import services.Blog.ServiceComment;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
public class updateComment {

    @FXML
    private TextField UpdateComment;
    @FXML
    public TextField updated_cntent;
    @FXML
  public  Label labelCOnent;

    private ComboBox<Integer> countrycombo;
    @FXML
    void back_to_list(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Blog/ShowAllBlog.fxml");
    }
    private Comments comments;
    ServiceComment Servicecomment = new ServiceComment();
    void retrievedata(Comments comments) {
        this.comments= comments;
        if (comments != null) {
            String contentcomment = comments.getContentcomment();
            updated_cntent.setText(comments.getContentcomment());

        }
    }
    @FXML
    void update_data(ActionEvent event) throws IOException, SQLException {

        String commentcontent = updated_cntent.getText();


        Comments commenttoupdate = new Comments(commentcontent);
        commenttoupdate.setIdcomment(comments.getIdcomment());






        ServiceComment serviceComment = new ServiceComment();
        serviceComment.updateOneComment(commenttoupdate);
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.setContentText("The product was updated successfully");
        alert.show();

    }
    @FXML
    void initialize() {
        // Listener for title text field
        updated_cntent.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                labelCOnent.setText(""); // Clear the label when the text field is modified
            }
        });




    }


}


