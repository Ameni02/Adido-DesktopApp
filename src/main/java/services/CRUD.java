package services;

import models.Image;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void insertImageProduit(Image imageproduit) throws SQLException;
    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
     List<T> selectAll() throws SQLException;
    List<Integer> getAllCountryIds() throws SQLException;

}
