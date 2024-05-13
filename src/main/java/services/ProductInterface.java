package services;


import models.Image;
import models.Country;
import models.product;
import java.sql.SQLException;
import java.util.List;

public interface ProductInterface<T> {

    void insertImageProduit(Image imageproduit) throws SQLException;
    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException;
    List<Integer> getAllCountryIds() throws SQLException;
    void updateApprovedStatus(product product) throws SQLException;



}
