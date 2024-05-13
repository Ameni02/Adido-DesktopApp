package services.Blog;


import models.Blog.Blog;
import models.Blog.Image;

import java.sql.SQLException;
import java.util.List;

public interface CRUDBlog<T> {

    void insertImageProduit(Image imageBlog) throws SQLException;

    void updateApprovedStatus(Blog blog) throws SQLException;

    void insertOne(T t, int idUser) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;



    List<T> selectAll() throws SQLException; // Assurez-vous que la signature est compatible ici



}
