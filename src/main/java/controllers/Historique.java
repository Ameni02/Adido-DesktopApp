package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Ecommerce.Commande;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.ServiceCommande;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.paint.Color;
import java.awt.Desktop;
import java.io.File;


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

    public void generatePdf(ActionEvent actionEvent) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Get the selected item from the table view
            Commande selectedCommande = listcommande.getSelectionModel().getSelectedItem();

            if (selectedCommande != null) {
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Set margins
                    final float margin = 40;
                    final float lineSpacing = 20;  // Adjust line spacing as needed

                    // Y position for information (start from bottom with some margin)
                    float yPosition = page.getMediaBox().getHeight() - margin - lineSpacing;

                    // Information section with smaller font and indentation
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("ID Commande: " + selectedCommande.getIdCommande());
                    yPosition -= lineSpacing;  // Update Y position for next line
                    contentStream.newLineAtOffset(0, lineSpacing);  // Move down without changing X position
                    contentStream.showText("Date Commande: " + selectedCommande.getDateCommande());
                    yPosition -= lineSpacing;
                    contentStream.newLineAtOffset(0, lineSpacing);
                    // ... (show other information using the same pattern)
                    contentStream.showText("Informations supplémentaires: " + selectedCommande.getAdditionalInformation());
                    contentStream.endText();
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String filename = "Historique_" + sdf.format(new Date()) + "_" + selectedCommande.getDateCommande() + ".pdf";
                File file = new File(filename);
                document.save(file);
                System.out.println("PDF generated successfully!");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("No Commande Selected");
                alert.setContentText("Please select a commande from the table to generate PDF");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





