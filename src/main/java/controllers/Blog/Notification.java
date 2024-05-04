package controllers.Blog;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Notification {

    public static void showNotification(String message) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        Label label = new Label(message);
        label.setStyle("-fx-background-color: lightblue; -fx-padding: 30px;");
        HBox hbox = new HBox(label);
        hbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(hbox);
        stage.setScene(scene);

        // Positionner la fenêtre en bas de l'écran
        stage.setX(stage.getMinWidth() - stage.getWidth() +9900); // Ajuster selon votre besoin

        // Animation pour afficher la notification pendant quelques secondes
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> stage.close());
        delay.play();

        stage.show();
    }
}
