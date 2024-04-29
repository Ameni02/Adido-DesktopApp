package services;
import models.Blog;
import models.Image;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceBlog implements CRUD<Blog> {

    private static Connection cnx;

    public ServiceBlog() {
        cnx = DBConnection.getInstance().getCnx();
    }


    @Override
    public void insertImageProduit(Image imageBlog) throws SQLException {
        // Vérifiez si le `product_id` existe dans la table `product`
        String checkProductIdQuery = "SELECT COUNT(*) FROM blog WHERE idblog = ?";
        try (PreparedStatement ps = cnx.prepareStatement(checkProductIdQuery)) {
            ps.setInt(1, imageBlog.getIdblog());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                throw new SQLException("Product ID does not exist.");
            }
        }

        // Insérez l'image dans la table `images`
        String req = "INSERT INTO image_blog (nom_image, idblog) VALUES (?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, imageBlog.getName_img());
            ps.setInt(2, imageBlog.getIdblog());
            ps.executeUpdate();
            System.out.println("Image added!");
        } catch (SQLException e) {
            throw new SQLException("Error adding image: " + e.getMessage(), e);
        }
    }





    @Override
    public void insertOne(Blog blog) {
        if (blog.getTitleBlog() == null || blog.getTitleBlog().isEmpty()) {
            throw new IllegalArgumentException("Le titre du blog ne peut pas être vide.");
        }

        String req = "INSERT INTO blog(titleblog,contentblog,country) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, blog.getTitleBlog());
            preparedStatement.setString(2, blog.getContentBlog());
            preparedStatement.setString(3, blog.getCountryBlog());

            // Exécutez la requête d'insertion
            preparedStatement.executeUpdate();

            // Obtenez le `product_id` généré
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int blogid = rs.getInt(1);
                blog.setIdblog(blogid);
            }

            System.out.println("Post added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateOne(Blog blog) throws SQLException {
        String requete = "UPDATE blog " +
                "SET titleblog = ?, contentblog = ?, country = ? " +
                "WHERE idblog = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(requete);
            preparedStatement.setString(1, blog.getTitleBlog());
            preparedStatement.setString(2, blog.getContentBlog());
            preparedStatement.setString(3, blog.getCountryBlog());
            preparedStatement.setInt(4, blog.getIdblog()); // Ajout de l'ID pour la clause WHERE

            preparedStatement.executeUpdate();
            System.out.println("Post Updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOne(Blog blog) throws SQLException {
        Connection conn = DBConnection.getInstance().getCnx();
        PreparedStatement stmt = null;

        try {
            // Désactiver temporairement les contraintes de clé étrangère
            //PreparedStatement stmtDisableFK = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
           // stmtDisableFK.executeUpdate();

            // Préparer et exécuter la requête de suppression
            String requete = "DELETE FROM blog WHERE idblog = ?";
            stmt = conn.prepareStatement(requete);
            stmt.setInt(1, blog.getIdblog());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Post supprimé !");
            } else {
                System.out.println("Aucun Post trouvé avec l'ID fourni.");
            }

            // Réactiver les contraintes de clé étrangère
            PreparedStatement stmtEnableFK = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
            stmtEnableFK.executeUpdate();
        } catch (SQLException e) {
            // Gérez l'exception en conséquence
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression du post : " + e.getMessage());
        } finally {
            // Fermer les ressources
            if (stmt != null) {
                stmt.close();
            }
            // Pas besoin de fermer la connexion car elle est gérée par DBConnection
        }
    }
    @Override
    public List<Blog> selectAll() throws SQLException {
        List<Blog> list = new ArrayList<>();
        String requete = "SELECT * FROM blog";
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(requete);
            while (rs.next()) {
                Blog b = new Blog();
                b.setIdblog(rs.getInt(1));
                b.setTitleBlog(rs.getString(2));
                b.setContentBlog(rs.getString(3));
                b.setCountryBlog(rs.getString(5));

                list.add(b);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }





    public List<Image> getImagesByProductId(int productId) throws SQLException {
        List<Image> images = new ArrayList<>();

        // Requête SQL pour sélectionner les images associées au blog donné
        String query = "SELECT id, nom_image, idblog FROM image_blog WHERE idblog = ?";

        // Préparez la déclaration avec le paramètre productId
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            // Parcourez les résultats et créez des objets Image pour chaque image associée au blog
            while (rs.next()) {
                int idImg = rs.getInt("id");
                String nameImg = rs.getString("nom_image");
                int blogid = rs.getInt("idblog");

                // Créez un objet Image à partir des résultats
                Image image = new Image(idImg, nameImg, blogid);

                // Ajoutez l'image à la liste des images
                images.add(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la récupération des images du blog : " + e.getMessage());
        }

        // Retournez la liste d'images
        return images;
    }

    public List<Integer> getAllIdBlogs() throws SQLException {
        List<Integer> idBlogList = new ArrayList<>();
        String query = "SELECT idblog FROM blog"; // Replace YourTableName with your actual table name

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idblog = resultSet.getInt("idblog");
                idBlogList.add(idblog);
            }
        }

        return idBlogList;
    }
}





