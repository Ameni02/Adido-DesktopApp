package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Country;
import models.product;
import models.Image;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import utils.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class Serviceproduct implements ProductInterface<product> {

    private Connection cnx;

    public Serviceproduct() {
        cnx = DBConnection.getInstance().getCnx();
    }


    @Override
    public void insertImageProduit(Image imageproduit) throws SQLException {
        // Vérifiez si le `product_id` existe dans la table `product`
        String checkProductIdQuery = "SELECT COUNT(*) FROM product WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(checkProductIdQuery)) {
            ps.setInt(1, imageproduit.getId_product());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                throw new SQLException("Product ID does not exist.");
            }
        }

        // Insérez l'image dans la table `images`
        String req = "INSERT INTO images (name, product_id) VALUES (?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, imageproduit.getName_img());
            ps.setInt(2, imageproduit.getId_product());
            ps.executeUpdate();
            System.out.println("Image added!");
        } catch (SQLException e) {
            throw new SQLException("Error adding image: " + e.getMessage(), e);
        }
    }
    @Override
    public void insertOne(product product) {
        String req = "INSERT INTO product(categorieproduct, nomproduct, prixproduct, stockproduct, promotionproduct, Approved,idcountry) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getCategorieproduct());
            preparedStatement.setString(2, product.getNomproduct());
            preparedStatement.setFloat(3, product.getPrixproduct());
            preparedStatement.setInt(4, product.getStockproduct());
            preparedStatement.setFloat(5, product.getPromotionproduct());
            preparedStatement.setInt(6, product.getApproved());
            preparedStatement.setInt(7, product.getIdCountry());

            // Exécutez la requête d'insertion
            preparedStatement.executeUpdate();

            // Obtenez le `product_id` généré
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int productId = rs.getInt(1);
                product.setId(productId); // Stockez le `product_id` généré dans l'objet `product`
            }

            System.out.println("Product added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  void updateOne(product product) throws SQLException {
        String requete = "UPDATE product " +
                "SET categorieproduct = ?, nomproduct = ?, prixproduct = ?, stockproduct= ?, promotionproduct = ?, Approved = ?, idcountry = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getCnx().prepareStatement(requete);
            preparedStatement.setString(1, product.getCategorieproduct());
            preparedStatement.setString(2, product.getNomproduct());
            preparedStatement.setFloat(3, product.getPrixproduct());
            preparedStatement.setInt(4, product.getStockproduct());
            preparedStatement.setInt(5, product.getPromotionproduct());
            preparedStatement.setInt(6, product.getApproved());
            preparedStatement.setInt(7, product.getIdCountry());
            preparedStatement.setInt(8, product.getId());
            preparedStatement.executeUpdate();
            System.out.println("product Updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
   /* public void deleteOne(product product) throws SQLException {
        String requete = "DELETE FROM product WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getCnx().prepareStatement(requete);
            preparedStatement.setInt(1,product.getId());
            preparedStatement.executeUpdate(); // Execute prepared statement directly without passing the query string
            System.out.println("deleted !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/
    public void deleteOne(product product) throws SQLException {
        Connection conn = DBConnection.getInstance().getCnx();
        PreparedStatement stmt = null;

        try {
            // Désactiver temporairement les contraintes de clé étrangère
            //PreparedStatement stmtDisableFK = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
            // stmtDisableFK.executeUpdate();

            // Préparer et exécuter la requête de suppression
            String requete = "DELETE FROM product WHERE id = ?";
            stmt = conn.prepareStatement(requete);
            stmt.setInt(1, product.getId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produit supprimé !");
            } else {
                System.out.println("Aucun produit trouvé avec l'ID fourni.");
            }

            // Réactiver les contraintes de clé étrangère
            PreparedStatement stmtEnableFK = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
            stmtEnableFK.executeUpdate();
        } catch (SQLException e) {
            // Gérez l'exception en conséquence
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression du produit : " + e.getMessage());
        } finally {
            // Fermer les ressources
            if (stmt != null) {
                stmt.close();
            }
            // Pas besoin de fermer la connexion car elle est gérée par DBConnection
        }
    }
    @Override
    public List<product> selectAll() throws SQLException {
        List<product> list = new ArrayList<>();
        String requete = "SELECT * FROM product";
        try {

            Statement stmt = cnx.prepareStatement(requete);
            ResultSet rs = stmt.executeQuery(requete);
            while (rs.next()) {


                product p = new product();

                p.setId(rs.getInt(1));
                p.setCategorieproduct(rs.getString(2));
                p.setNomproduct(rs.getString(3)); // Utilisez la colonne correcte pour nomproduct
                p.setPrixproduct(rs.getFloat(4)); // Utilisez la colonne correcte pour prixproduct
                p.setStockproduct(rs.getInt(5)); // Utilisez la colonne correcte pour stockproduct
                p.setPromotionproduct(rs.getInt(6)); // Utilisez la colonne correcte pour promotionproduct
                // p.setIdCountry(rs.getInt(7));
                p.setApproved(rs.getInt(7));
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }



    public List<Image> getImagesByProductId(int productId) throws SQLException {
        List<Image> images = new ArrayList<>();

        // Requête SQL pour sélectionner les images associées au produit donné
        String query = "SELECT id, name, product_id FROM images WHERE product_id = ?";

        // Préparez la déclaration avec le paramètre productId
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            // Parcourez les résultats et créez des objets Image pour chaque image associée au produit
            while (rs.next()) {
                int idImg = rs.getInt("id");
                String nameImg = rs.getString("name");
                int prodId = rs.getInt("product_id");

                // Créez un objet Image à partir des résultats
                Image image = new Image(idImg, nameImg, prodId);

                // Ajoutez l'image à la liste des images
                images.add(image);
            }
        } catch (SQLException e) {
            // Gérez l'exception en conséquence
            e.printStackTrace();
            throw  new SQLException("Erreur lors de la récupération des images du produit : " + e.getMessage());
        }

        // Retournez la liste d'images
        return images;
    }
    public List<Integer> getAllCountryIds() throws SQLException {
        // Créez une liste pour stocker les ID de pays
        List<Integer> countryIds = new ArrayList<>();

        // Requête SQL pour sélectionner tous les ID de pays
        String query = "SELECT idcountry FROM Country"; // Assurez-vous que les noms de la table et de la colonne sont corrects

        // Utilisez un `PreparedStatement` avec votre connexion `cnx`
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            // Parcourez les résultats
            while (rs.next()) {
                // Récupérez l'ID du pays à partir du `ResultSet` et ajoutez-le à la liste
                int countryId = rs.getInt("idcountry");
                countryIds.add(countryId);
            }
        }

        // Retournez la liste des ID de pays
        return countryIds;
    }

    @Override
    public void updateApprovedStatus(product product) throws SQLException {
        String requete = "UPDATE product " +
                "SET approved = 1 " +
                "WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(requete)) {

            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();
            System.out.println("Product updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String translate(String sourceLang, String targetLang, String text) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        String apiKey = "8433d15735msh2702a52a05e99bdp1e7f6djsn60b6c77343ce";
        String apiUrl = "https://swift-translate.p.rapidapi.com/translate";
        String textWithoutNewlines = text.replaceAll("\\n", "").replaceAll("\\r", "");

        String requestBody = String.format("{\n    \"text\": \"%s\",\n    \"sourceLang\": \"%s\",\n    \"targetLang\": \"%s\"\n}",
                textWithoutNewlines, sourceLang, targetLang);

        try {
            Response response = client.prepare("POST", apiUrl)
                    .setHeader("content-type", "application/json")
                    .setHeader("X-RapidAPI-Key", apiKey)
                    .setHeader("X-RapidAPI-Host", "swift-translate.p.rapidapi.com")
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

    public static String extractTranslatedText(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            return rootNode.get("translatedText").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isBadContent(String content) {
        AsyncHttpClient client1 = new DefaultAsyncHttpClient();

        try {
            CompletableFuture<Response> responseFuture = client1.prepare("POST", "https://neutrinoapi-bad-word-filter.p.rapidapi.com/bad-word-filter")
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
                client1.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing client", e);
            }
        }
    }

    private static String encodeContent(String content) {
        return content.replace(" ", "%20");
    }

    private static boolean parseBadWordResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        JsonNode isBadNode = jsonNode.get("is-bad");
        return isBadNode != null && isBadNode.asBoolean();
    }

}