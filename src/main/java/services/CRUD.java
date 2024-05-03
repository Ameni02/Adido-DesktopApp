package services;


import models.Blog;
import models.Image;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void insertImageProduit(Image imageBlog) throws SQLException;

    void updateApprovedStatus(Blog blog) throws SQLException;

    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException; // Assurez-vous que la signature est compatible ici



}
