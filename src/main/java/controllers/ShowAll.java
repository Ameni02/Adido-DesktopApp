package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Event;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import servises.ServiceEvent;
import test.FxMain;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ShowAll {

    @FXML
    private TableView<Event> eventListe;

    @FXML
    private TableColumn<Event, Integer> idevent;

    @FXML
    private TableColumn<Event, String> nameevent;

    @FXML
    private TableColumn<Event, String> tfaffiche;

    @FXML
    private TableColumn<Event, String> tfdateendevent;

    @FXML
    private TableColumn<Event, String> tfdatestartevent;

    @FXML
    private TableColumn<Event, String> tfdescriptionevent;

    @FXML
    private TableColumn<Event, Integer> tfidcountry;

    @FXML
    private TableColumn<Event, Integer> tfidorganiser;

    @FXML
    private TableColumn<Event, String> tflocationevent;

    @FXML
    private TableColumn<Event, Integer> tfnbattendees;
    @FXML
    private Button btnexportToPDF;


    @FXML
    private TextField searchevent;




    ServiceEvent serviceEvent = new ServiceEvent();

    @FXML
    void initialize() throws SQLException {
        ObservableList<Event> list = FXCollections.observableList(serviceEvent.selectAll());

        idevent.setCellValueFactory(new PropertyValueFactory<>("idevent"));
        nameevent.setCellValueFactory(new PropertyValueFactory<>("nameevent"));
        tfdescriptionevent.setCellValueFactory(new PropertyValueFactory<>("descriptionevent"));
        tfdatestartevent.setCellValueFactory(new PropertyValueFactory<>("datestartevent"));
        tfdateendevent.setCellValueFactory(new PropertyValueFactory<>("dateendevent"));
        tflocationevent.setCellValueFactory(new PropertyValueFactory<>("locationevent"));
        tfidorganiser.setCellValueFactory(new PropertyValueFactory<>("idorganiser"));
        tfnbattendees.setCellValueFactory(new PropertyValueFactory<>("nbattendees"));
        tfaffiche.setCellValueFactory(new PropertyValueFactory<>("affiche"));
        tfidcountry.setCellValueFactory(new PropertyValueFactory<>("idcountry"));
        eventListe.setItems(list);

        TableColumn<Event, Void> actionButtonColumn = new TableColumn<>("Actions");
        actionButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.setStyle("-fx-background-color: #4766EF; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 10px; " +
                        "-fx-padding: 5px 10px; " +
                        "-fx-border-radius: 50px; " +
                        "-fx-background-radius: 50px;");
                deleteButton.setOnAction(event -> {
                    Event event1 = getTableView().getItems().get(getIndex());
                    try {
                        serviceEvent.deleteOne(event1);
                        eventListe.getItems().remove(event1);
                    } catch (SQLException e) {
                        System.err.println("Unable to delete the event due to foreign key constraint: " + e.getMessage());
                    }
                });

                updateButton.setStyle("-fx-background-color: #4766EF; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 10px; " +
                        "-fx-padding: 5px 10px; " +
                        "-fx-border-radius: 50px; " +
                        "-fx-background-radius: 50px;");
                updateButton.setOnAction(event -> {
                    Event event1 = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = FxMain.loadFXML("/updateEvent.fxml");
                        updateEvent updateController = loader.getController();
                        updateController.retrievedata(event1);
                        System.out.println("Selected");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(deleteButton, updateButton);
                    setGraphic(buttons);
                }
            }
        });

        eventListe.getColumns().add(actionButtonColumn);
    }

    @FXML
    public void exportToPDF1(ActionEvent event) {
        List<String[]> data = getDataFromTableView();
        File file = askForSaveLocation("Save PDF");

        if (file != null) {
            try {
                exportToPDF(data, file.getAbsolutePath());
                showAlert(Alert.AlertType.INFORMATION, "Success", "PDF Exported Successfully!");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to export PDF!");
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid file location selected!");
        }
    }

    private List<String[]> getDataFromTableView() {
        List<String[]> data = new ArrayList<>();
        int numColumns = 6; // Adjust this according to the number of columns

        for (Event event : eventListe.getItems()) {
            String[] rowData = new String[numColumns];
            rowData[0] = event.getNameevent();
            rowData[1] = event.getDescriptionevent();
            rowData[2] = event.getDatestartevent().toString(); // Assuming these are of type String
            rowData[3] = event.getDateendevent().toString();
            rowData[4] = event.getLocationevent();
            rowData[5] = String.valueOf(event.getNbattendees());
            data.add(rowData);
        }

        return data;
    }

    private File askForSaveLocation(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (.pdf)", ".pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage) eventListe.getScene().getWindow();
        return fileChooser.showSaveDialog(stage);
    }

    private void exportToPDF(List<String[]> data, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;

                float rowHeight = 20;
                float cellMargin = 5;

                int numColumns = data.get(0).length;
                float[] columnWidths = new float[numColumns];
                for (int i = 0; i < numColumns; i++) {
                    columnWidths[i] = tableWidth / numColumns;
                }

                for (String[] rowData : data) {
                    float xPosition = margin;
                    for (int i = 0; i < numColumns; i++) {
                        String cellValue = rowData[i];
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(xPosition + cellMargin, yPosition - rowHeight + cellMargin);
                        contentStream.showText(cellValue);
                        contentStream.endText();
                        xPosition += columnWidths[i];
                    }
                    yPosition -= rowHeight;
                }
            }

            document.save(filePath);
        }
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void AjouterEvent(MouseEvent mouseEvent) throws IOException {
        FxMain.loadFXML("/AjouterEvent.fxml");
    }

    @FXML
    public void search() throws SQLException {
        String searchTerm = searchevent.getText().toLowerCase();
        if (searchTerm.isEmpty()) {
            eventListe.setItems(FXCollections.observableList(serviceEvent.selectAll()));
        } else {
            ObservableList<Event> filteredList = eventListe.getItems().filtered(event ->
                    event.getNameevent().toLowerCase().contains(searchTerm) ||
                            event.getDescriptionevent().toLowerCase().contains(searchTerm) ||
                            event.getDatestartevent().toString().toLowerCase().contains(searchTerm) ||
                            event.getDateendevent().toString().toLowerCase().contains(searchTerm) ||
                            event.getLocationevent().toLowerCase().contains(searchTerm) ||
                            String.valueOf(event.getNbattendees()).contains(searchTerm));
            eventListe.setItems(filteredList);
        }
    }
}
