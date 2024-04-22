package services;

import models.Blog;
import models.Comments;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceComment implements CRUDComment<Comments>  {

    private static Connection cnx;

    public ServiceComment() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override

    public void insertOneComment(Comments comments) throws SQLException {
        String req = "INSERT INTO comments (idblog, contentcomment) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, comments.getIdblog());
            preparedStatement.setString(2, comments.getContentcomment());

            // Exécutez la requête d'insertion
            preparedStatement.executeUpdate();

            // Obtenez le `idcomment` généré
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int idcomment = rs.getInt(1);
                comments.setIdcomment(idcomment);
            }

            System.out.println("Comment added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void updateOneComment(Comments comments) throws SQLException {
        String requete = "UPDATE comments " +
                "SET contentcomment = ? " +
                "WHERE idcomment = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(requete);
            preparedStatement.setString(1, comments.getContentcomment());

            preparedStatement.setInt(2, comments.getIdcomment()); // Ajout de l'ID pour la clause WHERE

            preparedStatement.executeUpdate();
            System.out.println("Comment Updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteOneComment(Comments comments) throws SQLException {
        Connection conn = DBConnection.getInstance().getCnx();
        PreparedStatement stmt = null;

        try {


            // Préparer et exécuter la requête de suppression
            String requete = "DELETE FROM comments WHERE idcomment = ?";
            stmt = conn.prepareStatement(requete);
            stmt.setInt(1, comments.getIdcomment());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("comment supprimé !");
            } else {
                System.out.println("Aucun commentaire trouvé avec l'ID fourni.");
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
    public List<Comments> selectAllComments() throws SQLException {
        List<Comments> list = new ArrayList<>();
        String requete = "SELECT * FROM comments";
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(requete);
            while (rs.next()) {
                Comments c = new Comments();
                c.setIdcomment(rs.getInt(1));
                c.setContentcomment(rs.getString(2));
            list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;

    }


}