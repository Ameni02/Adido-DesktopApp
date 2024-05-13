package services.Offer;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    int insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException;

}