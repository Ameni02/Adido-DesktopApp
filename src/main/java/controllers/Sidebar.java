package controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Person;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import services.ServicePerson;
import services.getData;
import test.FxMain;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.*;

import controllers.HomePage;

public class Sidebar {

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnblog;

    @FXML
    private Button btnevent;

    @FXML
    private Button btnoffre;

    @FXML
    private Button btnproduct;

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

    public void displayUsername()
    {
        UsersUsername.setText(getData.username);
    }

    private double x=0;
    private double y=0;
    @FXML
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout? ");
        Optional<ButtonType> option = alert.showAndWait();
        System.out.println("wwwwww");
        try {
            if (option.get().equals(ButtonType.OK))
            {
                BtnLogOut.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterPersonneFXML.fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                root.setOnMousePressed((MouseEvent event) ->
                {
                    x=event.getSceneX();
                    y=event.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent event) ->
                {
                    stage.setX(event.getScreenX()-x);
                    stage.setY(event.getScreenY()-y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) ->
                {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception e){e.printStackTrace();}
    }
    @FXML
    public void dashbordUser(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/HomePage.fxml")));
        Dashboard.getScene().setRoot(root);
    }


    @FXML
    public void home1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/EntryPage.fxml")));
        home1.getScene().setRoot(root);
    }
    @FXML
    void toblog(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Event/Blog/BlogOfficell.fxml")));
            btnblog.getScene().setRoot(root);
    }

    @FXML
    void toevent(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Event/Show_data_for_user.fxml")));
            btnevent.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void tooffer(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Offer/AjouterOfferFXML.fxml")));
            btnoffre.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void toproduct(ActionEvent event) {

        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/OptionProduct.fxml")));
            btnproduct.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void profile(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Profile.fxml")));
        btnProfile.getScene().setRoot(root);
    }
    @FXML
    public void Dachboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/HomePage.fxml")));
        Dashboard.getScene().setRoot(root);
    }
}
