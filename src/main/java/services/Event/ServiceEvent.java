
package services.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Event.Event;
import utils.DBConnection;

public class ServiceEvent {
    private static Connection cnx;

    public ServiceEvent() {
        cnx = DBConnection.getInstance().getCnx();
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
        ps.executeUpdate();
        System.out.println("Event Added !");
    }

    public static void updateOne(Event event) throws SQLException {
        String requete = "UPDATE event SET `nameevent`=?, `descriptionevent`=?, `datestartevent`=?, `dateendevent`=?, `locationevent`=?, `idorganiser`=?, `nbattendees`=?, `affiche`=?, `idcountry`=? WHERE `idevent`=?";

        try {
            PreparedStatement ps = DBConnection.getInstance().getCnx().prepareStatement(requete);
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
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Event Updated!");
            } else {
                System.out.println("No event found with ID: " + event.getIdevent());
            }

        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static List<Integer> getAllCountryIds() throws SQLException {
        List<Integer> countryIds = new ArrayList();
        String query = "SELECT idcountry FROM Country";
        PreparedStatement ps = cnx.prepareStatement(query);

        try {
            ResultSet rs = ps.executeQuery();

            try {
                while(rs.next()) {
                    int countryId = rs.getInt("idcountry");
                    countryIds.add(countryId);
                }
            } catch (Throwable var8) {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }
                }

                throw var8;
            }

            if (rs != null) {
                rs.close();
            }
        } catch (Throwable var9) {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Throwable var6) {
                    var9.addSuppressed(var6);
                }
            }

            throw var9;
        }

        if (ps != null) {
            ps.close();
        }

        return countryIds;
    }

    public static void deleteOne(Event event) throws SQLException {
        String requete = "DELETE FROM `event` WHERE `idevent`=?";

        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getCnx().prepareStatement(requete);
            preparedStatement.setInt(1, event.getIdevent());
            preparedStatement.executeUpdate();
            System.out.println("deleted !");
        } catch (SQLException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static List<Event> selectAll() throws SQLException {
        List<Event> eventList = new ArrayList();
        String req = "SELECT * FROM `event`";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
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
            PreparedStatement statement = DBConnection.getInstance().getCnx().prepareStatement(query);
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
            }

            System.out.println("news Fetched!");
            return event;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }
}
