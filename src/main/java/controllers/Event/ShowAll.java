
package controllers.Event;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Event.Event;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.Event.ServiceEvent;
import test.FxMain;

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

    public ShowAll() {
    }

    @FXML
    void initialize() throws SQLException {
        ServiceEvent var10000 = this.serviceEvent;
        ObservableList<Event> list = FXCollections.observableList(ServiceEvent.selectAll());
        this.idevent.setCellValueFactory(new PropertyValueFactory("idevent"));
        this.nameevent.setCellValueFactory(new PropertyValueFactory("nameevent"));
        this.tfdescriptionevent.setCellValueFactory(new PropertyValueFactory("descriptionevent"));
        this.tfdatestartevent.setCellValueFactory(new PropertyValueFactory("datestartevent"));
        this.tfdateendevent.setCellValueFactory(new PropertyValueFactory("dateendevent"));
        this.tflocationevent.setCellValueFactory(new PropertyValueFactory("locationevent"));
        this.tfidorganiser.setCellValueFactory(new PropertyValueFactory("idorganiser"));
        this.tfnbattendees.setCellValueFactory(new PropertyValueFactory("nbattendees"));
        this.tfaffiche.setCellValueFactory(new PropertyValueFactory("affiche"));
        this.tfidcountry.setCellValueFactory(new PropertyValueFactory("idcountry"));
        this.eventListe.setItems(list);
        TableColumn<Event, Void> actionButtonColumn = new TableColumn("Actions");
        actionButtonColumn.setCellFactory((param) -> {
            return new TableCell<Event, Void>() {
                private final Button deleteButton = new Button("Delete");
                private final Button updateButton = new Button("Update");

                {
                    this.deleteButton.setStyle("-fx-background-color: #4766EF; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 5px 10px; -fx-border-radius: 50px; -fx-background-radius: 50px;");
                    this.deleteButton.setOnAction((event) -> {
                        Event event1 = (Event)this.getTableView().getItems().get(this.getIndex());

                        try {
                            ServiceEvent var10000 = ShowAll.this.serviceEvent;
                            ServiceEvent.deleteOne(event1);
                            ShowAll.this.eventListe.getItems().remove(event1);
                        } catch (SQLException var4) {
                            System.err.println("Unable to delete the event due to foreign key constraint: " + var4.getMessage());
                        }

                    });
                    this.updateButton.setStyle("-fx-background-color: #4766EF; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 5px 10px; -fx-border-radius: 50px; -fx-background-radius: 50px;");
                    this.updateButton.setOnAction((event) -> {
                        Event event1 = (Event)this.getTableView().getItems().get(this.getIndex());

                        try {
                            FXMLLoader loader = FxMain.loadFXML("/Event/updateEvent.fxml");
                            updateEvent updateController = (updateEvent)loader.getController();
                            updateController.retrievedata(event1);
                            System.out.println("Selected");
                        } catch (IOException var5) {
                            throw new RuntimeException(var5);
                        }
                    });
                }

                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        this.setGraphic((Node)null);
                    } else {
                        HBox buttons = new HBox(new Node[]{this.deleteButton, this.updateButton});
                        this.setGraphic(buttons);
                    }

                }
            };
        });
        this.eventListe.getColumns().add(actionButtonColumn);
    }

    @FXML
    public void exportToPDF1(ActionEvent event) {
        List<String[]> data = this.getDataFromTableView();
        File file = this.askForSaveLocation("Save PDF");
        if (file != null) {
            try {
                this.exportToPDF(data, file.getAbsolutePath());
                this.showAlert(AlertType.INFORMATION, "Success", "PDF Exported Successfully!");
            } catch (IOException var5) {
                this.showAlert(AlertType.ERROR, "Error", "Failed to export PDF!");
                var5.printStackTrace();
            }
        } else {
            this.showAlert(AlertType.ERROR, "Error", "Invalid file location selected!");
        }

    }

    private List<String[]> getDataFromTableView() {
        List<String[]> data = new ArrayList();
        int numColumns = 6;
        Iterator var3 = this.eventListe.getItems().iterator();

        while(var3.hasNext()) {
            Event event = (Event)var3.next();
            String[] rowData = new String[numColumns];
            rowData[0] = event.getNameevent();
            rowData[1] = event.getDescriptionevent();
            rowData[2] = event.getDatestartevent().toString();
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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (.pdf)", new String[]{".pdf"});
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage)this.eventListe.getScene().getWindow();
        return fileChooser.showSaveDialog(stage);
    }

    private void exportToPDF(List<String[]> data, String filePath) throws IOException {
        PDDocument document = new PDDocument();

        try {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            try {
                float margin = 50.0F;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2.0F * margin;
                float yPosition = yStart;
                float rowHeight = 20.0F;
                float cellMargin = 5.0F;
                int numColumns = ((String[])data.get(0)).length;
                float[] columnWidths = new float[numColumns];

                for(int i = 0; i < numColumns; ++i) {
                    columnWidths[i] = tableWidth / (float)numColumns;
                }

                for(Iterator var23 = data.iterator(); var23.hasNext(); yPosition -= rowHeight) {
                    String[] rowData = (String[])var23.next();
                    float xPosition = margin;

                    for(int i = 0; i < numColumns; ++i) {
                        String cellValue = rowData[i];
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12.0F);
                        contentStream.newLineAtOffset(xPosition + cellMargin, yPosition - rowHeight + cellMargin);
                        contentStream.showText(cellValue);
                        contentStream.endText();
                        xPosition += columnWidths[i];
                    }
                }
            } catch (Throwable var21) {
                try {
                    contentStream.close();
                } catch (Throwable var20) {
                    var21.addSuppressed(var20);
                }

                throw var21;
            }

            contentStream.close();
            document.save(filePath);
        } catch (Throwable var22) {
            try {
                document.close();
            } catch (Throwable var19) {
                var22.addSuppressed(var19);
            }

            throw var22;
        }

        document.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void AjouterEvent(MouseEvent mouseEvent) throws IOException {
        FxMain.loadFXML("/Event/AjouterEvent.fxml");
    }

    @FXML
    public void search() throws SQLException {
        String searchTerm = this.searchevent.getText().toLowerCase();
        if (searchTerm.isEmpty()) {
            ServiceEvent var10001 = this.serviceEvent;
            this.eventListe.setItems(FXCollections.observableList(ServiceEvent.selectAll()));
        } else {
            ObservableList<Event> filteredList = this.eventListe.getItems().filtered((event) -> {
                return event.getNameevent().toLowerCase().contains(searchTerm) || event.getDescriptionevent().toLowerCase().contains(searchTerm) || event.getDatestartevent().toString().toLowerCase().contains(searchTerm) || event.getDateendevent().toString().toLowerCase().contains(searchTerm) || event.getLocationevent().toLowerCase().contains(searchTerm) || String.valueOf(event.getNbattendees()).contains(searchTerm);
            });
            this.eventListe.setItems(filteredList);
        }

    }
}
