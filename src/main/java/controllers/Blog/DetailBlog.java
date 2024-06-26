package controllers.Blog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.Blog.Blog;
import models.Blog.Comments;
import services.Blog.ServiceBlog;
import services.Blog.ServiceComment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public class DetailBlog {

    public TextArea ChampsComment;
    public ImageView generateQRCodeImage;
    public Button generateQRCode;
    public Label Voice;
    public TextArea testContent;
    public Text tettt;


    @FXML
    private Label contryLbael;

    @FXML
    public ImageView imageShow;
    @FXML
    public GridPane comentgrid;

    @FXML
    private Label titleLabel;


    private int selectedBlogId;
    List<String> badWords = Arrays.asList("Fuck", "putin", "bitch", "pute");


    public void initData(Blog selectedBlog) {
        titleLabel.setText(selectedBlog.getTitleBlog());
        contryLbael.setText(selectedBlog.getCountryBlog());
        testContent.setText(selectedBlog.getContentBlog());
        StringBuilder formattedContent = new StringBuilder();
        String content = selectedBlog.getContentBlog();

        int charCount = 0;
        for (char c : content.toCharArray()) {
            formattedContent.append(c);
            charCount++;

            // Ajouter un espace après chaque '.' pour séparer les phrases
            if (c == '.') {
                formattedContent.append(" ");
                charCount = 0; // Réinitialiser le compteur de caractères
            }

            // Ajouter un saut de ligne après chaque '\n' dans la base de données
            if (c == '\n') {
                formattedContent.append("\n");
                charCount = 0; // Réinitialiser le compteur de caractères
            }

            // Insérer un espace après chaque 25 caractères
            if (charCount % 25 == 0) {
                formattedContent.append(" ");
            }
        }

// Mettre à jour le TextField avec le contenu formaté
        testContent.setText(formattedContent.toString());
        displayComments(selectedBlog.getIdblog());
        selectedBlogId = selectedBlog.getIdblog(); // Stocker l'ID du blog sélectionné

        try {
            // Récupérer l'URL de l'image associée au blog
            ServiceBlog serviceBlog = new ServiceBlog();
            String imageUrl = String.valueOf(serviceBlog.getImageByBlogId(selectedBlogId));
            if (imageUrl != null) {
                // Charger l'image depuis un fichier local
                File file = new File(imageUrl);
                Image image = new Image(file.toURI().toString());
                imageShow.setImage(image); // Définir l'image dans ImageView
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception selon vos besoins
        }
    }


    private void displayComments(int blogId) {
        try {
            ServiceComment serviceComment = new ServiceComment();
            List<Comments> commentList = serviceComment.getCommentsByBlogId(blogId);

            int row = 0;
            for (Comments comment : commentList) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/Blog/CommentSection.fxml"));
                Parent card =loader.load();
                CommentSection commentSection =loader.getController();
                commentSection.setDataC(comment.getContentcomment(),comment.getIduser());
                comentgrid.add(card, 0, row);
                row++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Méthode pour ajouter un commentaire
    public void addComment(ActionEvent actionEvent) {
        try {
            String newComment = ChampsComment.getText(); // Récupérer le texte du champ de commentaire
            if (!newComment.isEmpty()) { // Vérifier que le commentaire n'est pas vide
                // Vérifier si le commentaire contient des mots inappropriés
                boolean containsBadWord = false;
                for (String badWord : badWords) {
                    if (newComment.toLowerCase().contains(badWord)) {
                        containsBadWord = true;
                        break; // Sortir de la boucle dès qu'un mot inapproprié est trouvé
                    }
                }

                if (containsBadWord) {
                    // Afficher une alerte ou un message indiquant que le commentaire contient des mots inappropriés
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Commentaire refusé");
                    alert.setHeaderText(null);
                    alert.setContentText("Votre commentaire contient des mots inappropriés et ne peut pas être ajouté.");
                    alert.showAndWait();
                } else {
                    // Le commentaire ne contient pas de mots inappropriés, l'ajouter normalement
                    Comments comment = new Comments();
                    comment.setContentcomment(newComment);
                    comment.setIdblog(selectedBlogId); // Utiliser l'ID du blog sélectionné

                    ServiceComment serviceComment = new ServiceComment();
                    serviceComment.insertOneComment(comment,1);

                    displayComments(selectedBlogId);
                    ChampsComment.setText(""); // Effacer le champ de commentaire après l'ajout
                }
            } else {
                // Afficher une alerte si le champ de commentaire est vide
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Commentaire vide");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer un commentaire avant de l'ajouter.");
                alert.showAndWait();
            }
            Notification.showNotification("A new comment is added !!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //QR SECTION
    private Image generateQRCodeImage(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1); // Adjust margin as needed


        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);


            return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (WriterException | IOException e) {
            e.printStackTrace(); // Handle error gracefully in your application
            return null;
        }

    }
    @FXML
    private void generateQRCode() {
        String Title = titleLabel.getText().trim();
        String Content = testContent.getText().trim();
        String Country = contryLbael.getText().trim();


        // Concatenate the cheque details into a single string
        String qrDetails = "Title: " + Title + "\n\nContent:\n" + Content + "\n\nCountry:\n" + Country;



        // Set the width and height of the QR code
        int width = 350;
        int height = 350;


        // Generate the QR code image based on the cheque details
        Image qrImage = generateQRCodeImage(qrDetails, width, height);


        // Set the generated QR code image to the ImageView
        generateQRCodeImage.setImage(qrImage);
    }


    @FXML
    public void Microbtn(ActionEvent actionEvent) {
        // Create a new Task for asynchronous speech recognition
        Task<String> recognitionTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                // Replace with your subscription key and region
                SpeechConfig speechConfig = SpeechConfig.fromSubscription("71109cefd1b54092b1a24c0337e0c264", "francecentral");

                try (SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, AudioConfig.fromDefaultMicrophoneInput())) {
                    System.out.println("Speak into your microphone."); // Inform user to speak

                    Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
                    SpeechRecognitionResult result = task.get();

                    if (result.getReason() == ResultReason.Canceled) {
                        System.out.println("Cancellation detected.");
                        return null;
                    } else if (result.getReason() == ResultReason.NoMatch) {
                        System.out.println("No speech recognized.");
                        return null;
                    } else {
                        String recognizedText = result.getText();
                        System.out.println("Recognized text: " + recognizedText);
                        return recognizedText;
                    }
                }
            }
        };

        // Start the recognition task and handle the result
        recognitionTask.setOnSucceeded(event1 -> {
            String transcribedText = recognitionTask.getValue();
            if (transcribedText != null) {
                // Update UI element (if desired)
                Voice.setText(transcribedText);

                // Call addComment method with the transcribed text
                ChampsComment.setText(transcribedText); // Set the transcribed text in the comment field
                addComment(null); // Call addComment method with ActionEvent as null
            }
        });

        recognitionTask.setOnFailed(event1 -> {
            Throwable exception = recognitionTask.getException();
            System.err.println("Speech recognition failed: " + exception.getMessage());
        });

        new Thread(recognitionTask).start();
    }

}