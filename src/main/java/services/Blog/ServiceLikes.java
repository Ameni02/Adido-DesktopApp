package services.Blog;
import models.Blog.LikePost;
import utils.DBConnection;

import java.sql.*;

public class ServiceLikes implements CrudLike<LikePost> {

    private static Connection cnx;

    public ServiceLikes() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void AjouterLike(LikePost likePost) throws SQLException {

        String req = "INSERT INTO like_blog (idblog) VALUES (?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, likePost.getIdbloglike()); // Utilisation de l'instance de LikePost

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int idlike = rs.getInt(1);
                likePost.setIdlike(idlike);
            }

            System.out.println("Like added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getLikeCountByBlogId(int blogId) throws SQLException {
        String query = "SELECT COUNT(*) FROM like_blog WHERE idblog = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, blogId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1); // Retourne le nombre de likes pour le blog spécifique
            }
        }
        return 0;
    }



    @Override
    public void deleteLikeByBlogId(int blogId, int likeId) throws SQLException {
        String req = "DELETE FROM like_blog WHERE idblog = ? AND id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, blogId);
            preparedStatement.setInt(2, likeId);
            preparedStatement.executeUpdate();
        }
    }
    @Override
    public LikePost getLastLikeByBlogId(int blogId) throws SQLException {
        String query = "SELECT * FROM like_blog WHERE idblog = ? ORDER BY id DESC LIMIT 1";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, blogId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idlike = resultSet.getInt("id");
                    int idblog = resultSet.getInt("idblog");
                    return new LikePost(idlike, idblog);
                }
            }
        }
        return null;
    }


}



