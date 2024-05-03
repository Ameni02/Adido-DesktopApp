package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import models.Ecommerce.Panier;
import utils.DBConnection;

public class ServicePanier implements PanierInterface<Panier> {

    private Connection cnx;

    public ServicePanier() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void addToCart(Panier panier) throws SQLException {
        String req = "INSERT INTO `panier`(`id`, `quantity`, `prix_total`, `product_name`,`idUser` ) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, panier.getId());
            ps.setInt(2, panier.getQuantity());
            ps.setInt(3, panier.getPrixTotal());
            ps.setString(4, panier.getProductName());
            ps.setInt(5, 1);
            ps.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product successfully added to the shopping cart.");
            alert.showAndWait();
            System.out.println("Panier Added !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void deleteOne(int panier) throws SQLException {
        String req = "DELETE FROM `panier` WHERE `idPanier`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, panier);
            ps.executeUpdate();
            System.out.println("Panier Deleted !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<Panier> selectAll(String Id) throws SQLException {
        List<Panier> panierList = new ArrayList<>();
        String req = "SELECT * FROM `panier`";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Panier p = new Panier(
                        rs.getInt("id_panier"),
                        rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getInt("prix_total"),
                        rs.getString("product_name")


                );
                panierList.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return panierList;
    }

    @Override
    public int retrieveOneProduct(String nom) throws SQLException {
        int productId = 0;

        // Query the database to retrieve the ID corresponding to the provided name
        String query = "SELECT id FROM product WHERE nomproduct = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, nom);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    productId = resultSet.getInt("id");
                }
            }
        }

        return productId;
    }
    public void updateCartItem(Panier panier) throws SQLException {
        String query = "UPDATE panier SET quantity = ?, prix_total = ? WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setInt(1, panier.getQuantity());
        statement.setInt(2, panier.getPrixTotal());
        statement.setInt(3, panier.getId());
        statement.executeUpdate();
    }

    public void clearAll() throws SQLException {
        String req = "DELETE FROM `panier`";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.executeUpdate();
            System.out.println("All items in the cart have been cleared.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    public int getQuantity(String productName) throws SQLException {
        String query = "SELECT quantity FROM panier WHERE product_name = ?";
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setString(1, productName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("quantity");
        } else {
            return 0;
        }
    }
    }

