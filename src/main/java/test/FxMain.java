package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxMain extends Application {
private static Stage primaryStage;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage; // Utilisez le stage passé en paramètre
        primaryStage.setTitle("TechTerra Portal");
        loadFXML("/ShowAll.fxml");
    }

    public static FXMLLoader loadFXML(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(FxMain.class.getResource(fxmlFileName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene); // Utilisez primaryStage initialisé
        primaryStage.show();
        return loader;
    }
}


