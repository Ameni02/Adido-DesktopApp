package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUDComment<T>  {

    void insertOneComment(T t) throws SQLException;
    void updateOneComment(T t) throws SQLException;
    void deleteOneComment(T t) throws SQLException;
    List<T> selectAllComments() throws SQLException;
}
