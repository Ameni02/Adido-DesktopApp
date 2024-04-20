package services;

import java.sql.SQLException;
import java.util.List;

public interface PanierInterface<T> {

    void addToCart(T t) throws SQLException;
    void deleteOne(int panier) throws SQLException;
    List<T> selectAll(String Id) throws SQLException;

}
