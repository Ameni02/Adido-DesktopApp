package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Ecommerce.Commande;
import services.ServiceCommande;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Historique {
    private final ServiceCommande ss = new ServiceCommande();
    @FXML
    private Button admin;
    @FXML
    private TableColumn<?, ?> actions;
    @FXML
    private TableColumn<?, ?> addinfo;

    @FXML
    private TableColumn<?, ?> adresse;

    @FXML
    private TableColumn<?, ?> datecommande;

    @FXML
    private TableColumn<?, ?> etatcommande;

    @FXML
    private TableColumn<?, ?> idcommande;


    @FXML
    private TableView<Commande> listcommande;

    @FXML
    private TableColumn<?, ?> phonenumber;

    @FXML
    private Button backbutton;

    @FXML
    void back(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/commanddetails.fxml")));
            backbutton.getScene().setRoot(root);


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }



    @FXML
    void initialize() {
        Show();
    }
    @FXML
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

    public void admincommand(ActionEvent actionEvent) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Ajoutcommande.fxml")));
            admin.getScene().setRoot(root);


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}


