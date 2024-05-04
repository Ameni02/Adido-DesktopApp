package servises;

import model.Event;
import utils.DBConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceEvent {
    private static Connection cnx;


    public ServiceEvent() {
        cnx = DBConnexion.getInstance().getCnx();
    }

    public void insertOne(Event event) throws SQLException {
        String req = "INSERT INTO `event`(`nameevent`, `descriptionevent`, `datestartevent`, `dateendevent`, `locationevent`, `idorganiser`, `nbattendees`, `affiche`,`idcountry`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, event.getNameevent());
        ps.setString(2, event.getDescriptionevent());
        ps.setObject(3, event.getDatestartevent());
        ps.setObject(4, event.getDateendevent());
        ps.setString(5, event.getLocationevent());
        ps.setInt(6, event.getIdorganiser());
        ps.setInt(7, event.getNbattendees());
        ps.setString(8, event.getAffiche());
        ps.setInt(9, event.getIdcountry());
        ps. executeUpdate();
        System.out.println("Event Added !");
    }

  /*  public void updateOne(Event event) throws SQLException {
        String req = "UPDATE `event` SET `nameevent`=?, `descriptionevent`=?, `datestartevent`=?, `dateendevent`=?, `locationevent`=?, `idorganiser`=?, `nbattendees`=?, `affiche`=?, `idcountry`=? WHERE `idevent`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, event.getNameevent());
        ps.setString(2, event.getDescriptionevent());
        ps.setString(3, event.getDatestartevent());
        ps.setString(4, event.getDateendevent());
        ps.setString(5, event.getLocationevent());
        ps.setInt(6, event.getIdorganiser());
        ps.setInt(7, event.getNbattendees());
        ps.setString(8, event.getAffiche());
        ps.setInt(9, event.getIdcountry());
        ps.setInt(10, event.getIdevent()); // Assuming `idevent` is the unique identifier
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Event Updated!");
        } else {
            System.out.println("No event found with the specified ID.");
        }
    }
   */

    public static void updateOne(Event event) throws SQLException {
        String requete = "UPDATE event " +
                "SET `nameevent`=?, `descriptionevent`=?, `datestartevent`=?, `dateendevent`=?, `locationevent`=?, `idorganiser`=?, `nbattendees`=?, `affiche`=?, `idcountry`=? WHERE `idevent`=?";
        try {
            PreparedStatement ps = DBConnexion.getInstance().getCnx().prepareStatement(requete);
            ps.setString(1, event.getNameevent());
            ps.setString(2, event.getDescriptionevent());
            ps.setObject(3, event.getDatestartevent());
            ps.setObject(4, event.getDateendevent());
            ps.setString(5, event.getLocationevent());
            ps.setInt(6, event.getIdorganiser());
            ps.setInt(7, event.getNbattendees());
            ps.setString(8, event.getAffiche());
            ps.setInt(9, event.getIdcountry());
            ps.setInt(10, event.getIdevent());

            // Execute the update operation
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Event Updated!");
            } else {
                System.out.println("No event found with ID: " + event.getIdevent());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Integer> getAllCountryIds() throws SQLException {
        // Créez une liste pour stocker les ID de pays
        List<Integer> countryIds = new ArrayList<>();

        // Requête SQL pour sélectionner tous les ID de pays
        String query = "SELECT idcountry FROM Country"; // Assurez-vous que les noms de la table et de la colonne sont corrects

        // Utilisez un PreparedStatement avec votre connexion cnx
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            // Parcourez les résultats
            while (rs.next()) {
                // Récupérez l'ID du pays à partir du ResultSet et ajoutez-le à la liste
                int countryId = rs.getInt("idcountry");
                countryIds.add(countryId);
            }
        }

        // Retournez la liste des ID de pays
        return countryIds;
    }


    public static void deleteOne(Event event) throws SQLException {

        String requete = "DELETE FROM `event` WHERE `idevent`=?";
        try {
            PreparedStatement preparedStatement = DBConnexion.getInstance().getCnx().prepareStatement(requete);
            preparedStatement.setInt(1, event.getIdevent());
            preparedStatement.executeUpdate(); // Execute prepared statement directly without passing the query string
            System.out.println("deleted !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
        public static List<Event> selectAll() throws SQLException {
            List<Event> eventList = new ArrayList<>();
            String req = "SELECT * FROM `event`";
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idevent = rs.getInt("idevent");
                String nameevent = rs.getString("nameevent");
                String descriptionevent = rs.getString("descriptionevent");
                Date datestartevent = rs.getDate("datestartevent");
                Date dateendevent = rs.getDate("dateendevent");
                String locationevent = rs.getString("locationevent");
                int idorganiser = rs.getInt("idorganiser");
                int nbattendees = rs.getInt("nbattendees");
                String affiche = rs.getString("affiche");
                int idcountry = rs.getInt("idcountry");

                Event e = new Event(nameevent, descriptionevent, datestartevent, dateendevent, locationevent, idorganiser, nbattendees, affiche, idcountry);
                e.setIdevent(idevent);
                eventList.add(e);
            }
            return eventList;
        }



    public Event getNewsById(int id) {
        Event event = null;
        String query = "SELECT * FROM event WHERE idevent = ?";
        try {
            PreparedStatement statement = DBConnexion.getInstance().getCnx().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                event = new Event();
                event.setIdevent(resultSet.getInt("idevent"));
                event.setNameevent(resultSet.getString("Nameevent"));
                event.setDescriptionevent(resultSet.getString("Descriptionevent"));
                event.setDatestartevent(resultSet.getDate("Datestartevent"));
                event.setDateendevent(resultSet.getDate("Dateendevent"));
                event.setIdorganiser(resultSet.getInt("Idorganiser"));
                event.setNbattendees(resultSet.getInt("Nbattendees"));
                event.setAffiche(resultSet.getString("Affiche"));
                event.setIdcountry(resultSet.getInt("Idcountry"));

                // Assuming category_id is an integer column representing the ordinal value of the enum
                // int categoryOrdinal = resultSet.getInt("category_id");
                //  news.setCategory(Threads.Category.values()[categoryOrdinal - 1]); // Assuming categories start from 1
            }
            System.out.println("news Fetched!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return event;
    }


    }

