    package controller;
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
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextArea;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.GridPane;
    import models.Blog;
    import models.Comments;
    import services.ServiceBlog;
    import services.ServiceComment;

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


        @FXML
        private Label contryLbael;

        @FXML
        public ImageView imageShow;
        @FXML
        public GridPane comentgrid;

        @FXML
        private Label titleLabel;

        @FXML
        private Label contentLabel;
        private int selectedBlogId;
        List<String> badWords = Arrays.asList("Fuck", "putin", "bitch", "pute");


        public void initData(Blog selectedBlog) {
            titleLabel.setText(selectedBlog.getTitleBlog());
            contentLabel.setText(selectedBlog.getContentBlog());
            contryLbael.setText(selectedBlog.getCountryBlog());
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommentSection.fxml"));
                    Label commentLabel = new Label(comment.getContentcomment());
                    comentgrid.add(commentLabel, 0, row);
                    row++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
                        serviceComment.insertOneComment(comment);

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
            String Content = contentLabel.getText().trim();
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
                                //System.out.println("Recognized text: " + recognizedText);
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
                    }
                });

                recognitionTask.setOnFailed(event1 -> {
                    Throwable exception = recognitionTask.getException();
                    System.err.println("Speech recognition failed: " + exception.getMessage());
                });

                new Thread(recognitionTask).start();
            }


    }


