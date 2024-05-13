package controllers;



import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.image.ImageView;

import models.Person;
import services.ServicePerson;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.*;
public class UserAdd {

        @FXML
        private TextField UsersSearch;

        @FXML
        private TextField UserAAdd;


        @FXML
        private Button home1;

        @FXML
        private TextField UserIDAdd;


        @FXML
        private TextField UserEAdd;

        @FXML
        private TextField UserFNAdd;

        @FXML
        private TextField UserLNAdd;

        @FXML
        private TextField UserPNAdd;

        @FXML
        private TextField UserUNAdd;

        @FXML
        private ComboBox<String> UserRAdd;

        @FXML
        private ImageView Qrimg;

        @FXML
        private TextField UserIDUpdate;

        @FXML
        private TextField UserFNUpdate;

        @FXML
        private TextField UserLNUpdate;

        @FXML
        private TextField UserPNUpdate;

        @FXML
        private TextField code;

        @FXML
        private TextField UserUNUpdate;

        @FXML
        private Button BtnLogOut;


        @FXML
        private Button Dashboard;

        @FXML
        private TableColumn<Person, String> UsersRole;

        @FXML
        private Label ProfileFirstName;

        @FXML
        private Label ProfileLastName;

        @FXML
        private TableColumn<Person, Void> col_delete = new TableColumn<>("Delete");


        @FXML
        private TableColumn<Person, Date> UserDateOfBirth;

        @FXML
        private TableColumn<Person, String> UsersAdress;

        @FXML
        private TableColumn<Person, String> UsersEmail;

        @FXML
        private TableColumn<Person, String> UsersFirstName;

        @FXML
        private TableColumn<Person, Number> UsersIdUser;

        @FXML
        private TableColumn<Person, String> UsersLastName;

        @FXML
        private TableColumn<Person,Integer> UsersPhoneNumber;

        @FXML
        private ComboBox<Person> UserRUpdate;

        @FXML
        private TableView<Person> UsersTabView;

        @FXML
        private TableColumn<Person, String> UsersUsername;


        @FXML
        private TextField UserPWAdd;

        @FXML
        private TextField UserIVAdd;


    public static void display(String title, String message, Runnable function) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        Button btn = new Button(message);
        btn.setOnAction(event -> {
            function.run();
            popupStage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().add(btn);

        Scene scene = new Scene(layout, 300, 150);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    ServicePerson person = new ServicePerson();
        public void addUserAdd() {
            try {
                String fn = UserFNAdd.getText();
                String ln = UserLNAdd.getText();
                String pn = UserPNAdd.getText();
                String a = UserAAdd.getText();
                String r = UserRAdd.getSelectionModel().getSelectedItem();
                String e = UserEAdd.getText();
                String u = UserUNAdd.getText();
                String p = UserPWAdd.getText();
                String iv = UserIVAdd.getText();
                System.out.println(r);
                System.out.println(fn);
                Person user = new Person(fn,ln,a,Integer.parseInt(pn),r,e,u,p,Integer.parseInt(iv));
                person.insertOne1(user);
                Alert alert;
                if (UserFNAdd.getText().isEmpty()
                        || UserEAdd.getText().isEmpty()
                        || UserRAdd.getSelectionModel().getSelectedItem() == null
                        || UserAAdd.getText().isEmpty()
                        || UserLNAdd.getText().isEmpty()
                        || UserPNAdd.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
}
