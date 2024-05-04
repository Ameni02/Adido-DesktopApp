package controllers.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Blog.Blog;
import services.Blog.ServiceBlog;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;

public class updateBlog {

    public TextField updated_country;
    @FXML
    private Label mylabel11;

    @FXML
    private Label mylabel12;

    @FXML
    private Label mylabel13;

    @FXML
    private TextField updated_title;

    @FXML
    private TextField updated_cntent;


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
        FxMain.loadFXML("/Blog/ShowAllBlog.fxml");
    }
    private Blog blog;
    ServiceBlog ServiceBlog = new ServiceBlog();
    void retrievedata(Blog blog) {
        this.blog= blog;
        if (blog != null) {
            String titreblog = blog.getTitleBlog();
            updated_title.setText(blog.getTitleBlog());
            String contentblog = blog.getContentBlog();
            updated_cntent.setText(blog.getContentBlog());
            String country= blog.getCountryBlog();
            updated_country.setText(blog.getCountryBlog());




        }
    }
    @FXML
    void update_data(ActionEvent event) throws IOException, SQLException {

        String title = updated_title.getText();
        String contentblog = updated_cntent.getText();
        String countyBlog = updated_country.getText();

       Blog BlogtToUpdate = new Blog(title,contentblog, countyBlog);
        BlogtToUpdate.setIdblog(blog.getIdblog());

        try {
        // Valider les entrées d'utilisateur
        if (title.isEmpty() || contentblog.isEmpty() || countyBlog.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "All fields are required.").show();
            return;
        }
        if (!contentblog.matches("[a-zA-Z\\s]+") ) {
            mylabelcate.setText("Category  cannot contain numbers.");

            return;
        }
        if ( !title.matches("[a-zA-Z\\s]+")) {
            mylabelnom.setText("name  cannot contain numbers.");

            return;
        }








        // Créer ou mettre à jour une instance de product



        } catch (Exception e) {
            throw new RuntimeException();
        }
        ServiceBlog.updateOne(BlogtToUpdate);
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.setContentText("the product was updated succefully");
        alert.show();

    }
    @FXML
    void initialize() {
        // Listener for title text field
        updated_title.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                mylabelcate.setText(""); // Clear the label when the text field is modified
            }
        });

        // Listener for author text field
        updated_cntent.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                mylabelnom.setText(""); // Clear the label when the text field is modified
            }
        });

        // Listener for content text field
        updated_country.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                mylabelprix.setText(""); // Clear the label when the text field is modified
            }
        });


    }


}
