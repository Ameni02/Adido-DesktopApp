package servises;
import java.util.List;

import java.awt.*;
import java.sql.SQLException;

public interface CRUD<T> {
    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException;

}
