package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Ecommerce.Commande;
import utils.DBConnection;

public class ServiceCommande implements CommandeInterface<Commande> {

    private Connection cnx;

    public ServiceCommande() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Commande commande) throws SQLException {
        String req = "INSERT INTO `commande`(`id_commande`, `dateCommande`, `etatCommande`, `coupon`, `commande_Address`, `command_phone_number`, `additional_information` , `iduser` ) VALUES "+" (?,?,?,?,?,?,?,?)";
       PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, commande.getIdCommande());
            ps.setDate(2, new java.sql.Date(commande.getDateCommande().getTime()));
            ps.setString(3, commande.getEtatCommande());
            ps.setString(4, commande.getCoupon());
            ps.setString(5, commande.getCommandeAdresse());
            ps.setInt(6, commande.getCommandePhoneNumber());
            ps.setString(7, commande.getAdditionalInformation());
        int iduser;
        ps.setInt(8, iduser=1 );
            ps.executeUpdate();
            System.out.println("Commande Added !");
    }

    @Override
    public void updateOne(Commande commande) throws SQLException {
        String req = "UPDATE `commande` SET `dateCommande`=?, `etatCommande`=?, `coupon`=?, `commande_Address`=?, `command_phone_number`=?, `additional_information`=? WHERE `id_commande`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setDate(1, new java.sql.Date(commande.getDateCommande().getTime()));
        ps.setString(2, commande.getEtatCommande());
        ps.setString(3, commande.getCoupon());
        ps.setString(4, commande.getCommandeAdresse());
        ps.setInt(5, commande.getCommandePhoneNumber());
        ps.setString(6, commande.getAdditionalInformation());
        ps.setInt(7, commande.getIdCommande());
        ps.executeUpdate();
        System.out.println("Commande Updated !");
    }

    @Override
    public void deleteOne(Commande commande) throws SQLException {

    }

    @Override
    public void deleteOne(String commande) throws SQLException {
        String req = "DELETE FROM `commande` WHERE `id_commande`=?";
       PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, Integer.parseInt(commande));
            ps.executeUpdate();
            System.out.println("Commande Deleted !");
    }


    @Override
    public List<Commande> selectAll() throws SQLException {
        List<Commande> commandeList = new ArrayList<>();
        String req = "SELECT * FROM `commande`";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Commande c = new Commande(
                        rs.getInt("id_commande"),
                        rs.getDate("dateCommande"),
                        rs.getString("etatCommande"),
                        rs.getString("coupon"),
                        rs.getString("commande_address"),
                        rs.getInt("command_phone_number"),
                        rs.getString("additional_information")
                );
                commandeList.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return commandeList;
    }

}
