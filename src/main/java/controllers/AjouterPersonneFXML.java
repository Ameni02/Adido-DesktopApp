package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import services.ServicePerson;
import services.getData;
import test.FxMain;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;



public class AjouterPersonneFXML implements Initializable {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    @FXML
    private AnchorPane slider;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private Button close;

    @FXML
    private Button loginBtn;

    @FXML
    private Label lastnameerror;
    @FXML
    private Label lastnameerror1;
    @FXML
    private Label lastnameerror2;
    @FXML
    private Label lastnameerror3;
    @FXML
    private Label lastnameerror4;
    @FXML
    private Label lastnameerror5;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField UserEmail;

    @FXML
    private TextField UserFirstName;

    @FXML
    private TextField UserLastName;

    @FXML
    private TextField UserPassword;

    @FXML
    private TextField UserUserName;


    @FXML
    private TextField UserPasswordLogin;

    @FXML
    private TextField UserEmailLogin;

    @FXML
    private TextField UserPasswordConfirm;

    public void close(){
        System.exit(0);
    }

    ServicePerson person = new ServicePerson();

    @Override
    public void initialize(URL locaition , ResourceBundle resources) {
        slider.setTranslateX(1007);
        MenuClose.setVisible(false);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
           slide.setNode(slider);

           slide.setToX(0);
           slide.play();

           slider.setTranslateX(1007);

           slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(1007);
            slide.play();

            slider.setTranslateX(1007);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });
    }
    public void loginAdmin()
    {
        String sql="SELECT * FROM user WHERE email = ? and password = ?";
        connect = DBConnection.getInstance().getCnx();
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, UserEmailLogin.getText());
            prepare.setString(2, UserPasswordLogin.getText());

            result=prepare.executeQuery();

            Alert alert;

            if (UserEmailLogin.getText().isEmpty() || UserPasswordLogin.getText().isEmpty())
            {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill up all blank fields");
                    alert.showAndWait();
            }
            else
            {
                if (result.next())
                {
                    getData.username=UserUserName.getText();
                    loginBtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/EntryPage.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMouseClicked((MouseEvent event) ->{
                        x=event.getSceneX();
                        y=event.getSceneY();

                    });

                    root.setOnMouseDragged((MouseEvent event)->
                    {
                        stage.setX(event.getScreenX()-x);
                        stage.setY(event.getScreenY()-y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
                else
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        }catch (Exception e)
        {

        }
    }

    public void reset( ) throws IOException {

        FxMain.loadFXML("/resetpw.fxml");
    }
    public boolean validateLastNameInput(String input, TextField textField) {
        if (input.isEmpty() || input.length() < 2 || !input.matches("^[a-zA-Z]*$")) {
            lastnameerror.setStyle("-fx-text-fill: red;");
            lastnameerror.setText("Last Name must be at least 3 characters long and doesn't contain numbers.");
            textField.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateFirstNameInput(String input, TextField textField) {
        if (input.isEmpty() || input.length() < 2 || !input.matches("^[a-zA-Z]*$")) {
            lastnameerror1.setStyle("-fx-text-fill: red;");
            lastnameerror1.setText("First Name must be at least 3 characters long and doesn't contain numbers.");
            textField.requestFocus();
            return false;
        } else {

            return true;
        }
    }

    public boolean validateUsernameInput(String input, TextField textField) {
        if (input.isEmpty() || input.length() < 3 || !input.matches("^[a-zA-Z0-9]*$")) {
            lastnameerror2.setStyle("-fx-text-fill: red;");
            lastnameerror2.setText("Username must be at least 3 characters long.");
            textField.requestFocus();
            return false;
        } else {

            return true;
        }
    }

    public boolean validateEmailInput(String input, TextField textField) {
        if (input.isEmpty() || !input.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            lastnameerror3.setStyle("-fx-text-fill: red;");
            lastnameerror3.setText("Email is not valid.");
            textField.requestFocus();
            return false;
        } else {

            return true;
        }
    }

    public boolean validatePasswordInput(String input, TextField textField) {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;

        if (input.length() >= 8) {
            for (char c : input.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    hasUppercase = true;
                } else if (Character.isLowerCase(c)) {
                    hasLowercase = true;
                } else if (Character.isDigit(c)) {
                    hasDigit = true;
                }
            }

            if (hasUppercase && hasLowercase && hasDigit) {

                return true;
            } else {
                lastnameerror4.setStyle("-fx-text-fill: red;");
                lastnameerror4.setText("Password must contain at least one uppercase letter, one lowercase letter, and one digit.");
                textField.requestFocus();
                return false;
            }
        } else {
            lastnameerror4.setStyle("-fx-text-fill: red;");
            lastnameerror4.setText("Password must be at least 8 characters long.");
            textField.requestFocus();
            return false;
        }
    }
    public boolean validatePasswordConfirmInput(String input, TextField textField,String input1, TextField textField1) {
        if (input.isEmpty() || !input.equals(input1)) {
            lastnameerror5.setStyle("-fx-text-fill: red;");
            lastnameerror5.setText("password does not match");
            textField.requestFocus();
            return false;
        } else {

            return true;
        }
    }
    public void signup()
    {
        Alert alert;
        boolean isValid = true;
        try {

            isValid &= validateLastNameInput(UserLastName.getText(), UserLastName);
            isValid &= validateFirstNameInput(UserFirstName.getText(), UserFirstName);
            isValid &= validateUsernameInput(UserUserName.getText(), UserUserName);
            isValid &= validateEmailInput(UserEmail.getText(), UserEmail);
            isValid &= validatePasswordInput(UserPassword.getText(), UserPassword);
            isValid &= validatePasswordConfirmInput(UserPasswordConfirm.getText(), UserPasswordConfirm,UserPassword.getText(), UserPassword);

            if (isValid) {
                person.insertOne(UserFirstName.getText(),UserLastName.getText(),UserUserName.getText(),UserEmail.getText(),"[0]",UserPassword.getText(),1);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }


}
