package controllers.Blog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import models.Blog.Blog;
import services.Blog.ServiceBlog;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ShowAllBlog {

    public TableColumn id_post;
    public Label Voice;
    @FXML
    private TableColumn<?, ?> action;
    @FXML
    private TableColumn<?, ?> id_blog;

    @FXML
    private TableColumn<?, ?> id_content;

    @FXML
    private TableColumn<?, ?> id_country;
    @FXML
    private TextField rechercheblog;


    @FXML
    private TableView<Blog> BlogListe;

    @FXML
    void ShowOfficelBlog(ActionEvent event) throws IOException {
        FxMain.loadFXML("/Blog/BlogOfficell.fxml");

    }


    @FXML
    void initialize() throws SQLException {

        ServiceBlog ServiceBlog = new ServiceBlog();
        ObservableList<Blog> list = FXCollections.observableList(ServiceBlog.selectAll());
        TableColumn<Object, Object> id_product;
        id_blog.setCellValueFactory(new PropertyValueFactory<>("idblog"));
        id_post.setCellValueFactory(new PropertyValueFactory<>("titleBlog"));
        id_content.setCellValueFactory(new PropertyValueFactory<>("contentBlog"));
        id_country.setCellValueFactory(new PropertyValueFactory<>("CountryBlog"));
        BlogListe.setItems(list);


        TableColumn<Blog, Void> actionButtonColumn = new TableColumn<>("Actions");
        actionButtonColumn.setCellFactory(param -> new TableCell<>() {

            private final Button aprouveButton = new Button("Aprouve");
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.setOnAction(event -> {
                    Blog blog = getTableView().getItems().get(getIndex());
                    try {
                        ServiceBlog.deleteOne(blog);
                        BlogListe.getItems().remove(blog);
                        System.out.println("Post deleted!");
                    } catch (SQLException e) {
                        if (e instanceof SQLIntegrityConstraintViolationException) {
                            System.err.println("Cannot delete the post due to foreign key constraint.");
                            // Optionally, inform the user about the constraint violation
                            // Display an error message or use an alert dialog
                        } else {
                            // Handle other SQLExceptions
                            e.printStackTrace();
                            System.err.println("Error deleting post: " + e.getMessage());
                        }
                    }
                });


                updateButton.setOnAction(event -> {
                    Blog blog = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = FxMain.loadFXML("/Blog/updateBlog.fxml");
                        updateBlog updateController = loader.getController();
                        updateController.retrievedata(blog);
                        System.out.println("Selected");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                });
                aprouveButton.setOnAction(event -> {
                    Blog blog = getTableView().getItems().get(getIndex());
                    blog.setApproved(1); // Changer l'état approuvé à true
                    System.out.println(blog);
                    try {
                        ServiceBlog serviceBlog = new ServiceBlog();
                        serviceBlog.updateApprovedStatus(blog); // Mettre à jour le blog dans la base de données
                        BlogListe.refresh(); // Rafraîchir la table pour refléter les modifications

            String userEmail = "sarah1sahlii1@gmail.com"; // Adresse e-mail du destinataire
            String enchereDescription = "Post add"; // Description spécifique à votre application
            String userName = "Sarah reads for you"; // Nom de l'utilisateur (optionnel)



            EmailSenderBlog.sendConfirmationEmail(userEmail, enchereDescription, userName) ;
                    } catch (SQLException e) {
                        // Gérer l'exception ici
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(deleteButton, updateButton, aprouveButton);
                    setGraphic(buttons);
                }
            }
        });

        BlogListe.getColumns().add(actionButtonColumn);
    }


    @FXML
    private void recherchebtn(ActionEvent actionEvent) {
        try {
            // Récupérer le titre du post à rechercher depuis le TextField
            String titleToSearch = rechercheblog.getText();

            // Obtenir la liste des blogs actuellement affichés dans la TableView
            ObservableList<Blog> blogs = BlogListe.getItems();

            // Parcourir la liste pour trouver le blog avec le titre spécifié
            for (Blog blog : blogs) {
                if (blog.getTitleBlog().equalsIgnoreCase(titleToSearch)) {
                    // Blog trouvé, afficher les détails ou effectuer une action
                    System.out.println("Blog trouvé : " + blog);
                    // Vous pouvez également afficher les détails dans une alerte ou effectuer une autre action ici
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Blog trouvé");
                    alert.setHeaderText(null);
                    alert.setContentText("Blog ID : " + blog.getIdblog() +
                            "\nTitre : " + blog.getTitleBlog() +
                            "\nContenu : " + blog.getContentBlog() +
                            "\nPays : " + blog.getCountryBlog());
                    alert.showAndWait();
                    return; // Arrêter la recherche après avoir trouvé le premier blog avec le titre spécifié
                }
            }

            // Si aucun blog correspondant au titre n'est trouvé
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Blog non trouvé");
            alert.setHeaderText(null);
            alert.setContentText("Aucun blog trouvé avec le titre : " + titleToSearch);
            alert.showAndWait();
        } catch (Exception e) {
            // Gérer les exceptions
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la recherche du blog.");
            alert.showAndWait();
        }

    }


}