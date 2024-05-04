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
               // idcommande.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
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
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Initialize the content stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Define the title and date
            String title = "List Commande";
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String currentDate = dateFormat.format(new Date());

            // Calculate the centered position for the title
            float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000f * 14;
            float titleXPosition = (page.getMediaBox().getWidth() - titleWidth) / 2;
            float titleYPosition = page.getMediaBox().getHeight() - 50; // Adjust as needed

            // Draw the title
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 19);
            contentStream.newLineAtOffset(titleXPosition, titleYPosition);
            contentStream.showText(title);
            contentStream.endText();

            // Place the date under the title on the left side
            float dateYPosition = titleYPosition - 40; // Adjust as needed
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset(50, dateYPosition); // 50 is the left margin
            contentStream.showText("Date : " + currentDate);
            contentStream.endText();

            // Add an empty line under the date by drawing a line
            float emptyLineHeight = 10; // Adjust as needed
            float nextContentYPosition = dateYPosition - (emptyLineHeight );
            contentStream.moveTo(50, nextContentYPosition); // Start of the line
            contentStream.lineTo(page.getMediaBox().getWidth() - 50, nextContentYPosition); // End of the line
            contentStream.stroke(); // Draw the line

            // Define the table layout
            float margin = 50;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yStart = dateYPosition - 30;
            float rowHeight = 20;
            float tableMargin = 10;

            // Assuming listCommande is your ObservableList<Commande>
            List<Commande> commandeList = listcommande.getItems();

            // Draw table headers
            float yPosition = yStart - rowHeight - tableMargin;
            float xPosition = margin;
            String[] headers = {"ID Commande", "Date Commande", "Informations supplémentaires"};
            for (String header : headers) {
                float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(header) / 1000f * 12;
                float centeredPosition = xPosition + (tableWidth / headers.length - textWidth) / 2;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(centeredPosition, yPosition);
                contentStream.showText(header);
                contentStream.endText();
                xPosition += tableWidth / headers.length;
            }





            // Draw table data
            yPosition -= rowHeight + tableMargin;
            for (Commande commande : commandeList) {
                xPosition = margin;
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                String dateAsString = date.format(commande.getDateCommande());
                // Draw each detail
                for (String detail : new String[]{String.valueOf(commande.getIdCommande()), dateAsString, commande.getAdditionalInformation()}) {
                    float textWidth = PDType1Font.HELVETICA.getStringWidth(detail) / 1000f * 10;
                    float centeredPosition = xPosition + (tableWidth / headers.length - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(centeredPosition, yPosition);
                    contentStream.showText(detail);
                    contentStream.endText();
                    xPosition += tableWidth / headers.length;
                }
                yPosition -= rowHeight + tableMargin;
            }

            // Close the content stream and save the document
            SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMdd");
            contentStream.close();
            document.save("Historique_" + fileDateFormat.format(new Date()) + ".pdf");
            document.close();

            // Open the generated PDF file automatically
            File pdfFile = new File("Historique_" + fileDateFormat.format(new Date()) + ".pdf");
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}





