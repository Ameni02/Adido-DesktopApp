package services.Offer;

import models.Offer.Offer;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceOffer implements CRUD<Offer> {

    private final Connection cnx;

    public ServiceOffer() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public int insertOne(Offer offer) throws SQLException {
        String req = "INSERT INTO offre`(prixpost`, descriptionpost, likespost, photopost, datedebut, datefin, nom_post, iduser, reservation,`country`) " +
                "VALUES " + "(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, offer.getPrixpost());
        ps.setString(2, offer.getDescriptionpost());
        ps.setInt(3, offer.getLikespost());
        ps.setString(4, offer.getPhotopost());
        ps.setDate(5, Date.valueOf(offer.getDateDebut()));
        ps.setDate(6, Date.valueOf(offer.getDateFin()));
        ps.setString(7, offer.getNom_post());
        ps.setInt(8, offer.getIduser());
        ps.setInt(9, offer.getReservation());
        ps.setString(10,offer.getCountry());
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
    }


    @Override
    public void updateOne(Offer offer) throws SQLException {
        String req = "UPDATE offre SET prixpost`=?,descriptionpost`=?,`likespost`=?,`photopost`=?,`datedebut`=?,`datefin`=?,`nom_post`=?,`iduser`=?,`reservation`=? WHERE `idpost`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setDouble(1, offer.getPrixpost());
        ps.setString(2, offer.getDescriptionpost());
        ps.setInt(3, offer.getLikespost());
        ps.setString(4, offer.getPhotopost());
        ps.setDate(5, Date.valueOf(offer.getDateDebut()));
        ps.setDate(6, Date.valueOf(offer.getDateFin()));
        ps.setString(7, offer.getNom_post());
        ps.setInt(8, offer.getIduser());
        ps.setInt(9, offer.getReservation());
        ps.setInt(10,offer.getIdpost());
        ps.executeUpdate();
    }

    @Override
    public void deleteOne(Offer offer) throws SQLException {
        String req = "DELETE FROM offre WHERE `idpost`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, offer.getIdpost());
        ps.executeUpdate();
    }

    @Override
    public List<Offer> selectAll() throws SQLException {
        List<Offer> offerList = new ArrayList<>();

        String req = "SELECT * FROM offre";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            Offer o = new Offer();
            o.setIdpost(rs.getInt("idpost"));
            o.setPrixpost(rs.getDouble("prixpost"));
            o.setDescriptionpost(rs.getString("descriptionpost"));
            o.setLikespost(rs.getInt("likespost"));
            o.setPhotopost(rs.getString("photopost"));
            o.setDateDebut(rs.getDate("datedebut").toLocalDate());
            o.setDateFin(rs.getDate("datefin").toLocalDate());
            o.setNom_post(rs.getString("nom_post"));
            o.setIduser(rs.getInt("iduser"));
            o.setReservation(rs.getInt("reservation"));
            offerList.add(o);
        }
        return offerList;
    }

    public List<String> selectCountry() throws SQLException {
        List<String> countryList = new ArrayList<>();

        String req = "SELECT namecountry FROM country";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                String countryName = rs.getString("namecountry");
                countryList.add(countryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }



    public List<Offer> search(String searchTerm) throws SQLException {
        List<Offer> searchResult = new ArrayList<>();
        String sql = "SELECT * FROM offre WHERE title_offer LIKE ? OR description_offer LIKE ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, "%" + searchTerm + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idOffer = resultSet.getInt("id_offer");
                    String titleOffer = resultSet.getString("title_offer");
                    String descriptionOffer = resultSet.getString("description_offer");
                    String countryOffer = resultSet.getString("country_offer");
                    String photoOffer = resultSet.getString("photo_offer");
                    LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                    LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

                    Offer offer = new Offer(idOffer, titleOffer, descriptionOffer, countryOffer, photoOffer, startDate, endDate);
                    searchResult.add(offer);
                }
            }
        }

        return searchResult;
    }
}
