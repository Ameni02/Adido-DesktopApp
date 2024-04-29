package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import models.Blog;
import services.ServiceBlog;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class ShowAll {

    public TableColumn id_post;
    @FXML
    private TableColumn<?, ?> action;
    @FXML
    private TableColumn<?, ?> id_blog;

    @FXML
    private TableColumn<?, ?> id_content;

    @FXML
    private TableColumn<?, ?> id_country;



    @FXML
    private TableView<Blog> BlogListe;
    @FXML

    void ShowOfficelBlog(ActionEvent event) throws IOException {
        FxMain.loadFXML("/BlogOfficell.fxml");

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
                        FXMLLoader loader = FxMain.loadFXML("/updateBlog.fxml");
                        updateBlog updateController = loader.getController();
                        updateController.retrievedata(blog);
                        System.out.println("Selected");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                });
                aprouveButton.setOnAction(event -> {
                    Blog blog = getTableView().getItems().get(getIndex());

                    // Mettre à jour l'état d'approbation dans la base de données
                    blog.setApproved(true); // Supposons que setApprouve est une méthode pour modifier l'attribut "approuve" du blog
                    // Enregistrez les modifications dans la base de données, par exemple :
                    // blogService.updateBlog(blog); // Supposons que blogService est votre service pour gérer les opérations sur les blogs

                    // Mettre à jour l'affichage dans le TableView si nécessaire
                    getTableView().refresh(); // Rafraîchit la vue pour refléter les modifications

                    // Afficher un message de confirmation ou effectuer d'autres actions nécessaires
                    System.out.println("Publication approuvée : " + blog.getTitleBlog());

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(deleteButton, updateButton);
                    setGraphic(buttons);
                }
            }
        });

        BlogListe.getColumns().add(actionButtonColumn);
    }


}
