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

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.*;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage implements Initializable {
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
    private AnchorPane homePage;

    @FXML
    private TextField UserPWAdd;

    @FXML
    private TextField UserIVAdd;

    public void displayUsername()
    {
        UsersUsername.setText(getData.username);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserRUpdate.setItems(FXCollections.observableArrayList());
        UserRAdd.setItems(FXCollections.observableArrayList("[1]","[0]"));
    }
    public void setUserInformation(String lastname,String firstname,String profilepicture)
    {
        ProfileFirstName.setText("welcome"+ firstname);
        ProfileLastName.setText(" "+ lastname);
    }
    private ObservableList<Person> addUserList;

    ServicePerson person = new ServicePerson();
    public void setAddUserShowListData()
    {

        addUserList=person.selectAll();
        UsersFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        UsersLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        UsersUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        UsersEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        UsersRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        UsersAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        UsersPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        UsersIdUser.setCellValueFactory(param -> new SimpleIntegerProperty( 1));
        UsersIdUser.setCellFactory(column -> new TableCell<Person, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });

        col_delete.setCellFactory(param -> new TableCell<Person, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Person userToDelete = getTableView().getItems().get(getIndex());

                    System.out.println(userToDelete);
                    // Delete user from the database
                    deleteUserFromDatabase(userToDelete);
                    // Remove user from the table
                    getTableView().getItems().remove(getIndex());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });


        UsersTabView.setItems(addUserList);
    }
    private void deleteUserFromDatabase(Person user) {
        try {
            person.deleteOne(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployeeSelect(){
        Person userD =  UsersTabView.getSelectionModel().getSelectedItem();
        int num = UsersTabView.getSelectionModel().getSelectedIndex();

        if ((num - 1)<-1) {return;}

        UserFNUpdate.setText(String.valueOf(userD.getFirst_name()));
        UserLNUpdate.setText(String.valueOf(userD.getLast_name()));
        UserUNUpdate.setText(String.valueOf(userD.getUsername()));
        UserPNUpdate.setText(String.valueOf(userD.getPhone_number()));
        UserIDUpdate.setText(String.valueOf(userD.getIduser()));

    }

    public void addUsersSearch() {
    System.out.println("hh");
        FilteredList<Person> filter = new FilteredList<>(addUserList, e -> true);

        UsersSearch.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateEmployeeData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();
                 if (predicateEmployeeData.getLast_name().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getFirst_name().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getUsername().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getAdress().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getRole().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getEmail().toLowerCase().contains(searchKey)) {
                    return true;
                 } else if (String.valueOf(predicateEmployeeData.getPhone_number()).contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Person> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(UsersTabView.comparatorProperty());
        UsersTabView.setItems(sortList);
    }

    public void openpopup( ) throws IOException {
        // Load the FXML file

        UserAdd.display("Popup Title", "Click me!", this::addUserAdd);
    }

public void userUpdate()
{
    try {
        Alert alert;
        if (UserFNUpdate.getText().isEmpty()||
        UserLNUpdate.getText().isEmpty()||
        UserUNUpdate.getText().isEmpty()||
        UserPNUpdate.getText().isEmpty()){alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("fill blank");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("sure ?");
            Optional<ButtonType>option = alert.showAndWait();
            alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                person.updateOne(UserFNUpdate.getText(), UserLNUpdate.getText(),UserUNUpdate.getText(), String.valueOf(UserRUpdate.getValue()),Integer.parseInt(UserPNUpdate.getText()),Integer.parseInt(UserIDUpdate.getText()));

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();
            }

        }
    } catch (SQLException e) {

        throw new RuntimeException(e);
    }
}
    public void generateSecretKey() throws IOException, SQLException {
        String account = "gmootaz3@gmail.com";
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        System.out.println(base32.encodeToString(bytes));
        String secretKey= base32.encodeToString(bytes);
        person.addSecrete(secretKey,1);


        FileWriter obj = new FileWriter("C:/Users/Si Taz/Desktop/WorkshopJDBC/src/main/resources/images/QR.txt");
        obj.write(secretKey);
        obj.close();

        bytes = base32.decode( secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        System.out.println();
        System.out.println(TOTP.getOTP(hexKey));
        try {
            String barCodeData= "otpauth://totp/"
                    + URLEncoder.encode("issuer" + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode("issuer", "UTF-8").replace("+", "%20");
            BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,200, 200);
            FileOutputStream out = new FileOutputStream("C:/Users/Si Taz/Desktop/WorkshopJDBC/src/main/resources/images/QR.png");
            MatrixToImageWriter.writeToStream(matrix,"png", out);
            out.close();
            Image image = new Image("file:///C:/Users/Si Taz/Desktop/WorkshopJDBC/src/main/resources/images/QR.png");
            Qrimg.setImage(image);
        } catch (UnsupportedEncodingException | WriterException | FileNotFoundException e) {
            throw new IllegalStateException(e);
        }

    }
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
    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        System.out.println(TOTP.getOTP(hexKey));
        return TOTP.getOTP(hexKey);
    }
    @FXML
    public void home1() {/*

        try {
            home1.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home.fxml")));
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
    @FXML
    void code() {
        String secretKey = null; // Your secret key
        try {
            File obj = new File("C:/Users/Si Taz/Desktop/WorkshopJDBC/src/main/resources/images/QR.txt");
            Scanner scanner = new Scanner(obj);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
               // secretKey = line;
                String enteredCode = code.getText();
                secretKey=person.selectSecret(1);
                String generatedCode = getTOTPCode(secretKey);
                if (enteredCode.equals(generatedCode)) {
                    Qrimg.setImage(null); // Remove QR image
                    new FileWriter("C:/Users/Si Taz/Desktop/WorkshopJDBC/src/main/resources/images/QR.txt",false).close();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}