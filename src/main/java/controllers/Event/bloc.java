

package controllers.Event;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Event.Event;
import services.Event.ServiceEvent;

public class bloc {
    public ImageView generateQRCodeImage;
    public Button generateQRCode;
    public ImageView testimage;
    @FXML
    private Text descriptionevent;
    @FXML
    private Text locationevent;
    @FXML
    private VBox eventBox;
    @FXML
    private Button event_readmore;
    @FXML
    private Label nameevent;
    @FXML
    private ImageView imagee;
    private int idevent;
    @FXML
    private Button backbtn;

    public bloc() {
    }

    @FXML
    void initialize() {
    }

    public void setData(String title, String content, int id, String afficheUrl) {
        this.nameevent.setText(title);
        this.descriptionevent.setText(content);
        this.idevent = id;

        try {
            File file = new File(afficheUrl);
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);
            this.imagee.setImage(image);


        } catch (IllegalArgumentException e) {
            System.err.println("Erreur de chargement de l'image : " + e.getMessage());
            System.err.println("URL invalide : " + afficheUrl);
            // Afficher un message d'erreur à l'utilisateur ou effectuer une autre action appropriée
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void handleReadMore() {
        try {
            ServiceEvent ServiceEvent = new ServiceEvent();
            Event event = ServiceEvent.getNewsById(this.idevent);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Event/Alldata.fxml"));
            Parent fullNewsRoot = (Parent)loader.load();
            Alldata AlldataController = (Alldata)loader.getController();
            AlldataController.setFullContent(event);
            Scene currentScene = this.event_readmore.getScene();
            ((Pane)currentScene.getRoot()).getChildren().setAll(new Node[]{fullNewsRoot});
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    private Image generateQRCodeImage(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (IOException | WriterException var8) {
            var8.printStackTrace();
            return null;
        }
    }

    @FXML
    private void generateQRCode() {
        String Title = this.nameevent.getText().trim();
        String Content = this.descriptionevent.getText().trim();
        String qrDetails = "Title: " + Title + "\n\nContent:\n" + Content;
        int width = 350;
        int height = 350;
        Image qrImage = this.generateQRCodeImage(qrDetails, width, height);
        this.generateQRCodeImage.setImage(qrImage);
    }
}
