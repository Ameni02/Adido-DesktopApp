package controllers;

import com.sun.javafx.collections.ElementObservableListDecorator;
import com.twilio.Twilio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Ecommerce.Commande;
import services.ServiceCommande;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class AjoutcommandeController {
    private static final String ACCOUNT_SID = "ACdc2fa3af4202c9577fb871b90f2ace3c";
    private static final String AUTH_TOKEN = "043bd7356830c06a0bf70d7715c92b22";

    @FXML
    private TableColumn<Commande, String> addinfo;

    @FXML
    private TextField searchCom;
    @FXML
    private TableColumn<Commande, String> adresse;

    @FXML
    private Button btnadd;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<Commande, Date> datecommande;

    @FXML
    private TableColumn<Commande, String> etatcommande;

    @FXML
    private TableColumn<Commande, Integer> idcommande;

    @FXML
    private TextField idcommandf;

    @FXML
    private TableView<Commande> listcommande;

    @FXML
    private TableColumn<Commande, Integer> phonenumber;

    private final ServiceCommande ss = new ServiceCommande();

    @FXML
    private TextField tfadresse;

    @FXML
    private DatePicker tfdate;

    @FXML
    private TextField tfetat;

    @FXML
    private TextArea tfinfo;

    @FXML
    private TextField tfphone;



    @FXML

    void ajoutcommandeController(ActionEvent event) {
        String adresse = tfadresse.getText();
        Date date = Date.valueOf(tfdate.getValue());
        String etat = tfetat.getText();
        String info = tfinfo.getText();
        int phone;

        // Check if any of the fields are empty
        if (adresse.isEmpty() || date == null || etat.isEmpty() || info.isEmpty() || tfphone.getText().isEmpty()) {
            // Handle empty fields
            // You can show an error message or highlight the empty fields to the user
            // For example, you can use JavaFX Alert to show an error message dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }
        Date currentDate = new Date(System.currentTimeMillis());
        if (date.compareTo(currentDate) >= 0) {
            // Handle invalid date (date is before today)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid date.");
            alert.showAndWait();
            return;
        }
        // Validate address input
        if (adresse.length() <= 3) {
            // Handle invalid address (address is not more than 3 characters)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid address .");
            alert.showAndWait();
            return;
        }
        if (etat.equals("Shipped")) {
            // Send SMS using Twilio
            String recipientPhoneNumber = "+21654736876"; // Replace with the recipient's phone number
            String messageBody = "We're excited to let you know that your recent order has been shipped! Your package is on its way and should be arriving soon. :\n";

            try {
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(
                                new PhoneNumber(recipientPhoneNumber),
                                new PhoneNumber("+12569739630"), // Replace with your Twilio phone number
                                messageBody)
                        .create();

                System.out.println("Message SID: " + message.getSid());
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("SMS sent successfully!");
                alert.showAndWait();
            } catch (Exception e) {
                // Handle Twilio API exception
                e.printStackTrace();
                // Display error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to send SMS. Please try again later.");
                alert.showAndWait();
                return;
            }
        } else if (!etat.equals("Confirmed") && !etat.equals("in process") && !etat.equals("Shipped")) {
            // Handle invalid status
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter 'Confirmed', 'in process', or 'Shipped' for the status.");
            alert.showAndWait();
            return;
        }
        // Validate phone number input
        try {
            phone = Integer.parseInt(tfphone.getText());
            // Check if phone number has exactly 8 digits
            if (tfphone.getText().length() != 8) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // Handle invalid phone number format or length
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid  phone number.");
            alert.showAndWait();
            return;
        }

        Commande commande = new Commande(0, date, etat, "", adresse, phone, info);

        try {
            ServiceCommande serviceCommande = new ServiceCommande();
            serviceCommande.insertOne(commande);
            Show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void delete(ActionEvent event) {
        String idcomm = idcommandf.getText();
        ServiceCommande ss = new ServiceCommande();
        try {
            ss.deleteOne(idcomm);
            Show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void update(ActionEvent event) {


        String adresse = tfadresse.getText();
        Date date = Date.valueOf(tfdate.getValue());
        String etat = tfetat.getText();
        String info = tfinfo.getText();
        int idComm = Integer.parseInt(idcommandf.getText());
        int phone;

        // Check if any of the fields are empty
        if (adresse.isEmpty() || date == null || etat.isEmpty() || info.isEmpty() || tfphone.getText().isEmpty()) {
            // Handle empty fields
            // You can show an error message or highlight the empty fields to the user
            // For example, you can use JavaFX Alert to show an error message dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }
        Date currentDate = new Date(System.currentTimeMillis());
        if (date.compareTo(currentDate) >= 0) {
            // Handle invalid date (date is before today)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid date.");
            alert.showAndWait();
            return;
        }
        // Validate address input
        if (adresse.length() <= 3) {
            // Handle invalid address (address is not more than 3 characters)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid address .");
            alert.showAndWait();
            return;
        }
        if (!etat.equals("confirmed") && !etat.equals("in process") && !etat.equals("shipped")) {
            // Handle invalid status
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter 'confirmed', 'in process', or 'shipped' for the status.");
            alert.showAndWait();
            return;
        }
        // Validate phone number input
        try {
            phone = Integer.parseInt(tfphone.getText());
            // Check if phone number has exactly 8 digits
            if (tfphone.getText().length() != 8) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // Handle invalid phone number format or length
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid  phone number.");
            alert.showAndWait();
            return;
        }
        Commande commande = new Commande(idComm, date, etat, "", adresse, phone, info);
        ServiceCommande ss = new ServiceCommande();
        try{Show(); ss.updateOne(commande); } catch (SQLException e) {   throw new RuntimeException(e); }
    }

    @FXML
    void initialize() {
        Show();
    }

    public void Show() {
        try {
            List<Commande> ls1 = ss.selectAll();

            if (ls1 != null) {
                idcommande.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
                adresse.setCellValueFactory(new PropertyValueFactory<>("commandeAdresse"));
                phonenumber.setCellValueFactory(new PropertyValueFactory<>("commandePhoneNumber"));
                addinfo.setCellValueFactory(new PropertyValueFactory<>("additionalInformation"));
                etatcommande.setCellValueFactory(new PropertyValueFactory<>("etatCommande"));
                datecommande.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));

                listcommande.setItems(FXCollections.observableArrayList(ls1));
            } else {
                // Handle case where ls1 is null
                System.err.println("Error: List of commandes is null.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AjoutcommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    public void search() throws SQLException {
        String searchTerm = searchCom.getText().toLowerCase();
        if (searchTerm.isEmpty()) {
            ServiceCommande serviceCommande = new ServiceCommande();
            listcommande.setItems(FXCollections.observableList(serviceCommande.selectAll()));
        } else {
            ObservableList<Commande> filteredList = FXCollections.observableArrayList();
            ServiceCommande serviceCommande = new ServiceCommande();
            List<Commande> allCommandes = serviceCommande.selectAll();
            for (Commande commande : allCommandes) {
                if (commande.getCommandeAdresse().toLowerCase().contains(searchTerm) ||
                        commande.getEtatCommande().toLowerCase().contains(searchTerm) ||
                        commande.getDateCommande().toString().toLowerCase().contains(searchTerm) ||
                        String.valueOf(commande.getCommandePhoneNumber()).contains(searchTerm)) {
                    filteredList.add(commande);
                }
            }
            listcommande.setItems(filteredList);
        }
    }

}