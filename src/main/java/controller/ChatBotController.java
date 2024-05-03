package controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ChatBotController {

    @FXML
    private TextField userInputField;

    @FXML
    private TextArea chatArea;

    @FXML
    private Button sendButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleSendButton(ActionEvent event) {
        String userMessage = userInputField.getText();

        if (isBadContent(userMessage)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Bad word detected!");
            alert.show();
            System.out.println("Bad word detencted!!!");
            return;
        }
        chatArea.setText(extractText(chatGPTRequest(userMessage)));
        userInputField.clear();
    }



    private static final String API_URL = "https://chatgpt-api8.p.rapidapi.com/";
    private static final String API_KEY = "a12427d48emsh2e548b5f8f26bd7p19eacejsna0e0d08175b2";
    private static final String HOST = "chatgpt-api8.p.rapidapi.com";

    public String chatGPTRequest(String userMessage) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        try {
            String requestBody = String.format("[\n    {\n        \"content\": \"%s\",\n        \"role\": \"user\"\n    }\n]", userMessage);

            Response response = client.prepare("POST", API_URL)
                    .setHeader("content-type", "application/json")
                    .setHeader("X-RapidAPI-Key", API_KEY)
                    .setHeader("X-RapidAPI-Host", HOST)
                    .setBody(requestBody)
                    .execute()
                    .toCompletableFuture()
                    .join();

            return response.getResponseBody();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing the HTTP client", e);
            }
        }
    }


    public static String extractText(String jsonResponse) {
        try {
            // Create ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON response
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Extract the "text" value
            return rootNode.get("text").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle parsing exceptions
        }
    }


    public static boolean isBadContent(String content) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        try {
            CompletableFuture<Response> responseFuture = client.prepare("POST", "https://neutrinoapi-bad-word-filter.p.rapidapi.com/bad-word-filter")
                    .setHeader("content-type", "application/x-www-form-urlencoded")
                    .setHeader("X-RapidAPI-Key", "8433d15735msh2702a52a05e99bdp1e7f6djsn60b6c77343ce")
                    .setHeader("X-RapidAPI-Host", "neutrinoapi-bad-word-filter.p.rapidapi.com")
                    .setBody("content=" + encodeContent(content) + "&censor-character=*")
                    .execute()
                    .toCompletableFuture();

            Response response = responseFuture.join();
            return parseBadWordResponse(response.getResponseBody());
        } catch (IOException e) {
            throw new RuntimeException("Error executing request", e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing client", e);
            }
        }
    }

    private static String encodeContent(String content) {
        // You may need to URL encode the content if necessary
        return content.replace(" ", "%20");
    }

    private static boolean parseBadWordResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        JsonNode isBadNode = jsonNode.get("is-bad");
        return isBadNode != null && isBadNode.asBoolean();
    }




}