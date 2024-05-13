package controllers.Offer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Offer.Offer;
import services.Offer.ServiceOffer;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AjouterOfferFXML {
    public Button btnUploadPhoto;
    @FXML
    private TextField tfNom, tfDescription, tfPrix;
    @FXML
    private Text errorNom,errorDescription,errorPrix,errorDD,errorDF;
    @FXML
    private DatePicker dpDateDebut, dpDateFin;
    @FXML
    private ImageView uploadedImageView;
    static String uploadedImagePath = "";
    @FXML
    private ChoiceBox<String> countryDrop;
    public void initialize()
    {
        ServiceOffer serviceOffer = new ServiceOffer();
        try {
            List<String> countries = serviceOffer.selectCountry();
            countryDrop.getItems().addAll(countries);
            countryDrop.setOnAction(event -> handleCountrySelection());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleUploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        Stage stage = (Stage) uploadedImageView.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                File targetFile = new File("src/main/resources/Offer/Uploads" + selectedFile.getName());
                AjouterOfferFXML.uploadedImagePath = "src/main/resources/Offer/Uploads" + selectedFile.getName();
                Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image(targetFile.toURI().toString());
                uploadedImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void resetForm() {
        tfNom.clear();
        tfDescription.clear();
        tfPrix.clear();
        dpDateDebut.setValue(null);
        dpDateFin.setValue(null);
        uploadedImageView.setImage(null);
        uploadedImagePath = "";
    }

    @FXML
    private void ajouterOffre(){
        if (!validateForm())
        {
            return;
        }
        ServiceOffer serviceOffer = new ServiceOffer();
        Offer offer = new Offer();
        offer.setNom_post(tfNom.getText());
        offer.setDescriptionpost(tfDescription.getText());
        offer.setPrixpost(Double.parseDouble(tfPrix.getText()));
        offer.setDateDebut(dpDateDebut.getValue());
        offer.setDateFin(dpDateFin.getValue());
        offer.setCountry(countryDrop.getValue());
        offer.setPhotopost(uploadedImagePath);
        try {
            int generatedID = serviceOffer.insertOne(offer);
            System.out.println("Offer inserted with ID: " + generatedID);
            FXMLLoader loader = new FXMLLoader(new File("/Offer/OfferDashboardFXML.fxml").toURI().toURL());
            Parent root = loader.load();
            Stage stage = (Stage) tfNom.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();
        String nomPost = tfNom.getText();
        if (nomPost == null || nomPost.isEmpty()) {
            errors.append("Nom Post is required\n");
        } else if (nomPost.length() < 4) {
            errors.append("Nom Post must be at least 4 characters long\n");
        }
        double prixPost;
        try {
            prixPost = Double.parseDouble(tfPrix.getText());
            if (prixPost < 5) {
                errors.append("Prix Post must be at least 5 Dollars\n");
            } else if (prixPost > 200) {
                errors.append("Prix Post must be at most 200 Dollars\n");
            }
        } catch (NumberFormatException e) {
            errors.append("Prix Post must be a valid number\n");
        }
        LocalDate dateDebut = dpDateDebut.getValue();
        LocalDate today = LocalDate.now();
        if (dateDebut == null || dateDebut.isBefore(today)) {
            errors.append("Date Debut is required and must be at least today\n");
        }
        LocalDate dateFin = dpDateFin.getValue();
        if (dateFin == null || dateFin.isBefore(dateDebut)) {
            errors.append("Date Fin is required and must be after Date Debut\n");
        }
        if (uploadedImagePath == null || uploadedImagePath.isEmpty()) {
            errors.append("Photo is required\n");
        }
        errorNom.setText("");
        errorPrix.setText("");
        errorDD.setText("");
        errorDF.setText("");
        if (errors.length() > 0) {
            String allErrors = errors.toString().trim();
            String[] errorArray = allErrors.split("\n");
            for (String error : errorArray) {
                if (error.startsWith("Nom")) {
                    errorNom.setText(error);
                } else if (error.startsWith("Prix")) {
                    errorPrix.setText(error);
                } else if (error.startsWith("Date Debut")) {
                    errorDD.setText(error);
                } else if (error.startsWith("Date Fin")) {
                    errorDF.setText(error);
                }
            }
            return false;
        }
        NotificationOffer.showNotification("A new Offer is added !!");
        return true;
    }

    public void ChatBot(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Offer/ChatFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Chat Bot");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fetchRandomImageURL(String country) {
        String unsplashAccessKey = "k-NOVtnQL2Yx2opVGetM7-YMn50JEuW0-WwFLTgfjWA";
        String unsplashURL = "https://api.unsplash.com/photos/random?query=" + country + "&client_id=" + unsplashAccessKey;

        try {
            URL url = new URL(unsplashURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.next());
                }
                scanner.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());
                return jsonNode.get("urls").get("regular").asText();
            } else {
                System.out.println("Failed to fetch image: HTTP error code: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch image: " + e.getMessage());
            return null;
        }
    }

    @FXML
    private void handleCountrySelection() {
        String selectedCountry = countryDrop.getValue();
        if (selectedCountry != null && !selectedCountry.isEmpty()) {
            try {
                String imageURL = fetchRandomImageURL(selectedCountry);
                if (imageURL != null && !imageURL.isEmpty()) {
                    Image image = new Image(imageURL);
                    uploadedImageView.setImage(image);
                } else {
                    System.out.println("No image URL found for country: " + selectedCountry);
                }
            } catch (Exception e) {
                System.out.println("Error fetching image: " + e.getMessage());
            }
        }
    }
}