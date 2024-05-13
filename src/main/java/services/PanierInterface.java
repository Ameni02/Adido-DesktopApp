package services;

import models.Panier;

import java.sql.SQLException;
import java.util.List;

public interface PanierInterface<T> {

    void addToCart(T t) throws SQLException;
    void deleteOne(int panier) throws SQLException;
    List<T> selectAll(String Id) throws SQLException;
    public int retrieveOneProduct(String nom) throws SQLException;
    public void clearAll() throws SQLException ;
    public int getQuantity(String productName) throws SQLException ;
    public void updateCartItem(Panier panier) throws SQLException ;
    public int getStock(String productName) throws SQLException;
}