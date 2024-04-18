package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Ecommerce.Panier;
import utils.DBConnection;

public class ServicePanier implements CRUD<Panier> {

    private Connection cnx;

    public ServicePanier() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Panier panier) throws SQLException {
        String req = "INSERT INTO `panier`(`idPanier`, `id`, `quantity`, `prixTotal`, `productName`) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, panier.getIdPanier());
            ps.setInt(2, panier.getId());
            ps.setInt(3, panier.getQuantity());
            ps.setInt(4, panier.getPrixTotal());
            ps.setString(5, panier.getProductName());
            ps.executeUpdate();
            System.out.println("Panier Added !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void updateOne(Panier panier) throws SQLException {
        String req = "UPDATE `panier` SET `quantity`=?, `prixTotal`=?, `productName`=? WHERE `idPanier`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, panier.getQuantity());
            ps.setInt(2, panier.getPrixTotal());
            ps.setString(3, panier.getProductName());
            ps.setInt(4, panier.getIdPanier());
            ps.executeUpdate();
            System.out.println("Panier Updated !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void deleteOne(Panier panier) throws SQLException {
        String req = "DELETE FROM `panier` WHERE `idPanier`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, panier.getIdPanier());
            ps.executeUpdate();
            System.out.println("Panier Deleted !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void deleteOne(String commande) throws SQLException {

    }


    @Override
    public List<Panier> selectAll() throws SQLException {
        List<Panier> panierList = new ArrayList<>();
        String req = "SELECT * FROM `panier`";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Panier p = new Panier(
                        rs.getInt("idPanier"),
                        rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getInt("prixTotal"),
                        rs.getString("productName")
                );
                panierList.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return panierList;
    }
}
