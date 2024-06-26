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
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        FxMain.primaryStage = primaryStage;
        //Image favicon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/favicon.png")));

        primaryStage.setTitle("ADIDO");
        //  primaryStage.getIcons().add(favicon);
        loadFXML("/AjouterPersonneFXML.fxml");

    }
    public static FXMLLoader loadFXML(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(FxMain.class.getResource(fxmlFileName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        return loader;
    }

}