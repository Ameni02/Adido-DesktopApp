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
import models.Comments;
import services.ServiceComment;
import test.FxMain;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class CommentBack {

    @FXML
    private TableView<Comments> CommentList;
    @FXML
    public TableColumn<Comments, Integer> idcomment;
    @FXML
    public TableColumn<Comments, Integer> idblog;
    @FXML
    public TableColumn<Comments, String> contentcomment;



    @FXML
    void initialize() throws SQLException {
        ServiceComment serviceComment = new ServiceComment();
        ObservableList<Comments> list = FXCollections.observableList(serviceComment.selectAllComments());

        idcomment.setCellValueFactory(new PropertyValueFactory<>("idcomment"));
        contentcomment.setCellValueFactory(new PropertyValueFactory<>("contentcomment"));
        CommentList.setItems(list);

        // Create the actions column
        TableColumn<Comments, Void> actionButtonColumn = new TableColumn<>("Actions");
        actionButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.setOnAction(event -> {
                    Comments comment = getTableView().getItems().get(getIndex());
                    try {
                        serviceComment.deleteOneComment(comment);
                        CommentList.getItems().remove(comment);
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
                    Comments comments = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = FxMain.loadFXML("/updateComment.fxml");
                        updateComment updateController = loader.getController();
                        updateController.retrievedata(comments);
                        System.out.println("Selected");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

        CommentList.getColumns().add(actionButtonColumn);
    }

    @FXML
    void ajoutercomment(ActionEvent actionEvent)throws IOException {
        FxMain.loadFXML("/ShowAll.fxml");
    }
}
