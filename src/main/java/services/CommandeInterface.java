package services;

import java.sql.SQLException;
import java.util.List;

public interface CommandeInterface<T> {
    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    void deleteOne(String commande) throws SQLException;
    List<T> selectAll() throws SQLException;
}
