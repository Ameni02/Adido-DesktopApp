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
        primaryStage.setTitle("TechTerra Portal");
        //  primaryStage.getIcons().add(favicon);
        loadFXML("/Blog/BlogOfficell.fxml");
    }
    public static FXMLLoader loadFXML(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(FxMain.class.getResource(fxmlFileName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        return loader;
    }
}